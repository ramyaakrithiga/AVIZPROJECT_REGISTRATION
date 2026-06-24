package com.aviz.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

/**
 * Page Object for Aviz Aero Registration – Step 3: Document Upload & Submission.
 *
 * <p>Reached after a successful Step 2 submission. Actions on this page:
 * <ul>
 *   <li>Upload supporting documents (optional)</li>
 *   <li>Accept Terms & Conditions checkbox (required)</li>
 *   <li>Click "Send register request"</li>
 * </ul>
 * After submission a confirmation message is shown.
 *
 * <p>Covers test cases: POS-04, POS-05, POS-06, NEG-08, EDGE-07, EDGE-08.
 */
public class RegistrationStep3Page extends BasePage {

    /* ── Locators ────────────────────────────────────────────── */

            @FindBy(xpath = "//input[@type='file']"
            + " | //label[contains(normalize-space(),'Upload')]/following::input[@type='file'][1]"
                + " | //*[contains(normalize-space(),'Document')]/following::input[@type='file'][1]"
                + " | //input[contains(@accept,'.pdf') or contains(@accept,'image')]")
    private WebElement fileUploadInput;

        private static final By TERMS_CHECKBOX_CANDIDATES = By.xpath(
            "//input[@type='checkbox' and ("
                + "contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'tnc')"
                + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'tnc')"
                + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'agree')"
                + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'agree'))]"
                + " | //label[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'conditions')]/preceding::input[@type='checkbox'][1]"
                + " | //label[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'conditions')]/following::input[@type='checkbox'][1]"
                    + " | //*[(@role='checkbox' or contains(@class,'checkbox')) and (contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                    + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'conditions')"
                    + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                    + " or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'conditions'))]"
                    + " | //form//input[@type='checkbox']");

            private static final By TERMS_LABEL_CANDIDATES = By.xpath(
                "//label[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')"
                    + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'conditions')]"
                    + " | //*[self::span or self::div or self::p][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms and conditions')"
                    + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terms')]");

    @FindBy(xpath = "//button[contains(normalize-space(),'Send register request')] "
            + "| //button[contains(normalize-space(),'Submit')] "
            + "| //input[@value='Send register request']"
            + " | //*[self::button or self::a or self::input][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'send register request')]"
            + " | //button[@type='submit'][last()]")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(@class,'success') or contains(@class,'confirmation') "
            + "or contains(@class,'thank') or contains(text(),'submitted') "
            + "or contains(text(),'received') or contains(text(),'Thank you') "
            + "or contains(text(),'request has been') or contains(text(),'successfully')]")
    private WebElement confirmationMessage;

    @FindBy(xpath = "//h1 | //h2 | //*[contains(@class,'step') and (contains(text(),'3') "
            + "or contains(text(),'Document') or contains(text(),'Upload'))]")
    private WebElement stepHeading;

    /* ── Error locator ───────────────────────────────────────── */

    private static final By ANY_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') "
            + "or contains(@class,'validation') or contains(@class,'alert') "
            + "or contains(@class,'help-text') or @role='alert']");

    private static final By TERMS_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') or @role='alert']"
            + "[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
            + "'terms') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'condition') or "
            + "contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'accept')]");

    private static final By FILE_ERROR = By.xpath(
            "//*[contains(@class,'error') or contains(@class,'invalid') or @role='alert']"
            + "[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
            + "'file') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'type') or "
            + "contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),'size')]");

        private static final By STEP3_INDICATOR = By.xpath(
            "//*[contains(normalize-space(),'Step 3') or contains(normalize-space(),'Document Upload') "
            + "or contains(normalize-space(),'Upload Document')]");

        private static final By STEP3_PRIMARY_ACTION = By.xpath(
            "//button[contains(normalize-space(),'Send register request')]"
            + " | //button[contains(normalize-space(),'Submit')]"
            + " | //input[@value='Send register request']"
            + " | //button[@type='submit']");

        private static final By STEP3_TERMS = TERMS_CHECKBOX_CANDIDATES;

        private static final By STEP3_FILE_INPUT = By.xpath("//input[@type='file']");

    /* ── Constructor ────────────────────────────────────────── */

    public RegistrationStep3Page() {
        super();
    }

    /* ── Actions ─────────────────────────────────────────────── */

    /**
     * Uploads a document using the file input element.
     *
     * @param absoluteFilePath absolute path to the file to upload
     */
    public RegistrationStep3Page uploadDocument(String absoluteFilePath) {
        wait.waitForPresence(By.xpath("//input[@type='file']"));
        fileUploadInput.sendKeys(absoluteFilePath);
        return this;
    }

    /**
     * Creates a temporary dummy file of the given extension and size (approximately),
     * and uploads it. Useful for EDGE-07 and EDGE-08 test cases.
     *
     * @param fileName fictional file name including extension (e.g. "malware.exe")
     * @param sizeKB   approximate size to write in kilobytes (0 = empty file)
     */
    public RegistrationStep3Page uploadDummyFile(String fileName, int sizeKB) {
        try {
            File tempDir  = new File("target/temp-uploads");
            tempDir.mkdirs();
            File tempFile = new File(tempDir, fileName);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
                if (sizeKB > 0) {
                    byte[] data = new byte[sizeKB * 1024];
                    java.nio.file.Files.write(tempFile.toPath(), data);
                }
            }
            uploadDocument(tempFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create / upload dummy file: " + fileName, e);
        }
        return this;
    }

    /** Checks the Terms & Conditions checkbox. */
    public RegistrationStep3Page acceptTermsAndConditions() {
        WebElement termsCheckbox = findFirstVisibleTermsCheckbox();
        if (termsCheckbox == null && !clickTermsLabelFallback() && !setAnyCheckboxCheckedViaJs()) {
            throw new IllegalStateException("Unable to locate a visible Terms and Conditions checkbox on Step 3.");
        }

        if (termsCheckbox == null) {
            return this;
        }

        if (!termsCheckbox.isSelected()) {
            try {
                termsCheckbox.click();
            } catch (Exception e) {
                try {
                    driver.findElement(By.xpath(
                            "//label[contains(normalize-space(),'Terms') or contains(normalize-space(),'Conditions')][1]"))
                            .click();
                } catch (Exception inner) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);
                }
            }
        }
        return this;
    }

    /** Leaves the Terms & Conditions checkbox unchecked. */
    public RegistrationStep3Page leaveTermsUnchecked() {
        WebElement termsCheckbox = findFirstVisibleTermsCheckbox();
        if (termsCheckbox == null) {
            return this;
        }

        if (termsCheckbox.isSelected()) {
            try {
                termsCheckbox.click();
            } catch (Exception e) {
                try {
                    driver.findElement(By.xpath(
                            "//label[contains(normalize-space(),'Terms') or contains(normalize-space(),'Conditions')][1]"))
                            .click();
                } catch (Exception inner) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);
                }
            }
        }
        return this;
    }

    /** Clicks "Send register request" to submit the full registration. */
    public RegistrationStep3Page clickSubmit() {
        try {
            click(submitButton);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }
        return this;
    }

    /** Clicks submit without accepting T&C to trigger the T&C validation error. */
    public void clickSubmitWithoutTerms() {
        wait.waitForVisibility(submitButton);
        submitButton.click();
    }

    /* ── Verifications ───────────────────────────────────────── */

    /** @return {@code true} if the Step 3 document upload page is visible. */
    public boolean isStep3PageDisplayed() {
        try {
            wait.waitFor(d ->
                    isAnyDisplayed(STEP3_INDICATOR)
                            || isAnyDisplayed(STEP3_PRIMARY_ACTION)
                            || isAnyDisplayed(STEP3_TERMS)
                            || isAnyPresent(STEP3_FILE_INPUT));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isAnyDisplayed(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements.stream().anyMatch(el -> {
                try {
                    return el.isDisplayed();
                } catch (Exception ignored) {
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isAnyPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement findFirstVisibleTermsCheckbox() {
        List<WebElement> candidates = driver.findElements(TERMS_CHECKBOX_CANDIDATES);
        for (WebElement candidate : candidates) {
            try {
                if (candidate.isDisplayed() && candidate.isEnabled()) {
                    return candidate;
                }
            } catch (Exception ignored) { }
        }
        return null;
    }

    private boolean clickTermsLabelFallback() {
        List<WebElement> labels = driver.findElements(TERMS_LABEL_CANDIDATES);
        for (WebElement label : labels) {
            try {
                if (!label.isDisplayed()) {
                    continue;
                }
                try {
                    label.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", label);
                }
                return true;
            } catch (Exception ignored) { }
        }
        return false;
    }

    private boolean setAnyCheckboxCheckedViaJs() {
        try {
            List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
            for (WebElement checkbox : checkboxes) {
                try {
                    Object result = ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].checked = true;"
                                    + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
                                    + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                                    + "return arguments[0].checked;",
                            checkbox);
                    if (Boolean.TRUE.equals(result)) {
                        return true;
                    }
                } catch (Exception ignored) { }
            }
        } catch (Exception ignored) { }
        return false;
    }

    /** @return {@code true} if the confirmation / success message is visible after submission. */
    public boolean isConfirmationMessageDisplayed() {
        try {
            wait.waitForVisibility(confirmationMessage);
            String message = confirmationMessage.getText();
            System.out.println("Confirmation message: " + message);
            return confirmationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** @return the text of the confirmation message. */
    public String getConfirmationMessageText() {
        try {
            return getText(confirmationMessage);
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

    /** @return {@code true} if any visible error message contains the given keyword. */
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

    /** @return {@code true} if a T&C-related error is currently visible. */
    public boolean isTermsErrorDisplayed() {
        List<WebElement> errors = driver.findElements(TERMS_ERROR);
        return errors.stream().anyMatch(el -> {
            try { return el.isDisplayed(); } catch (Exception e) { return false; }
        });
    }

    /** @return {@code true} if the T&C checkbox is visible on the page. */
    public boolean isTermsCheckboxVisible() {
        try {
            return findFirstVisibleTermsCheckbox() != null;
        } catch (Exception e) {
            return false;
        }
    }

    /** @return {@code true} if the submit button is visible. */
    public boolean isSubmitButtonVisible() {
        try {
            wait.waitForVisibility(submitButton);
            return submitButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
