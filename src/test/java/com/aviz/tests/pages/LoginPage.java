package com.aviz.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Aviz Aero Login page (https://demo.aviz.aero).
 *
 * <p>Elements are identified from the live page:
 * <ul>
 *   <li>Email field  – input with placeholder "Email Id"</li>
 *   <li>Password     – input[type='password']</li>
 *   <li>Login button – button with visible text "Login"</li>
 *   <li>Register Now – anchor linking to /register</li>
 * </ul>
 *
 * <p>All interactions delegate to the helpers in {@link BasePage} which apply
 * explicit waits before acting on elements.
 */
public class LoginPage extends BasePage {

    /* ── Page Locators (via @FindBy – initialised by PageFactory in BasePage) ── */

    @FindBy(xpath = "//input[@placeholder='Email Id' or @type='email' or @name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password' or @placeholder='Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[normalize-space()='Login'] | //input[@value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[contains(text(),'Register') or contains(@href,'register')]")
    private WebElement registerNowLink;

    @FindBy(xpath = "//a[contains(text(),'Forget') or contains(@href,'forgot')]")
    private WebElement forgetPasswordLink;

    /* ── Constructor ────────────────────────────────────────── */

    public LoginPage() {
        super();
    }

    /* ── Actions ─────────────────────────────────────────────── */

    /** Opens the login page in the browser. */
    public LoginPage open() {
        navigateToBaseUrl();
        return this;
    }

    /** Enters the email address into the Email Id field. */
    public LoginPage enterEmail(String email) {
        clearAndType(emailField, email);
        return this;
    }

    /** Enters the password into the Password field. */
    public LoginPage enterPassword(String password) {
        clearAndType(passwordField, password);
        return this;
    }

    /** Clicks the Login button. */
    public void clickLogin() {
        click(loginButton);
    }

    /** Fills in credentials and submits the login form. */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    /**
     * Clicks the "Register Now!" link and returns a {@link RegistrationPage}
     * instance ready for further interactions.
     */
    public RegistrationPage clickRegisterNow() {
        click(registerNowLink);
        wait.waitForUrlContains("register");
        return new RegistrationPage();
    }

    /* ── Verifications ───────────────────────────────────────── */

    /** @return {@code true} if the Login page is currently displayed. */
    public boolean isLoginPageDisplayed() {
        try {
            wait.waitForVisibility(emailField);
            return emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** @return {@code true} if the "Register Now!" link is visible. */
    public boolean isRegisterNowLinkVisible() {
        try {
            wait.waitForVisibility(registerNowLink);
            return registerNowLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** @return the text of the "Register Now!" link. */
    public String getRegisterNowLinkText() {
        return getText(registerNowLink);
    }

    /** @return the current URL of the login page. */
    public String getLoginPageUrl() {
        return getCurrentUrl();
    }
}
