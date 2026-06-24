package com.aviz.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aviz.framework.config.ConfigReader;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton manager for ExtentReports.
 * <p>
 * Creates a timestamped HTML report in the configured output directory.
 * Thread-local {@link ExtentTest} nodes allow safe parallel test logging.
 * </p>
 *
 * Usage:
 * <pre>
 *   ExtentReportManager.initReports();                 // once per suite
 *   ExtentReportManager.createTest("My Scenario");     // per scenario
 *   ExtentReportManager.getTest().pass("Step passed");
 *   ExtentReportManager.flushReports();                // once per suite
 * </pre>
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    private ExtentReportManager() { /* utility class */ }

    /* ── Lifecycle ───────────────────────────────────────────── */

    /** Initialise the ExtentReports instance (call once before any test runs). */
    public static synchronized void initReports() {
        if (extent != null) {
            return;
        }
        ConfigReader config = ConfigReader.getInstance();
        String outputDir  = config.getReportsOutputDir();
        String timestamp  = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath = outputDir + File.separator + "AvizReport_" + timestamp + ".html";

        // Ensure output directory exists
        new File(outputDir).mkdirs();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Aviz Aero – Registration Automation Report");
        sparkReporter.config().setReportName("BDD Test Execution Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimelineEnabled(true);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Aviz Aero");
        extent.setSystemInfo("Environment", "Demo");
        extent.setSystemInfo("Base URL", config.getBaseUrl());
        extent.setSystemInfo("Browser", config.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Execution Date", timestamp);
    }

    /** Write all pending logs to the HTML report file. Call once after all tests finish. */
    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    /* ── Test node management ────────────────────────────────── */

    /**
     * Creates a new test node in the report for the current thread.
     *
     * @param testName display name of the test / scenario
     * @return the created {@link ExtentTest} node
     */
    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThreadLocal.set(test);
        return test;
    }

    /**
     * Creates a new test node with a description.
     *
     * @param testName    display name
     * @param description description shown in the report
     * @return the created {@link ExtentTest} node
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    /**
     * Returns the {@link ExtentTest} associated with the current thread.
     * Throws {@link IllegalStateException} if {@link #createTest} was not called first.
     */
    public static ExtentTest getTest() {
        ExtentTest test = testThreadLocal.get();
        if (test == null) {
            throw new IllegalStateException("No ExtentTest exists for this thread. Call createTest() first.");
        }
        return test;
    }

    /** Removes the test reference from the current thread's local storage. */
    public static void removeTest() {
        testThreadLocal.remove();
    }
}
