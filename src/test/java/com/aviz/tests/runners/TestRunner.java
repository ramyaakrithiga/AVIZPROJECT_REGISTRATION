package com.aviz.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber TestNG runner.
 *
 * <p>Configuration options:
 * <ul>
 *   <li>{@code features}      – path to the Gherkin feature files</li>
 *   <li>{@code glue}          – packages containing step definitions and hooks</li>
 *   <li>{@code tags}          – filter which scenarios to run (override with -Dcucumber.filter.tags)</li>
 *   <li>{@code plugin}        – report plugins: pretty console, Cucumber HTML, JSON, and ExtentReport adapter</li>
 *   <li>{@code monochrome}    – cleaner console output</li>
 *   <li>{@code dryRun}        – set to true to check step mappings without executing</li>
 * </ul>
 *
 * <p>Run all tests:
 * <pre>mvn test</pre>
 *
 * <p>Run only @Smoke tagged scenarios:
 * <pre>mvn test -Dcucumber.filter.tags="@Smoke"</pre>
 *
 * <p>Run with a specific browser:
 * <pre>mvn test -Dbrowser=firefox</pre>
 */
@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = {
                "com.aviz.tests.hooks",
                "com.aviz.tests.stepdefinitions"
        },
        tags      = "not @Ignore",
        plugin    = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun     = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Override to enable parallel scenario execution when the TestNG suite
     * is configured with {@code data-provider-thread-count > 1}.
     *
     * @return a 2D array of scenario objects consumed by TestNG's parallel data provider
     */
    @Override
    @DataProvider(parallel = false)   // set parallel = true for concurrent execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
