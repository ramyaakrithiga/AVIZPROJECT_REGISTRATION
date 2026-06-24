package com.aviz.framework.utils;

import com.aviz.framework.driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Reusable explicit-wait utilities.
 * <p>
 * All methods obtain the current thread's WebDriver through {@link DriverManager}
 * so they are safe for parallel execution.
 * </p>
 */
public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /**
     * Creates a WaitUtils instance using the globally configured explicit-wait timeout.
     *
     * @param timeoutSeconds seconds before an explicit wait times out
     */
    public WaitUtils(int timeoutSeconds) {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /* ── Visibility ─────────────────────────────────────────── */

    /** Wait until the element located by {@code locator} is visible. */
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait until the given {@code element} is visible. */
    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /* ── Clickability ───────────────────────────────────────── */

    /** Wait until the element located by {@code locator} is clickable. */
    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Wait until the given {@code element} is clickable. */
    public WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /* ── Presence ───────────────────────────────────────────── */

    /** Wait until the element is present in the DOM (may not be visible). */
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /* ── URL / Title ────────────────────────────────────────── */

    /** Wait until the current URL contains {@code urlFragment}. */
    public boolean waitForUrlContains(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    /** Wait until the page title contains {@code titleFragment}. */
    public boolean waitForTitleContains(String titleFragment) {
        return wait.until(ExpectedConditions.titleContains(titleFragment));
    }

    /* ── Text ───────────────────────────────────────────────── */

    /** Wait until the element's text contains {@code text}. */
    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /* ── Invisibility ───────────────────────────────────────── */

    /** Wait until the element is no longer visible or present. */
    public boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /* ── Alert ──────────────────────────────────────────────── */

    /** Wait until a JavaScript alert is present and return it. */
    public Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /* ── Custom ─────────────────────────────────────────────── */

    /** Apply any arbitrary {@link ExpectedCondition}. */
    public <T> T waitFor(ExpectedCondition<T> condition) {
        return wait.until(condition);
    }

    /* ── Page-load ready ────────────────────────────────────── */

    /** Polls until document.readyState == 'complete'. */
    public void waitForPageToLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
