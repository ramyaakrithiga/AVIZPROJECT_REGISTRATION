@RegistrationSuite @Regression
Feature: Aviz Aero Registration Flow
  As a new user visiting https://demo.aviz.aero
  I want to navigate from the Login page through the 3-step Registration wizard
  So that I can create a new account on the Aviz Aero platform

  Background:
    Given the user has opened the Aviz Aero login page

  # ═══════════════════════════════════════════════════════════
  # POSITIVE SCENARIOS  (POS-01 → POS-06)
  # ═══════════════════════════════════════════════════════════

  # POS-01
  @Positive @Smoke @POS-01
  Scenario: POS-01 – Verify Aviz login page loads and Register Now navigates to registration page
    Given the login page is displayed with the "Register Now" link
    When the user clicks on the "Register Now" link
    Then the browser should navigate to the registration page URL containing "/register"
    And the registration page heading should contain "Create your Aviz Account"
    And the registration Step 1 form fields should be visible

  # POS-02
  @Positive @Functional @POS-02
  Scenario Outline: POS-02 – Register with valid personal information (Step 1)
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name  | Last Name  | Email   | Mobile   | Designation   |
      | <firstName> | <lastName> | <email> | <mobile> | <designation> |
    And the user clicks the Step 1 "Next" button
    Then the Step 2 company information page should be displayed

    Examples:
      | firstName | lastName | email                     | mobile     | designation |
      | John      | Doe      | john.doe@testmail.com     | 9876543210 | QA Engineer |
      | Jane      | Smith    | jane.smith@aviztest.qa    | 8123456789 | Developer   |

  # POS-03
  @Positive @Functional @POS-03
  Scenario: POS-03 – Register with valid company information (Step 2)
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size     | Communication Email  | Company URL   | City   | Contact Phone Number | Pincode |
      | Aviz Corp    | 11-50 employees  | contact@avizcorp.com | avizcorp.com  | Berlin | 9000000001           | 10115   |
    And the user clicks the Step 2 "Next" button
    Then the Step 3 document upload page should be displayed

  # POS-04
  @Positive @Functional @POS-04
  Scenario: POS-04 – Submit registration with optional documents and accepted T&C
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user accepts the Terms and Conditions
    And the user clicks "Send register request"
    Then a registration confirmation message should be displayed

  # POS-05
  @Positive @Functional @POS-05 @ManualVerification
  Scenario: POS-05 – Approval workflow – valid submission leads to admin approval and credential email
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user accepts the Terms and Conditions
    And the user clicks "Send register request"
    Then a registration confirmation message should be displayed
    And the registration request should be pending Super Admin review

  # POS-06
  @Positive @Functional @POS-06 @ManualVerification
  Scenario: POS-06 – Approval workflow – invalid submission leads to rejection notification
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user accepts the Terms and Conditions
    And the user clicks "Send register request"
    Then a registration confirmation message should be displayed
    And the rejection workflow is handled by Super Admin outside the UI

  # ═══════════════════════════════════════════════════════════
  # NEGATIVE SCENARIOS  (NEG-01 → NEG-08)
  # ═══════════════════════════════════════════════════════════

  # NEG-01
  @Negative @Validation @NEG-01
  Scenario: NEG-01 – Missing required First Name shows validation error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email                 | Mobile     | Designation |
      |            | Kumar     | test.user@aviztest.qa | 9000000001 | Analyst     |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "First Name"

  # NEG-02
  @Negative @Validation @NEG-02
  Scenario: NEG-02 – Invalid email format shows validation error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email          | Mobile     | Designation |
      | Ramya      | Test      | ramya @@ gmail | 9876500001 | Tester      |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "email"

  # NEG-03
  @Negative @Validation @NEG-03
  Scenario: NEG-03 – Duplicate email shows "Email already exists" error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email                       | Mobile     | Designation |
      | Duplicate  | User      | existing.user@avizdemo.aero | 9111111111 | Manager     |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "already exists"

  # NEG-04
  @Negative @Validation @NEG-04
  Scenario: NEG-04 – Duplicate mobile number shows "Mobile number already exists" error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email                    | Mobile     | Designation |
      | Dup        | Mobile    | fresh.email@aviztest.qa  | 9000000000 | Analyst     |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "mobile"

  # NEG-05
  @Negative @Validation @NEG-05
  Scenario: NEG-05 – Missing Company Name on Step 2 shows validation error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size     | Communication Email  | Company URL  | City   | Contact Phone Number | Pincode |
      |              | 11-50 employees  | ops@testcompany.qa   | testco.qa    | Munich | 9200000001           | 80331   |
    And the user clicks the Step 2 "Next" button
    Then a Step 2 validation error should be displayed containing "Company Name"

  # NEG-06
  @Negative @Validation @NEG-06
  Scenario: NEG-06 – Invalid company email format shows validation error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size      | Communication Email | Company URL  | City      | Contact Phone Number | Pincode |
      | Verizon Test | 51-200 employees  | verizon@.com        | verizon.com  | New York  | 9300000001           | 10001   |
    And the user clicks the Step 2 "Next" button
    Then a Step 2 validation error should be displayed containing "email"

  # NEG-07
  @Negative @Validation @NEG-07
  Scenario: NEG-07 – Missing Pincode on Step 2 shows validation error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size     | Communication Email | Company URL   | City    | Contact Phone Number | Pincode |
      | Test Corp    | 11-50 employees  | info@testcorp.qa    | testcorp.qa   | Hamburg | 9400000001           |         |
    And the user clicks the Step 2 "Next" button
    Then a Step 2 validation error should be displayed containing "Pincode"

  # NEG-08
  @Negative @Validation @NEG-08
  Scenario: NEG-08 – T&C not accepted shows validation error on submission
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user does NOT accept the Terms and Conditions
    And the user clicks "Send register request"
    Then a Step 3 validation error should be displayed containing "terms"

  # ═══════════════════════════════════════════════════════════
  # EDGE CASE SCENARIOS  (EDGE-01 → EDGE-08)
  # ═══════════════════════════════════════════════════════════

  # EDGE-01
  @Edge @Boundary @EDGE-01
  Scenario: EDGE-01 – Very long First Name (100+ characters) shows error or truncation
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name                                                                                           | Last Name | Email                    | Mobile     | Designation |
      | AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA | Edge      | edge.long@aviztest.qa    | 9500000001 | Tester      |
    And the user clicks the Step 1 "Next" button
    Then the application should handle the long First Name with an error or field truncation

  # EDGE-02
  @Edge @Boundary @EDGE-02
  Scenario: EDGE-02 – Special characters in First Name show invalid characters error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email                     | Mobile     | Designation |
      | Ramya@123  | Test      | edge.special@aviztest.qa  | 9500000002 | QA          |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "Invalid characters"

  # EDGE-03
  @Edge @Boundary @EDGE-03
  Scenario: EDGE-03 – Mobile number with extra digits (15-digit) shows invalid length error
    When the user clicks on the "Register Now" link
    And the user fills in Step 1 personal information:
      | First Name | Last Name | Email                    | Mobile          | Designation |
      | Edge       | Mobile    | edge.mobile@aviztest.qa  | 987654321012345 | Developer   |
    And the user clicks the Step 1 "Next" button
    Then a validation error should be displayed containing "mobile"

  # EDGE-04
  @Edge @Boundary @EDGE-04
  Scenario: EDGE-04 – Company size minimum boundary value (1 employee) is accepted
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size     | Communication Email | Company URL      | City   | Contact Phone Number | Pincode |
      | Sole Trader  | 1-10 employees   | solo@soletrader.qa  | soletrader.qa    | Berlin | 9600000001           | 10115   |
    And the user clicks the Step 2 "Next" button
    Then the Step 3 document upload page should be displayed

  # EDGE-05
  @Edge @Boundary @EDGE-05
  Scenario: EDGE-05 – Company size maximum boundary value is accepted or shows limit error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size     | Communication Email | Company URL   | City    | Contact Phone Number | Pincode |
      | Mega Corp    | 500+ employees   | info@megacorp.qa    | megacorp.qa   | Hamburg | 9700000001           | 20095   |
    And the user clicks the Step 2 "Next" button
    Then the application should handle the maximum company size with acceptance or a limit error

  # EDGE-06
  @Edge @Boundary @EDGE-06
  Scenario: EDGE-06 – Website field is optional – blank value should not block progress
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user fills in Step 2 company information:
      | Company Name | Company Size      | Communication Email | Company URL | City      | Contact Phone Number | Pincode |
      | No-Web Corp  | 11-50 employees   | contact@noweb.qa    |             | Frankfurt | 9800000001           | 60311   |
    And the user clicks the Step 2 "Next" button
    Then the Step 3 document upload page should be displayed

  # EDGE-07
  @Edge @FileUpload @EDGE-07
  Scenario: EDGE-07 – Upload invalid file type (.exe) shows "Invalid file type" error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user uploads an invalid file type "malware.exe"
    And the user accepts the Terms and Conditions
    And the user clicks "Send register request"
    Then a Step 3 validation error should be displayed containing "file type"

  # EDGE-08
  @Edge @FileUpload @EDGE-08
  Scenario: EDGE-08 – Upload file exceeding 10 MB shows "File size exceeds limit" error
    When the user clicks on the "Register Now" link
    And the user completes Step 1 with valid personal data
    And the user completes Step 2 with valid company data
    And the user uploads a file larger than 10 MB named "large_document.pdf"
    And the user accepts the Terms and Conditions
    And the user clicks "Send register request"
    Then a Step 3 validation error should be displayed containing "size"
