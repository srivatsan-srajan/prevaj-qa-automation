PREVAJ QA AUTOMATION FRAMEWORK

1. PROJECT OVERVIEW

This is a Maven-based API and UI automation project using Java, Cucumber, REST Assured and Selenium WebDriver.

The API scenarios cover user retrieval, pagination, user creation, delayed responses and login success/failure.

The UI scenarios cover login, product selection, cart validation, item removal and checkout price/tax validation.

The framework supports configurable browser execution, headless execution, parallel UI testing and automated CI/CD execution through GitHub Actions.

2. TECHNOLOGY STACK

- Java 25
- Maven
- Cucumber 7.33.0
- JUnit 5 / JUnit Platform
- REST Assured 5.5.6
- Selenium WebDriver 4.39.0
- Jackson 2.20.1
- GitHub Actions

Refer to pom.xml for the exact dependency versions.

3. FRAMEWORK CAPABILITIES & SCALABILITY

- API Extension: New API modules can be added using the existing API client structure. Common settings and request handling are reused, while each module can have its own endpoints, payloads and operations.
- Authentication Extension: Authentication is handled separately from API operations. This makes it easier to add or change authentication methods without changing the API modules.
- Browser Support: New browsers can be added through the DriverFactory. Browser creation and configuration are handled in one place, so the rest of the UI tests do not need browser-specific changes.
- Reusable UI Components: Common page actions and wait methods are available through BasePage. Page Objects can reuse these methods, and new pages can add their own locators and actions.
- Test Data and Shared Context: New scenarios can use Cucumber DataTables and Scenario Outlines to provide different test data. PicoContainer shares scenario-specific data between step definitions, without using static variables or mixing data between scenarios.
- Parallel and Headless Execution: UI tests can run in parallel with separate browser instances for each thread. Headless execution is also supported, allowing tests to run in CI without opening a visible browser.
- Configuration: Browser, application URLs, timeouts and execution settings can be changed through configuration or system properties without modifying the source code.
- CI/CD Extension: API and UI tests run as separate jobs in the GitHub Actions pipeline. More test groups, execution environments and pipeline jobs can be added as the project grows. Test reports are uploaded as separate artifacts for review.

4. PROJECT STRUCTURE AND RESPONSIBILITIES

src/test/java/com/prevaj/evaluation

- api/auth
  - Contains API authentication-related code.

- api/clients
  - BaseApiClient: common API settings such as base URL, JSON headers and authentication.
  - ReqResApiClient: sends HTTP requests to the ReqRes user and login endpoints.

- api/modules
  - UserModule: handles user-related operations, including retrieving users across multiple pages and converting responses into Java objects.

- api/payloads
  - LoginPayload, UserPayload and UserListResponse: Java classes used to store API request and response data.

- config
  - FrameworkConfig: reads framework.properties and allows values to be overridden through system properties. API keys can be supplied through an environment variable or system property.

- diagnostics
  - Contains code used to collect information when a test fails. Screenshots for failed scenarios are captured for all browsers while logs are collected only for Chrome for now.

- hooks
  - CucumberUiHooks: starts and closes the browser for UI tests. It also captures screenshots when a UI test fails.

- runners
  - TestRunner: starts Cucumber tests through JUnit Platform.

- stepdef
  - ApiTestSteps and UiTestSteps: contain the code that connects feature-file steps to API operations and UI page actions, and checks the test results.

- ui/driver
  - DriverFactory: creates Chrome, Firefox or Edge drivers and applies browser settings, including headless mode.
  - DriverManager: manages separate WebDriver instances for parallel test execution using ThreadLocal.

- ui/pages
  - BasePage: contains common methods for waiting for elements and interacting with the page.
  - LoginPage, ProductsPage, CartPage and CheckoutPage: contain the actions and checks for their respective application pages.

- UiScenarioContext and ApiScenarioContext classes store data used during each test scenario. PicoContainer is used to share this data between step definitions.

src/test/resources

- features: contains API-Test.feature and UI-Test.feature, which define the test scenarios.
- config/framework.properties: contains default application URLs, browser settings and timeout values.

5. DESIGN DECISIONS

- API requests are handled in API clients. UserModule contains the operations related to users, while step definitions handle the test steps and verify the results.
- Common API settings are kept in BaseApiClient so they can be reused by different API clients.
- UserModule handles pagination by retrieving each page, checking the response and combining the results.
- API request and response data is stored in Java POJO classes. Unknown JSON fields are ignored so that adding new fields in the API response does not break the tests.
- Page Objects contain the locators and actions for each application page. BasePage provides common methods for waiting and interacting with page elements.
- UI test data, such as product names and tax rate, is provided through feature files. The expected subtotal is calculated using the item prices displayed on the checkout page. Tax and total amounts are calculated using BigDecimal to avoid rounding issues.
- Browser type, headless mode, application URLs and timeout values can be changed through configuration without modifying the code.
- Browser setup and cleanup are handled only for scenarios tagged with @ui. When a UI test fails, a screenshot is captured and attached to the Cucumber report.
- Test data is stored in instance variables rather than static variables. This prevents data from one scenario from being shared with another scenario.
- PicoContainer is used to share the required objects and test data between step-definition classes within the same scenario.
- ThreadLocal is used to maintain a separate WebDriver instance for each thread. This allows UI scenarios to run in parallel without different tests using the same browser instance.

6. CONFIGURATION

Default values are maintained in src/test/resources/config/framework.properties.

System properties override the corresponding property-file values.

For API credentials, the environment variable REQRES_API_KEY takes precedence over the api.key system property.

API credentials should not be committed to source control or exposed in shared console logs.

7. EXECUTION

- Run all scenarios:

	mvn clean test -Dapi.key=YOUR_API_KEY -Dbrowser=firefox

- Run API scenarios:

	mvn clean test -Dcucumber.filter.tags="@api" -Dapi.key=YOUR_API_KEY

- Run UI scenarios using the configured browser:

	mvn clean test -Dcucumber.filter.tags="@ui"

- Run UI scenarios using Chrome:

	mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome

- Run UI scenarios in headless Chrome:

	mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome -Dheadless=true

- Run UI scenarios in parallel using three threads:

	mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=firefox -Dheadless=true -Dparallel.enabled=true -Dparallel.count=3

- Alternatively, set the API key in the environment:

	export REQRES_API_KEY=YOUR_API_KEY

	mvn clean test

The API key can also be supplied through the Maven system property -Dapi.key.

8. CI/CD PIPELINE

GitHub Actions is used to automate API and UI test execution.

Workflow location:

.github/workflows/prevaj-qa-automation.yml

The pipeline is triggered by:

- Push to the main branch.
- Pull requests against the main branch.
- Manual execution through workflow_dispatch.

The pipeline consists of two independent jobs that execute concurrently.

API Automation Job:

- Checks out the source code.
- Configures Java 25 using Temurin.
- Retrieves the API key from the GitHub repository secret REQRES_API_KEY.
- Executes API scenarios using the @api tag.
- Publishes Surefire and Cucumber reports.

UI Automation Job:

- Checks out the source code.
- Configures Java 25 using Temurin.
- Executes Firefox UI scenarios in headless mode.
- Executes UI scenarios in parallel using three threads.
- Publishes Surefire and Cucumber reports.

Test reports are published as four separate GitHub Actions artifacts:

- api-cucumber-report.html
- api-surefire-reports
- ui-cucumber-report.html
- ui-surefire-reports

Cucumber reports are uploaded as standalone HTML files for quick and easy readability. Surefire reports are retained in their original format.

All artifacts are retained for 14 days.

The API key is configured under GitHub repository Settings > Secrets and variables > Actions.

9. REPORTING

The Cucumber HTML report is generated at:

target/cucumber-report/cucumber.html

It provides feature-level, scenario-level and step-level execution results, including execution durations.

For failed UI scenarios, a screenshot is attached to the Cucumber scenario.

Maven Surefire reports are generated at:

target/surefire-reports/

These include XML and TXT execution results, providing machine-readable test outcomes and failure diagnostics for CI integration.

10. ISSUES AND RESOLUTION

- API Authentication:
  ReqRes required an API key to access its endpoints. Instead of hardcoding the key, it was made configurable and supplied through an environment variable or Maven system property.
- JSON Response Mapping:
  API responses could contain additional fields that were not defined in the Java response classes. The response models were configured to ignore unknown fields so that these additional fields would not cause deserialization failures.
- API Pagination:
  The user API returned data across multiple pages. Pagination was implemented in UserModule to retrieve users from all available pages and combine the results.
- Cucumber Step Matching:
  Some step definitions contained special characters that were not matching the corresponding feature-file steps. The characters were properly escaped to ensure correct step matching.
- Dynamic UI Elements:
  Some UI elements took time to appear or update, especially during cart updates and page navigation. Explicit waits were added to wait for the required elements and page conditions instead of using fixed delays.
- Checkout Validation Locator:
  The checkout error message locator did not initially match the application's actual HTML attribute. It was corrected to use the right data-test selector so that validation messages could be captured correctly.
- Checkout Mandatory-Field Validation:
  Initially, separate scenarios were used to check missing first name, last name and postal code. This repeated the browser setup and checkout navigation. These checks were combined into a single scenario, where the form is submitted multiple times while entering each required field. The application retains previously entered values after validation failures, making this approach possible.
- Scoped Browser Setup and Teardown:
  Browser setup was initially applicable beyond UI scenarios. The hooks were restricted to scenarios tagged with @ui so that API scenarios do not start unnecessary browser instances. Browser cleanup is also handled after each UI scenario.
- Chrome Browser Options:
  Chrome displayed a generic password dialog after login. Custom Chrome options were added to prevent this dialog from interfering with automated test execution.
- Parallel UI Execution:
  Running UI scenarios in parallel requires each test to have its own browser and test data. ThreadLocal was used to maintain separate WebDriver instances, and scenario-specific data was managed through PicoContainer instead of static variables. Parallel execution was tested locally using three threads.
- Headless Browser Execution:
  UI tests were initially run using a visible browser. Headless execution was later configured and tested locally so that the same tests could run in CI without opening a visible browser.
