package com.aviz.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for Aviz Aero Registration – Step 2: Company Information.
 *
 * <p>Reached after a successful Step 1 submission. Fields:
 * <ul>
 *   <li>Company Name (required)</li>
 *   <li>Company Size (required)</li>
 *   <li>Communication Email (required)</li>
 *   <li>Website (optional)</li>
 *   <li>Office Address (required)</li>
 *   <li>City (required)</li>
 *   <li>Country (required)</li>
 *   <li>Company Phone (required)</li>
 * </ul>
 * Clicking "Next" advances to Step 3 (Document Upload).
 *
 * <p>Covers test cases: POS-03, NEG-05, NEG-06, NEG-07, EDGE-04, EDGE-05, EDGE-06.
 */
public class RegistrationStep2Page extends BasePage {

    /* ── Locators ────────────────────────────────────────────── */

            @FindBy(xpath = "//input[@id='auth-input-Enter-Company-Name' or @placeholder='Enter Company Name']"
                + " | //label[normalize-space()='Enter Company Name' or normalize-space()='Company Name']/following::input[1]")
    private WebElement companyNameField;

            @FindBy(xpath = "//select[option[contains(.,'employees')]]"
                + " | //label[normalize-space()='Company Size']/following::select[1]")
    private WebElement companySizeField;

            @FindBy(xpath = "//input[@id='auth-input-Email' or @placeholder='Enter Communication Email']"
                + " | //label[normalize-space()='Email']/following::input[1]")
    private WebElement communicationEmailField;

            @FindBy(xpath = "//input[@placeholder='Enter Company URL']"
                + " | //label[normalize-space()='Company URL']/following::input[1]")
    private WebElement websiteField;

            @FindBy(xpath = "//input[@aria-label='Search city' or @placeholder='Search and select city']"
                + " | //label[normalize-space()='City']/following::input[1]")
            private WebElement cityField;

            @FindBy(xpath = "//input[@aria-label='Country' or @placeholder='Country (auto-filled)']"
                + " | //label[normalize-space()='Country']/following::input[1]")
    private WebElement countryField;

            @FindBy(xpath = "//input[@id='auth-input-Enter-Contact-Phone-Number' or @placeholder='Enter Contact Phone Number']"
                + " | //label[normalize-space()='Enter Contact Phone Number']/following::input[1]")
    private WebElement companyPhoneField;

            @FindBy(xpath = "//input[@id='auth-input-Pincode' or @placeholder='Enter Pincode']"
                + " | //input[contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')"
                + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')"
                + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')]"
                + " | //label[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')]/following::input[1]")
            private WebElement pincodeField;

                private static final By PINCODE_LOCATOR = By.xpath(
                    "//input[@id='auth-input-Pincode' or @placeholder='Enter Pincode']"
                    + " | //input[contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                    + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                    + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                    + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                    + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                    + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                    + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                    + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                    + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')"
                    + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')"
                    + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')"
                    + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')]"
                    + " | //label[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pincode')"
                    + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'postal')"
                    + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'zip')]/following::input[1]");

            @FindBy(xpath = "//button[@type='submit' and normalize-space()='Next']"
                + " | //button[contains(@class,'auth-btn-primary') and normalize-space()='Next']"
                + " | //input[@value='Next']")
    private WebElement nextButton;

        @FindBy(xpath = "//h1 | //h2 | //*[contains(@class,'step') and (contains(text(),'2') "
            + "or contains(text(),'Company') or contains(text(),'Organization'))]"
            + " | //*[contains(.,'Enter Company Name')]")
    private WebElement stepHeading;

    /* ── Error locator ───────────────────────────────────────── */

    private static final By ANY_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') "
            + "or contains(@class,'validation') or contains(@class,'alert') "
            + "or contains(@class,'help-text') or @role='alert']");

    private static final By CITY_SUGGESTION_ITEMS = By.xpath(
            "//*[@role='option' or @role='listitem' or self::li or self::div]"
            + "[contains(@class,'option') or contains(@class,'suggest') or contains(@class,'menu-item') "
            + "or contains(@class,'autocomplete') or contains(@class,'pac-item') "
            + "or @role='option' or @role='listitem']");

    /* ── Constructor ────────────────────────────────────────── */

    public RegistrationStep2Page() {
        super();
    }

    private void setInputOrSelect(WebElement element, String value) {
        wait.waitForVisibility(element);
        String tagName = element.getTagName();
        if ("select".equalsIgnoreCase(tagName)) {
            try {
                new Select(element).selectByVisibleText(value);
                return;
            } catch (Exception ignored) { }
            try {
                new Select(element).selectByValue(value);
                return;
            } catch (Exception ignored) { }

            // Final fallback for styled / partially blocked selects.
            // Match either option text (exact) or value (exact), then trigger change.
            try {
                String script =
                        "const sel = arguments[0];" +
                        "const val = arguments[1];" +
                        "const opts = Array.from(sel.options || []);" +
                        "let match = opts.find(o => (o.text || '').trim() === val);" +
                        "if (!match) { match = opts.find(o => (o.value || '').trim() === val); }" +
                        "if (!match && /^\\d+\\-\\d+\\s+employees$/i.test(val)) {" +
                        "  const compact = val.replace(/\\s+employees$/i, '').trim();" +
                        "  match = opts.find(o => (o.value || '').trim() === compact);" +
                        "}" +
                        "if (!match) {" +
                        "  const numericOnly = val.replace(/\\s+employees$/i, '').trim();" +
                        "  match = opts.find(o => (o.text || '').trim().toLowerCase() === numericOnly.toLowerCase());" +
                        "}" +
                        "if (!match) return false;" +
                        "sel.value = match.value;" +
                        "sel.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "sel.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "return true;";
                Object selected = ((JavascriptExecutor) driver).executeScript(script, element, value);
                if (Boolean.TRUE.equals(selected)) {
                    return;
                }
            } catch (Exception ignored) { }

            throw new IllegalStateException("Unable to select dropdown value: " + value);
        }
        clearAndType(element, value);
    }

    /* ── Actions ─────────────────────────────────────────────── */

    public RegistrationStep2Page enterCompanyName(String name) {
        clearAndType(companyNameField, name);
        return this;
    }

    public RegistrationStep2Page enterCompanySize(String size) {
        setInputOrSelect(companySizeField, size);
        return this;
    }

    public RegistrationStep2Page enterCommunicationEmail(String email) {
        clearAndType(communicationEmailField, email);
        return this;
    }

    public RegistrationStep2Page enterWebsite(String website) {
        if (!website.isEmpty()) {
            String normalizedWebsite = website.replaceFirst("^https?://", "");
            clearAndType(websiteField, normalizedWebsite);
        }
        return this;
    }

    public RegistrationStep2Page enterAddress(String address) {
        // The live Step 2 form no longer exposes a free-text address field.
        // Preserve the public method for compatibility with existing steps.
        return this;
    }

    public RegistrationStep2Page enterCity(String city) {
        wait.waitForVisibility(cityField);
        cityField.click();
        cityField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        cityField.sendKeys(Keys.DELETE);
        cityField.sendKeys(city);

        boolean selected = false;
        try {
            // Prefer clicking a suggestion item that matches the requested city text.
            List<WebElement> suggestions = driver.findElements(CITY_SUGGESTION_ITEMS);
            for (WebElement item : suggestions) {
                String text = item.getText() == null ? "" : item.getText().trim();
                if (!text.isEmpty() && text.toLowerCase().contains(city.toLowerCase()) && item.isDisplayed()) {
                    try {
                        item.click();
                    } catch (Exception ignored) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
                    }
                    selected = true;
                    break;
                }
            }
        } catch (Exception ignored) { }

        if (!selected) {
            try {
                cityField.sendKeys(Keys.ARROW_DOWN);
                cityField.sendKeys(Keys.ENTER);
                cityField.sendKeys(Keys.TAB);
                selected = true;
            } catch (Exception ignored) { }
        }

        if (!selected) {
            // Final fallback for custom autocomplete widgets that react only to JS events.
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                    "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                    "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));" +
                    "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
                    cityField, city);
        }

        // Some builds only commit autocomplete choice on blur.
        try {
            cityField.sendKeys(Keys.TAB);
        } catch (Exception ignored) { }

        return this;
    }

    public RegistrationStep2Page enterCountry(String country) {
        // Country is auto-filled by the selected city in the live form.
        return this;
    }

    public RegistrationStep2Page enterCompanyPhone(String phone) {
        clearAndType(companyPhoneField, phone);
        return this;
    }

    public RegistrationStep2Page enterPincode(String pincode) {
        try {
            wait.waitForVisibility(pincodeField);
            clearAndType(pincodeField, pincode);
            return this;
        } catch (Exception ignored) {
            // Fall through to broader locator scan if the primary element binding is stale or hidden.
        }

        try {
            List<WebElement> pincodeInputs = driver.findElements(PINCODE_LOCATOR);
            for (WebElement input : pincodeInputs) {
                if (input.isDisplayed() && input.isEnabled()) {
                    try {
                        input.clear();
                        input.sendKeys(pincode);
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].value = arguments[1];"
                                        + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                        + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                                input, pincode);
                    }
                    return this;
                }
            }
        } catch (Exception ignored) {
            // Some tenant configurations may hide pincode; skip in that case.
        }
        return this;
    }

    /**
     * Fills all Step-2 company fields. Pass empty string {@code ""} for optional / blank fields.
     */
    public RegistrationStep2Page fillStep2Form(String companyName, String companySize,
                                               String commEmail, String website,
                                               String address, String city,
                                               String country, String phone) {
        if (!companyName.isEmpty())  enterCompanyName(companyName);
        if (!companySize.isEmpty())  enterCompanySize(companySize);
        if (!commEmail.isEmpty())    enterCommunicationEmail(commEmail);
        enterWebsite(website);
        if (!address.isEmpty())      enterAddress(address);
        if (!city.isEmpty())         enterCity(city);
        if (!country.isEmpty())      enterCountry(country);
        if (!phone.isEmpty())        enterCompanyPhone(phone);
        String pincode = address.matches("\\d{4,10}") ? address : "10115";
        enterPincode(pincode);
        return this;
    }

    /**
     * Clicks "Next" and returns a {@link RegistrationStep3Page} for chaining.
     */
    public RegistrationStep3Page clickNext() {
        try {
            click(nextButton);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
        }
        return new RegistrationStep3Page();
    }

    /** Clicks "Next" without complete data to trigger validation. */
    public void clickNextWithoutFillingForm() {
        wait.waitForVisibility(nextButton);
        nextButton.click();
    }

    /* ── Verifications ───────────────────────────────────────── */

    /** @return {@code true} if the Step 2 page is currently displayed. */
    public boolean isStep2PageDisplayed() {
        try {
            wait.waitForVisibility(companyNameField);
            return companyNameField.isDisplayed();
        } catch (Exception e) {
            return false;
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

    /** @return {@code true} if any visible error text contains the given keyword. */
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

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
