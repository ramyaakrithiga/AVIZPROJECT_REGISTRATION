package com.aviz.framework.utils;

import com.aviz.framework.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Utility for capturing screenshots during test execution.
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "target/screenshots/";

    private ScreenshotUtils() { /* utility class */ }

    /**
     * Captures a screenshot and saves it to the screenshots directory.
     *
     * @param screenshotName logical name for the screenshot file
     * @return absolute path of the saved screenshot file, or null on failure
     */
    public static String captureScreenshot(String screenshotName) {
        WebDriver driver = DriverManager.getDriver();
        try {
            new File(SCREENSHOT_DIR).mkdirs();
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Captures a screenshot and returns it as a Base64-encoded string
     * (useful for embedding directly into ExtentReports).
     *
     * @return Base64 string of the screenshot
     */
    public static String captureScreenshotAsBase64() {
        WebDriver driver = DriverManager.getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
