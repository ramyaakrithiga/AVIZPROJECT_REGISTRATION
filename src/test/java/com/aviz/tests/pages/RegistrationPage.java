package com.aviz.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for Aviz Aero Registration – Step 1: Personal Information.
 * URL: https://demo.aviz.aero/register
 *
 * <p>Fields collected in Step 1:
 * <ul>
 *   <li>Operator (dropdown / select)</li>
 *   <li>First Name</li>
 *   <li>Last Name</li>
 *   <li>Mail ID (email)</li>
 *   <li>Mobile / Phone Number</li>
 *   <li>Designation</li>
 * </ul>
 * Clicking "Next" advances to Step 2 (Company Information).
 *
 * <p>Covers test cases: POS-01, POS-02, NEG-01, NEG-02, NEG-03, NEG-04,
 * EDGE-01, EDGE-02, EDGE-03.
 */
public class RegistrationPage extends BasePage {

    /* ── Locators ────────────────────────────────────────────── */

        @FindBy(xpath = "//select[contains(@class,'auth-select--register') or option[@value='operator'] or option[@value='broker']]")
    private WebElement operatorDropdown;

        @FindBy(xpath = "//input[@id='auth-input-First-Name' or @placeholder='Enter First Name' "
            + "or @name='firstName' or @id='firstName' or @formcontrolname='firstName' or @aria-label='First Name']"
            + " | //label[normalize-space()='First Name']/following::input[1]"
            + " | //*[normalize-space()='First Name']/following::input[1]")
    private WebElement firstNameField;

        @FindBy(xpath = "//input[@id='auth-input-Last-Name' or @placeholder='Enter Last Name' "
            + "or @name='lastName' or @id='lastName' or @formcontrolname='lastName' or @aria-label='Last Name']"
            + " | //label[normalize-space()='Last Name']/following::input[1]"
            + " | //*[normalize-space()='Last Name']/following::input[1]")
    private WebElement lastNameField;

        @FindBy(xpath = "//input[@id='auth-input-Mail-ID' or @placeholder='Enter Mail ID' or @placeholder='Mail ID' "
            + "or @type='email' or @type='text' and @id='auth-input-Mail-ID' "
            + "or @name='email' or @id='email' or @formcontrolname='email' or @aria-label='Mail ID']"
            + " | //label[normalize-space()='Mail ID']/following::input[1]"
            + " | //*[normalize-space()='Mail ID']/following::input[1]")
    private WebElement emailField;

        @FindBy(xpath = "//input[@aria-label='Phone number' or @placeholder='Enter Phone Number' "
            + "or @name='phone' or @name='mobile' or @id='phone' or @id='mobile' "
            + "or @formcontrolname='phone' or @formcontrolname='mobile']"
            + " | //label[contains(normalize-space(),'Phone Number')]/following::input[@type='tel'][last()]"
            + " | //*[contains(normalize-space(),'Phone Number')]/following::input[@type='tel'][last()]")
    private WebElement mobileField;

        @FindBy(xpath = "//input[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country code')"
            + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country code')"
            + " or starts-with(@placeholder,'+')"
            + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country')"
            + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country')]"
            + " | //button[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country code')"
            + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country')"
            + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'country')]")
        private WebElement countryCodeField;

        private static final By COUNTRY_CODE_OPTION_IN_LIST = By.xpath(
            "//*[@role='option' or self::li or self::div]"
            + "[contains(normalize-space(),'+91') or contains(normalize-space(),'India')]");

        private static final String DEFAULT_COUNTRY_CODE = "+91";

        @FindBy(xpath = "//input[@id='auth-input-Designation' or @placeholder='Enter Designation' "
            + "or @placeholder='Designation' or @name='designation' or @id='designation' "
            + "or @formcontrolname='designation' or @aria-label='Designation']"
            + " | //label[normalize-space()='Designation']/following::input[1]"
            + " | //*[normalize-space()='Designation']/following::input[1]")
    private WebElement designationField;

        @FindBy(xpath = "//button[@type='submit' and normalize-space()='Next'] | //button[contains(@class,'auth-btn-primary') and normalize-space()='Next'] | //input[@value='Next']")
    private WebElement nextButton;

    @FindBy(xpath = "//h1 | //h2 | //*[contains(@class,'title') and "
            + "(contains(text(),'Create') or contains(text(),'Register') or contains(text(),'Account'))]")
    private WebElement pageHeading;

    /* ── Error locators (Step 1 field-level) ─────────────────── */

    private static final By FIRST_NAME_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') or @role='alert']"
            + "[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
            + "'first name')]");

    private static final By EMAIL_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') or @role='alert']"
            + "[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
            + "'email') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'mail')]");

    private static final By MOBILE_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') or @role='alert']"
            + "[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
            + "'mobile') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'phone')]");

    private static final By ANY_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') "
            + "or contains(@class,'validation') or contains(@class,'alert') "
            + "or contains(@class,'help-text') or @role='alert']");

    /* ── Constructor ────────────────────────────────────────── */

    public RegistrationPage() {
        super();
    }

    /* ── Actions ─────────────────────────────────────────────── */

    /** Selects the operator type from the dropdown. */
    public RegistrationPage selectOperator(String operatorValue) {
        try {
            wait.waitForVisibility(operatorDropdown);
            new Select(operatorDropdown).selectByVisibleText(operatorValue);
        } catch (Exception e) {
            // Operator may be a custom component; attempt click-based selection
            try {
                operatorDropdown.click();
                driver.findElement(By.xpath(
                        "//*[contains(@class,'option') and normalize-space()='" + operatorValue + "']"))
                      .click();
            } catch (Exception ignored) { }
        }
        return this;
    }

    public RegistrationPage ensureDefaultOperatorSelected() {
        try {
            wait.waitForVisibility(operatorDropdown);
            Select select = new Select(operatorDropdown);
            String selected = select.getFirstSelectedOption().getText().trim();
            if (selected.equalsIgnoreCase("Register as") || selected.isEmpty()) {
                select.selectByValue("operator");
            }
        } catch (Exception ignored) { }
        return this;
    }

    /** Enters the first name. */
    public RegistrationPage enterFirstName(String firstName) {
        clearAndType(firstNameField, firstName);
        return this;
    }

    /** Enters the last name. */
    public RegistrationPage enterLastName(String lastName) {
        clearAndType(lastNameField, lastName);
        return this;
    }

    /** Enters the email (Mail ID). */
    public RegistrationPage enterEmail(String email) {
        clearAndType(emailField, email);
        return this;
    }

    /** Enters the mobile / phone number. */
    public RegistrationPage enterMobile(String mobile) {
        try {
            wait.waitForVisibility(countryCodeField);
            countryCodeField.click();
            countryCodeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            countryCodeField.sendKeys(Keys.DELETE);
            countryCodeField.sendKeys(DEFAULT_COUNTRY_CODE);

            List<WebElement> countryOptions = driver.findElements(COUNTRY_CODE_OPTION_IN_LIST);
            for (WebElement option : countryOptions) {
                if (option.isDisplayed()) {
                    try {
                        option.click();
                    } catch (Exception ignored) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    }
                    break;
                }
            }

            countryCodeField.sendKeys(Keys.TAB);
        } catch (Exception ignored) { }
        clearAndType(mobileField, mobile);
        return this;
    }

    /** Enters the designation. */
    public RegistrationPage enterDesignation(String designation) {
        clearAndType(designationField, designation);
        return this;
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }

    /**
     * Fills all Step-1 form fields with the supplied values.
     * Pass empty string {@code ""} for any field that should remain blank.
     */
    public RegistrationPage fillStep1Form(String firstName, String lastName,
                                          String email, String mobile,
                                          String designation) {
        firstName = safeValue(firstName);
        lastName = safeValue(lastName);
        email = safeValue(email);
        mobile = safeValue(mobile);
        designation = safeValue(designation);
        ensureDefaultOperatorSelected();
        if (!firstName.isEmpty())   enterFirstName(firstName);
        if (!lastName.isEmpty())    enterLastName(lastName);
        if (!email.isEmpty())       enterEmail(email);
        if (!mobile.isEmpty())      enterMobile(mobile);
        if (!designation.isEmpty()) enterDesignation(designation);
        return this;
    }

    /** Overload that also sets the Operator field. */
    public RegistrationPage fillStep1Form(String operator, String firstName, String lastName,
                                          String email, String mobile, String designation) {
        operator = safeValue(operator);
        if (!operator.isEmpty())    selectOperator(operator);
        return fillStep1Form(firstName, lastName, email, mobile, designation);
    }

    /**
     * Clicks "Next" (waits until clickable so implicit timing is handled).
     * Returns a {@link RegistrationStep2Page} for chaining into Step 2.
     */
    public RegistrationStep2Page clickNext() {
        click(nextButton);
        return new RegistrationStep2Page();
    }

    /** Clicks "Next" without waiting for clickability (triggers HTML5 validation). */
    public void clickNextWithoutFillingForm() {
        wait.waitForVisibility(nextButton);
        nextButton.click();
    }

    /* ── Verifications ───────────────────────────────────────── */

    public boolean isRegistrationPageDisplayed() {
        try {
            wait.waitForVisibility(firstNameField);
            return firstNameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getRegistrationPageUrl() {
        return getCurrentUrl();
    }

    public boolean isOnRegistrationUrl() {
        return getCurrentUrl().toLowerCase().contains("register");
    }

    public String getPageHeadingText() {
        try {
            return getText(pageHeading);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean areErrorMessagesDisplayed() {
        try {
            List<WebElement> errors = driver.findElements(ANY_ERROR);
            return errors.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    public String getAllErrorMessages() {
        List<WebElement> errors = driver.findElements(ANY_ERROR);
        StringBuilder messages = new StringBuilder();
        for (WebElement el : errors) {
            try {
                if (el.isDisplayed() && !el.getText().isBlank()) {
                    if (messages.length() > 0) messages.append(" | ");
                    messages.append(el.getText().trim());
                }
            } catch (Exception ignored) { }
        }
        return messages.toString();
    }

    /** @return {@code true} if any error matching the given keyword is visible. */
    public boolean isErrorDisplayedContaining(String keyword) {
        List<WebElement> errors = driver.findElements(ANY_ERROR);
        return errors.stream().anyMatch(el -> {
            try {
                return el.isDisplayed()
                        && el.getText().toLowerCase().contains(keyword.toLowerCase());
            } catch (Exception e) {
                return false;
            }
        });
    }

    public boolean isNextButtonVisible() {
        try {
            wait.waitForVisibility(nextButton);
            return nextButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstNameFieldDisplayed() {
        return isDisplayed(firstNameField);
    }

    public boolean isEmailFieldDisplayed() {
        return isDisplayed(emailField);
    }

    public boolean isMobileFieldDisplayed() {
        return isDisplayed(mobileField);
    }
}
