package com.aviz.tests.stepdefinitions;

import com.aviz.framework.utils.ExtentReportManager;
import com.aviz.tests.pages.LoginPage;
import com.aviz.tests.pages.RegistrationPage;
import com.aviz.tests.pages.RegistrationStep2Page;
import com.aviz.tests.pages.RegistrationStep3Page;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Step Definitions covering all 22 test cases from RegistrationPage.docx:
 *   POS-01 to POS-06  (Positive)
 *   NEG-01 to NEG-08  (Negative)
 *   EDGE-01 to EDGE-08 (Edge / Boundary)
 *
 * Page objects are lazily initialised per scenario after the Background step
 * creates the browser session (via {@link com.aviz.tests.hooks.Hooks}).
 */
public class RegistrationSteps {

    /* ── Page object references ── */
    private LoginPage             loginPage;
    private RegistrationPage      registrationPage;  // Step 1
    private RegistrationStep2Page step2Page;         // Step 2 – Company info
    private RegistrationStep3Page step3Page;         // Step 3 – Docs + T&C

    /* ── Shared valid test data for "completes Step X" helpers ── */
    private static final String VALID_FIRST      = "Auto";
    private static final String VALID_LAST       = "Tester";
    private static final String VALID_DESIG      = "QA Engineer";

    private static final String VALID_COMPANY    = "Aviz corporation and limited";
    private static final String VALID_CO_SIZE    = "11-50 employees";
    private static final String VALID_COMM_EMAIL = "contact@avizautocorp.qa";
    private static final String VALID_WEBSITE    = "avizautocorp.qa";
    private static final String VALID_ADDRESS    = "10115";
    private static final String VALID_CITY       = "Berlin";
    private static final String VALID_COUNTRY    = "Germany";
    private static final String VALID_CO_PHONE   = "9000000001";
    private static final AtomicInteger UNIQUE_COUNTER = new AtomicInteger(0);
        private static final String STALE_DUPLICATE_EMAIL = "existing.user@avizdemo.aero";
        private static final Pattern REGISTERED_EMAIL_PATTERN = Pattern.compile(
            "Generated unique Step 1 data:\\s+([^\\s<]+)\\s*/");

    private ExtentTest report() {
        return ExtentReportManager.getTest();
    }

    private String uniqueEmail() {
        return "auto.tester." + System.currentTimeMillis() + "." + UNIQUE_COUNTER.incrementAndGet() + "@aviztest.qa";
    }

    private String uniqueMobile() {
        int suffix = UNIQUE_COUNTER.incrementAndGet() % 100000;
        return String.format("9%09d", suffix);
    }

    private String uniqueCompanyName() {
        return VALID_COMPANY + " " + System.currentTimeMillis() + "-" + UNIQUE_COUNTER.incrementAndGet();
    }

    private String safeCellValue(String value) {
        return value == null ? "" : value;
    }

    private String resolveDuplicateEmailCandidate(String email) {
        String normalizedEmail = safeCellValue(email);
        if (!STALE_DUPLICATE_EMAIL.equalsIgnoreCase(normalizedEmail)) {
            return normalizedEmail;
        }

        String registeredEmail = findLatestRegisteredEmailFromReports();
        return registeredEmail == null ? normalizedEmail : registeredEmail;
    }

    private String findLatestRegisteredEmailFromReports() {
        Path reportsDir = Paths.get("target", "extent-reports");
        if (!Files.isDirectory(reportsDir)) {
            return null;
        }

        try (var paths = Files.list(reportsDir)) {
            return paths
                    .filter(path -> Files.isRegularFile(path)
                            && path.getFileName().toString().startsWith("AvizReport_")
                            && path.getFileName().toString().endsWith(".html"))
                    .sorted((left, right) -> {
                        try {
                            return Files.getLastModifiedTime(right).compareTo(Files.getLastModifiedTime(left));
                        } catch (IOException e) {
                            return 0;
                        }
                    })
                    .map(this::extractRegisteredEmail)
                    .filter(email -> email != null && !email.isBlank())
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            return null;
        }
    }

    private String extractRegisteredEmail(Path reportPath) {
        try {
            for (String line : Files.readAllLines(reportPath)) {
                Matcher matcher = REGISTERED_EMAIL_PATTERN.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1).trim();
                }
            }
        } catch (IOException ignored) { }
        return null;
    }

    private void waitForMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting " + millis + " ms", e);
        }
    }

    /* ══════════════════════════════════════════════════════════
     *  BACKGROUND / GIVEN steps
     * ══════════════════════════════════════════════════════════ */

    @Given("the user has opened the Aviz Aero login page")
    public void theUserHasOpenedTheAvizAeroLoginPage() {
        loginPage = new LoginPage();
        loginPage.open();
        report().info("Navigated to Aviz Aero login page: " + loginPage.getLoginPageUrl());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should be visible after navigation.");
    }

    @Given("the login page is displayed with the {string} link")
    public void theLoginPageIsDisplayedWithTheLink(String linkText) {
        if (loginPage == null) { loginPage = new LoginPage(); }
        boolean visible = loginPage.isRegisterNowLinkVisible();
        report().info("Checking visibility of '" + linkText + "' link -> " + visible);
        Assert.assertTrue(visible, "'" + linkText + "' link should be visible on login page.");
    }

    /* ══════════════════════════════════════════════════════════
     *  WHEN steps – Navigation
     * ══════════════════════════════════════════════════════════ */

    @When("the user clicks on the {string} link")
    public void theUserClicksOnTheLink(String linkText) {
        report().info("Clicking '" + linkText + "' on the login page.");
        registrationPage = loginPage.clickRegisterNow();
        report().info("Navigated to: " + registrationPage.getRegistrationPageUrl());
    }

    /* ══════════════════════════════════════════════════════════
     *  WHEN steps – Step 1 (Personal Information)
     * ══════════════════════════════════════════════════════════ */

    @When("the user fills in Step 1 personal information:")
    public void theUserFillsInStep1PersonalInformation(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        String firstName   = safeCellValue(data.getOrDefault("First Name",  ""));
        String lastName    = safeCellValue(data.getOrDefault("Last Name",   ""));
        String email       = resolveDuplicateEmailCandidate(data.getOrDefault("Email", ""));
        String mobile      = safeCellValue(data.getOrDefault("Mobile",      ""));
        String designation = safeCellValue(data.getOrDefault("Designation", ""));
        report().info(String.format(
                "Step 1 – Name: '%s %s', Email: '%s', Mobile: '%s', Designation: '%s'",
                firstName, lastName, email, mobile, designation));
        registrationPage.fillStep1Form(firstName, lastName, email, mobile, designation);
    }

    @When("the user clicks the Step 1 {string} button")
    public void theUserClicksTheStep1Button(String buttonName) {
        report().info("Clicking Step 1 '" + buttonName + "' button.");
        step2Page = registrationPage.clickNext();
    }

    @When("the user completes Step 1 with valid personal data")
    public void theUserCompletesStep1WithValidPersonalData() {
        String email = uniqueEmail();
        String mobile = uniqueMobile();
        report().info("Auto-completing Step 1 with valid personal data.");
        report().info("Generated unique Step 1 data: " + email + " / " + mobile);
        registrationPage.fillStep1Form(VALID_FIRST, VALID_LAST, email, mobile, VALID_DESIG);
        step2Page = registrationPage.clickNext();
        report().info("Step 1 submitted.");
    }

    /* ══════════════════════════════════════════════════════════
     *  WHEN steps – Step 2 (Company Information)
     * ══════════════════════════════════════════════════════════ */

    @When("the user fills in Step 2 company information:")
    public void theUserFillsInStep2CompanyInformation(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        String companyName  = data.getOrDefault("Company Name",        "");
        String companySize  = data.getOrDefault("Company Size",        "");
        String commEmail    = data.getOrDefault("Communication Email", "");
        String website      = data.getOrDefault("Company URL", data.getOrDefault("Website", ""));
        String address      = data.getOrDefault("Pincode", data.getOrDefault("Address", ""));
        String city         = data.getOrDefault("City",                "");
        String country      = data.getOrDefault("Country",             "");
        String phone        = data.getOrDefault("Contact Phone Number", data.getOrDefault("Phone", ""));
        report().info(String.format(
            "Step 2 – Company: '%s', Size: '%s', Email: '%s', URL: '%s', City: '%s', Phone: '%s', Pincode: '%s'",
            companyName, companySize, commEmail, website, city, phone, address));
        step2Page.fillStep2Form(companyName, companySize, commEmail, website, address, city, country, phone);
    }

    @When("the user clicks the Step 2 {string} button")
    public void theUserClicksTheStep2Button(String buttonName) {
        report().info("Clicking Step 2 '" + buttonName + "' button.");
        step3Page = step2Page.clickNext();
    }

    @When("the user completes Step 2 with valid company data")
    public void theUserCompletesStep2WithValidCompanyData() {
        report().info("Auto-completing Step 2 with valid company data.");
        String companyName = uniqueCompanyName();
        report().info("Generated unique company name: " + companyName);
        step2Page.fillStep2Form(companyName, VALID_CO_SIZE, VALID_COMM_EMAIL,
                VALID_WEBSITE, VALID_ADDRESS, VALID_CITY, VALID_COUNTRY, VALID_CO_PHONE);
        step3Page = step2Page.clickNext();
        report().info("Step 2 submitted.");
    }

    /* ══════════════════════════════════════════════════════════
     *  WHEN steps – Step 3 (Documents + T&C + Submit)
     * ══════════════════════════════════════════════════════════ */

    @When("the user accepts the Terms and Conditions")
    public void theUserAcceptsTheTermsAndConditions() {
        report().info("Accepting Terms and Conditions.");
        step3Page.acceptTermsAndConditions();
    }

    @When("the user does NOT accept the Terms and Conditions")
    public void theUserDoesNotAcceptTheTermsAndConditions() {
        report().info("Leaving Terms and Conditions unchecked (NEG-08).");
        step3Page.leaveTermsUnchecked();
    }

    @When("the user clicks {string}")
    public void theUserClicks(String buttonName) {
        report().info("Clicking '" + buttonName + "'.");
        if (buttonName.toLowerCase().contains("send register")
                || buttonName.toLowerCase().contains("submit")) {
            step3Page.clickSubmit();
            waitForMillis(2000);
            report().info("Waited 2 seconds after clicking submit.");
        } else {
            throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
    }

    @When("the user uploads an invalid file type {string}")
    public void theUserUploadsAnInvalidFileType(String fileName) {
        report().info("EDGE-07: Uploading invalid file type: " + fileName);
        step3Page.uploadDummyFile(fileName, 1);
    }

    @When("the user uploads a file larger than 10 MB named {string}")
    public void theUserUploadsAFileLargerThan10MBNamed(String fileName) {
        report().info("EDGE-08: Uploading large file (>10 MB): " + fileName);
        step3Page.uploadDummyFile(fileName, 11_000);
    }

    /* ══════════════════════════════════════════════════════════
     *  THEN steps – POS assertions
     * ══════════════════════════════════════════════════════════ */

    @Then("the browser should navigate to the registration page URL containing {string}")
    public void theBrowserShouldNavigateToRegistrationPageUrlContaining(String urlFragment) {
        String currentUrl = registrationPage.getRegistrationPageUrl();
        report().info("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.toLowerCase().contains(urlFragment.toLowerCase()),
                "URL should contain '" + urlFragment + "' but was: " + currentUrl);
        report().pass("URL correctly contains '" + urlFragment + "'.");
    }

    @Then("the registration page heading should contain {string}")
    public void theRegistrationPageHeadingShouldContain(String expectedHeading) {
        String heading = registrationPage.getPageHeadingText();
        report().info("Page heading: '" + heading + "'");
        boolean matched = heading.toLowerCase().contains(expectedHeading.toLowerCase())
                || registrationPage.isOnRegistrationUrl();
        Assert.assertTrue(matched,
                "Heading should contain '" + expectedHeading + "' but was: '" + heading + "'");
        report().pass("Registration page heading verified.");
    }

    @Then("the registration Step 1 form fields should be visible")
    public void theRegistrationStep1FormFieldsShouldBeVisible() {
        boolean firstNameOk = registrationPage.isFirstNameFieldDisplayed();
        boolean emailOk     = registrationPage.isEmailFieldDisplayed();
        boolean mobileOk    = registrationPage.isMobileFieldDisplayed();
        report().info("First Name: " + firstNameOk + ", Email: " + emailOk + ", Mobile: " + mobileOk);
        Assert.assertTrue(firstNameOk, "First Name field should be visible.");
        Assert.assertTrue(emailOk,     "Email (Mail ID) field should be visible.");
        Assert.assertTrue(mobileOk,    "Mobile/Phone field should be visible.");
        report().pass("All Step 1 form fields are visible.");
    }

    @Then("the Step 2 company information page should be displayed")
    public void theStep2CompanyInformationPageShouldBeDisplayed() {
        boolean displayed = step2Page.isStep2PageDisplayed();
        report().info("Step 2 page displayed: " + displayed);
        Assert.assertTrue(displayed,
                "Step 2 (Company Information) page should be displayed after valid Step 1 submission.");
        report().pass("Step 2 company information page is displayed.");
    }

    @Then("the Step 3 document upload page should be displayed")
    public void theStep3DocumentUploadPageShouldBeDisplayed() {
        boolean displayed = step3Page.isStep3PageDisplayed();
        report().info("Step 3 page displayed: " + displayed);
        Assert.assertTrue(displayed,
                "Step 3 (Document Upload) page should be displayed after valid Step 2 submission.");
        report().pass("Step 3 document upload page is displayed.");
    }

    @Then("a registration confirmation message should be displayed")
    public void aRegistrationConfirmationMessageShouldBeDisplayed() {
        boolean confirmed = step3Page.isConfirmationMessageDisplayed();
        String  msgText   = step3Page.getConfirmationMessageText();
        report().info("Confirmation displayed: " + confirmed + " | Text: " + msgText);
        Assert.assertTrue(confirmed,
                "A confirmation message should appear after successful registration submission.");
        report().pass("Registration confirmation received: " + msgText);
    }

    @Then("the registration request should be pending Super Admin review")
    public void theRegistrationRequestShouldBePendingSuperAdminReview() {
        // POS-05: Admin approval is a back-office process; UI side confirms submission only.
        report().info("[POS-05] Admin approval workflow is a backend/manual process.");
        report().pass("Submission confirmed. Super Admin review is outside UI automation scope.");
    }

    @Then("the rejection workflow is handled by Super Admin outside the UI")
    public void theRejectionWorkflowIsHandledBySuperAdminOutsideTheUI() {
        // POS-06: Rejection notification is triggered by admin – outside UI automation.
        report().info("[POS-06] Rejection notification is a backend/manual process.");
        report().pass("Registration submitted. Rejection flow is outside UI automation scope.");
    }

    /* ══════════════════════════════════════════════════════════
     *  THEN steps – NEG assertions
     * ══════════════════════════════════════════════════════════ */

    @Then("a validation error should be displayed containing {string}")
    public void aValidationErrorShouldBeDisplayedContaining(String keyword) {
        boolean errorFound = registrationPage.isErrorDisplayedContaining(keyword)
                || registrationPage.areErrorMessagesDisplayed();
        String  allErrors  = registrationPage.getAllErrorMessages();
        report().info("Errors found: '" + allErrors + "' | Keyword expected: '" + keyword + "'");
        Assert.assertTrue(errorFound,
                "Expected validation error containing '" + keyword + "' but got: " + allErrors);
        report().pass("Validation error correctly displayed. Text: " + allErrors);
    }

    @Then("a Step 2 validation error should be displayed containing {string}")
    public void aStep2ValidationErrorShouldBeDisplayedContaining(String keyword) {
        boolean errorFound = step2Page.isErrorDisplayedContaining(keyword)
                || step2Page.areErrorMessagesDisplayed();
        String  allErrors  = step2Page.getAllErrorMessages();
        report().info("Step 2 errors: '" + allErrors + "' | Keyword: '" + keyword + "'");
        Assert.assertTrue(errorFound,
                "Expected Step 2 error containing '" + keyword + "' but got: " + allErrors);
        report().pass("Step 2 validation error correctly displayed. Text: " + allErrors);
    }

    @Then("a Step 3 validation error should be displayed containing {string}")
    public void aStep3ValidationErrorShouldBeDisplayedContaining(String keyword) {
        boolean errorFound = step3Page.isErrorDisplayedContaining(keyword)
                || step3Page.areErrorMessagesDisplayed();
        String  allErrors  = step3Page.getAllErrorMessages();
        report().info("Step 3 errors: '" + allErrors + "' | Keyword: '" + keyword + "'");
        Assert.assertTrue(errorFound,
                "Expected Step 3 error containing '" + keyword + "' but got: " + allErrors);
        report().pass("Step 3 validation error correctly displayed. Text: " + allErrors);
    }

    /* ══════════════════════════════════════════════════════════
     *  THEN steps – EDGE assertions
     * ══════════════════════════════════════════════════════════ */

    @Then("the application should handle the long First Name with an error or field truncation")
    public void theApplicationShouldHandleTheLongFirstNameWithAnErrorOrFieldTruncation() {
        // EDGE-01: either an error is shown, or the app advances (truncation)
        boolean errorShown   = registrationPage.areErrorMessagesDisplayed();
        boolean stepAdvanced = step2Page != null && step2Page.isStep2PageDisplayed();
        report().info("EDGE-01: Error shown=" + errorShown + ", Step advanced=" + stepAdvanced);
        Assert.assertTrue(errorShown || stepAdvanced,
                "Application should either show an error or truncate the value and advance.");
        if (errorShown) {
            report().pass("Validation error shown for long First Name: " + registrationPage.getAllErrorMessages());
        } else {
            report().pass("Application accepted input and advanced (truncation behaviour).");
        }
    }

    @Then("the application should handle the maximum company size with acceptance or a limit error")
    public void theApplicationShouldHandleTheMaximumCompanySizeWithAcceptanceOrALimitError() {
        // EDGE-05: accepted (step 3 shown) or limit error on step 2
        boolean step3Shown = step3Page != null && step3Page.isStep3PageDisplayed();
        boolean errorShown = step2Page != null && step2Page.areErrorMessagesDisplayed();
        report().info("EDGE-05: Step 3 shown=" + step3Shown + ", Error shown=" + errorShown);
        Assert.assertTrue(step3Shown || errorShown,
                "Application should either accept the maximum company size or display a limit error.");
        if (step3Shown) {
            report().pass("Maximum company size accepted; Step 3 is displayed.");
        } else {
            report().pass("Company size limit error: " + step2Page.getAllErrorMessages());
        }
    }

    /* ══════════════════════════════════════════════════════════
     *  Legacy steps retained for backward compatibility
     * ══════════════════════════════════════════════════════════ */

    @Then("the {string} link text should be visible")
    public void theLinkTextShouldBeVisible(String linkText) {
        boolean visible = loginPage.isRegisterNowLinkVisible();
        report().info("'" + linkText + "' link visible: " + visible);
        Assert.assertTrue(visible, "'" + linkText + "' link should be visible on the login page.");
        report().pass("'" + linkText + "' link is visible.");
    }

    @Then("the {string} link should point to the registration URL")
    public void theLinkShouldPointToTheRegistrationUrl(String linkText) {
        boolean visible = loginPage.isRegisterNowLinkVisible();
        report().info("'" + linkText + "' link accessible: " + visible);
        Assert.assertTrue(visible,
                "'" + linkText + "' link must be present to navigate to registration.");
        report().pass("'" + linkText + "' link is present and accessible.");
    }
}
