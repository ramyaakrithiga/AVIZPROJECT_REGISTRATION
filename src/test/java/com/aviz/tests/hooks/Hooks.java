package com.aviz.tests.hooks;

import com.aviz.framework.config.ConfigReader;
import com.aviz.framework.driver.DriverManager;
import com.aviz.framework.utils.ExtentReportManager;
import com.aviz.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Cucumber Hooks for driver lifecycle, ExtentReports integration, and screenshot capture.
 *
 * <p>Execution order:
 * <ol>
 *   <li>{@link BeforeAll}  – initialises the ExtentReports instance once per suite</li>
 *   <li>{@link Before}     – starts a new WebDriver and creates a test node in the report</li>
 *   <li>Scenario steps run …</li>
 *   <li>{@link After}      – captures screenshot on failure, marks the test in the report,
 *                            and quits the driver</li>
 *   <li>{@link AfterAll}   – flushes the ExtentReports HTML file</li>
 * </ol>
 */
public class Hooks {

    private static final ConfigReader config = ConfigReader.getInstance();

    /* ── Suite-level hooks ─────────────────────────────────── */

    /**
     * Called once before the very first scenario.
     * Initialises the ExtentReports singleton so that all threads share one report file.
     */
    @BeforeAll
    public static void beforeAll() {
        ExtentReportManager.initReports();
    }

    /**
     * Called once after all scenarios have run.
     * Flushes pending log entries and writes the final HTML report to disk.
     */
    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flushReports();
    }

    /* ── Scenario-level hooks ──────────────────────────────── */

    /**
     * Called before each scenario.
     * <ul>
     *   <li>Initialises a new WebDriver for this thread.</li>
     *   <li>Creates an ExtentReport test node tagged with the scenario's tags.</li>
     * </ul>
     *
     * @param scenario the Cucumber {@link Scenario} metadata object
     */
    @Before
    public void setUp(Scenario scenario) {
        DriverManager.initDriver();

        // Create an ExtentTest node; include tags as categories
        ExtentTest test = ExtentReportManager.createTest(
                scenario.getName(),
                "Feature: " + scenario.getId().replace("/", " > ").replace(".feature", ""));

        scenario.getSourceTagNames().forEach(tag ->
                test.assignCategory(tag.replace("@", "")));

        test.info("Scenario started: " + scenario.getName());
        test.info("Tags: " + scenario.getSourceTagNames());
    }

    /**
     * Called after each scenario regardless of pass/fail.
     * <ul>
     *   <li>On failure: captures a screenshot, embeds it in both the Cucumber report
     *       and ExtentReport, marks the ExtentTest as FAILED.</li>
     *   <li>On pass: marks the ExtentTest as PASSED.</li>
     *   <li>Always quits the WebDriver.</li>
     * </ul>
     *
     * @param scenario the Cucumber {@link Scenario} metadata object
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                // Capture screenshot for Cucumber HTML report
                byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Screenshot on failure – " + scenario.getName());

                // Embed screenshot in ExtentReport
                if (config.isScreenshotOnFailure()) {
                    String base64Screenshot = ScreenshotUtils.captureScreenshotAsBase64();
                    ExtentReportManager.getTest()
                            .fail("Scenario FAILED: " + scenario.getName(),
                                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                } else {
                    ExtentReportManager.getTest()
                            .log(Status.FAIL, "Scenario FAILED: " + scenario.getName());
                }
            } else {
                ExtentReportManager.getTest()
                        .pass("Scenario PASSED: " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("[Hooks] Could not capture screenshot: " + e.getMessage());
            try {
                ExtentReportManager.getTest()
                        .log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
            } catch (Exception ignored) { }
        } finally {
            ExtentReportManager.removeTest();
            DriverManager.quitDriver();
        }
    }
}
