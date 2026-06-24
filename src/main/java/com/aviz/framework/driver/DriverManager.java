package com.aviz.framework.driver;

import com.aviz.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Thread-local WebDriver manager.
 * <p>
 * Initialises, provides, and tears down WebDriver instances per thread,
 * enabling safe parallel test execution.
 * </p>
 *
 * Usage:
 * <pre>
 *   DriverManager.initDriver();        // in @Before hook
 *   WebDriver driver = DriverManager.getDriver();
 *   DriverManager.quitDriver();        // in @After hook
 * </pre>
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ConfigReader config = ConfigReader.getInstance();

    private DriverManager() { /* utility class – no instantiation */ }

    /* ── Public API ─────────────────────────────────────────── */

    /** Initialise the WebDriver for the current thread. */
    public static void initDriver() {
        if (driverThreadLocal.get() != null) {
            return;  // already initialised for this thread
        }
        String browser = config.getBrowser().toLowerCase().trim();
        boolean headless = config.isHeadless();

        WebDriver driver;
        switch (browser) {
            case "firefox":
                driver = createFirefoxDriver(headless);
                break;
            case "edge":
                driver = createEdgeDriver(headless);
                break;
            case "chrome":
            default:
                driver = createChromeDriver(headless);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
    }

    /** Returns the WebDriver for the current thread. */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialised for this thread. Call initDriver() first.");
        }
        return driver;
    }

    /** Quits the browser and removes the WebDriver from the thread-local store. */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /* ── Private factory methods ────────────────────────────── */

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }
}
