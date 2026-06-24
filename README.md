# Aviz Aero Registration – BDD Automation Framework

A **Selenium + Cucumber + TestNG** BDD test automation framework targeting the  
[Aviz Aero demo application](https://demo.aviz.aero), specifically the *Register Now* flow.

---

## Project Structure

```
AvizProject_Registration/
├── pom.xml                          # Maven build & dependency configuration
├── testng.xml                       # TestNG suite configuration
│
├── src/
│   ├── main/java/com/aviz/framework/
│   │   ├── config/
│   │   │   └── ConfigReader.java    # Reads config.properties (singleton)
│   │   ├── driver/
│   │   │   └── DriverManager.java   # Thread-local WebDriver lifecycle manager
│   │   └── utils/
│   │       ├── WaitUtils.java       # Explicit-wait helpers
│   │       ├── ExtentReportManager.java  # ExtentReports singleton
│   │       └── ScreenshotUtils.java # Screenshot capture utilities
│   │
│   └── test/
│       ├── java/com/aviz/tests/
│       │   ├── hooks/
│       │   │   └── Hooks.java       # @Before/@After – driver init, reporting, screenshots
│       │   ├── pages/
│       │   │   ├── BasePage.java    # Common POM base class
│       │   │   ├── LoginPage.java   # Login page interactions
│       │   │   └── RegistrationPage.java  # Step-1 registration form
│       │   ├── stepdefinitions/
│       │   │   └── RegistrationSteps.java # Cucumber step definitions
│       │   └── runners/
│       │       └── TestRunner.java  # Cucumber + TestNG entry point
│       │
│       └── resources/
│           ├── features/
│           │   └── Registration.feature  # Gherkin BDD scenarios
│           ├── config.properties    # Runtime configuration
│           ├── extent.properties    # ExtentReports adapter config
│           └── spark-config.json    # Spark reporter theme config
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 11 or higher |
| Maven | 3.8+ |
| Google Chrome | Latest stable |
| ChromeDriver | Auto-managed by WebDriverManager |

---

## Configuration

Edit `src/test/resources/config.properties` to customise the run:

```properties
browser=chrome          # chrome | firefox | edge
base.url=https://demo.aviz.aero
implicit.wait=10        # seconds
explicit.wait=15        # seconds
headless=false          # true for CI/headless environments
screenshot.on.failure=true
```

---

## Running Tests

### Run all tests
```bash
mvn test
```

### Run only Smoke tests
```bash
mvn test -Dcucumber.filter.tags="@Smoke"
```

### Run only Negative / Validation tests
```bash
mvn test -Dcucumber.filter.tags="@Negative"
```

### Run with Firefox
```bash
mvn test -Dbrowser=firefox
```

### Run in headless mode
```bash
mvn test -Dheadless=true
```

### Dry-run (check step mappings without executing)
Set `dryRun = true` in `TestRunner.java`, then run `mvn test`.

---

## Reports

After execution, reports are generated in the `target/` folder:

| Report | Location |
|--------|----------|
| Cucumber HTML | `target/cucumber-reports/cucumber.html` |
| Cucumber JSON | `target/cucumber-reports/cucumber.json` |
| ExtentReports Spark | `target/extent-reports/AvizSparkReport.html` |
| Timestamped Extent | `target/extent-reports/AvizReport_<timestamp>.html` |
| Screenshots | `target/screenshots/` |

---

## BDD Scenarios Covered

### Positive Scenarios (`@Positive`)
1. **Navigation** – Click "Register Now" from login page → verify URL contains `/register`.  
2. **Form fields visible** – All 5 Step-1 fields (First Name, Last Name, Mail ID, Phone, Designation) and "Next" button are displayed.  
3. **Valid submission** – Filling all fields with valid data and clicking Next proceeds without errors (Scenario Outline with 2 data sets).

### Negative Scenarios (`@Negative`)
4. **Empty form** – Clicking Next with no input triggers validation errors.  
5. **Invalid email** – Entering a malformed email triggers validation errors.  
6. **Missing First Name** – Required field left blank triggers validation errors.  
7. **Spaces-only input** – Blank fields trigger validation errors.  
8. **Link accessibility** – "Register Now" link is present on the login page.

---

## Extending the Framework

- **New page?** Create a class in `com.aviz.tests.pages` extending `BasePage`.
- **New feature?** Add a `.feature` file under `src/test/resources/features/`.
- **New steps?** Add methods to an existing step-def class or create a new one in `com.aviz.tests.stepdefinitions`.
- **New browser?** Add a factory method in `DriverManager` and add the case to the switch.
