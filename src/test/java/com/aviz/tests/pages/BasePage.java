package com.aviz.tests.pages;

import com.aviz.framework.config.ConfigReader;
import com.aviz.framework.driver.DriverManager;
import com.aviz.framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for all Page Objects.
 * <p>
 * Provides the WebDriver instance, a pre-configured WaitUtils, the base URL,
 * and common navigation helpers shared by every page.
 * </p>
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils wait;
    protected final ConfigReader config;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.config = ConfigReader.getInstance();
        this.wait   = new WaitUtils(config.getExplicitWait());
        PageFactory.initElements(driver, this);
    }

    /* ── Navigation helpers ─────────────────────────────────── */

    /** Navigates to the application's base URL. */
    public void navigateToBaseUrl() {
        driver.get(config.getBaseUrl());
        wait.waitForPageToLoad();
    }

    /** Navigates to an absolute URL. */
    public void navigateTo(String url) {
        driver.get(url);
        wait.waitForPageToLoad();
    }

    /* ── Common page queries ─────────────────────────────────── */

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /* ── Element interaction helpers ────────────────────────── */

    /** Clears a field and types the given text. */
    protected void clearAndType(WebElement element, String text) {
        wait.waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /** Clicks an element after waiting for it to be clickable. */
    protected void click(WebElement element) {
        wait.waitForClickability(element);
        element.click();
    }

    /** Returns the trimmed visible text of an element. */
    protected String getText(WebElement element) {
        wait.waitForVisibility(element);
        return element.getText().trim();
    }

    /** Returns whether the element is currently displayed on the page. */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
