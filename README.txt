PREVAJ QA AUTOMATION FRAMEWORK

1. PROJECT OVERVIEW

This is a Maven-based API and UI automation project using Java, Cucumber,
REST Assured and Selenium WebDriver.

The API scenarios cover user retrieval, pagination, user creation, delayed
responses and login success/failure. The UI scenario covers login, product
selection, cart validation, item removal and checkout price/tax validation.

2. TECHNOLOGY STACK

- Java 25
- Maven
- Cucumber 7
- JUnit 5 / JUnit Platform
- REST Assured
- Selenium WebDriver
- Jackson

Refer to pom.xml for the exact dependency versions.

3. PROJECT STRUCTURE AND RESPONSIBILITIES

src/test/java/com/prevaj/evaluation

- api/clients
  - BaseApiClient: common base URI, JSON headers and API authentication.
  - ReqResApiClient: HTTP operations for the ReqRes user and login endpoints.

- api/modules
  - UserModule: user workflows, including retrieving all pages and mapping
    API responses to payload objects.

- api/payloads
  - LoginPayload, UserPayload and UserListResponse: request/response models.

- config
  - FrameworkConfig: loads framework.properties and allows system-property
    overrides. API credentials are read from an environment variable or
    Maven system property.

- ui/driver
  - DriverFactory: creates Chrome, Firefox or Edge and applies browser options,
    including optional headless execution.

- ui/pages
  - BasePage: common WebDriver wait and element interaction methods.
  - LoginPage, ProductsPage, CartPage and CheckoutPage: page-specific actions
    and data retrieval.

- stepdef
  - APITestSteps and UITestSteps: connect Gherkin steps to the API modules and
    Page Objects, and perform scenario assertions.

- runners
  - TestRunner: JUnit Platform entry point for Cucumber.

src/test/resources

- features: API-Test.feature and UI-Test.feature.
- config/framework.properties: default URLs, browser and timeout settings.

4. DESIGN DECISIONS

- HTTP communication is kept in API clients. UserModule contains user-level
  operations, while step definitions contain test orchestration and assertions.
- Common API configuration is reused through BaseApiClient.
- Pagination is handled in UserModule. Each page response is checked before
  its data is added to the combined result.
- API request/response data is represented by POJOs. Unknown JSON properties
  are ignored so additional fields do not break deserialization.
- Page Objects encapsulate locators and UI interactions. BasePage contains
  common wait and interaction behaviour.
- UI test data such as product names and tax rate is supplied in the feature
  file. The expected checkout subtotal is calculated from the item prices
  displayed on the checkout page; tax and total are calculated with BigDecimal.
- Browser selection, headless mode, URLs and timeout values are configurable.
- UI setup and teardown are scoped to @ui scenarios. A screenshot is attached
  to the Cucumber scenario when a UI test fails.
- Step-definition state is held in instance fields, not static fields, so it
  remains scoped to the Cucumber scenario lifecycle.

5. CONFIGURATION

Default values are in src/test/resources/config/framework.properties.
System properties override the corresponding property-file values. For
secrets, the environment variable REQRES_API_KEY takes precedence over the
api.key system property.

6. EXECUTION

Run all scenarios:

  mvn clean test -Dapi.key=YOUR_API_KEY

Run API scenarios:

  mvn clean test -Dcucumber.filter.tags="@api" -Dapi.key=YOUR_API_KEY

Run UI scenarios using the configured browser:

  mvn clean test -Dcucumber.filter.tags="@ui"

Run UI scenarios using Chrome:

  mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome

Run UI scenarios in headless Chrome:

  mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome -Dheadless=true

Alternatively, set the API key in the environment:

  export REQRES_API_KEY=YOUR_API_KEY
  mvn clean test

Do not commit API keys or include them in shared console logs.

7. REPORTING

The Cucumber HTML report is generated at:

  target/cucumber-report/cucumber.html

For failed UI scenarios, a screenshot is attached to the Cucumber scenario.

8. EXTENSION POINTS

- Additional API domains can extend BaseApiClient and provide their own client,
  payload models and module class.
- Additional UI pages can extend BasePage to reuse the common wait behaviour.
- New scenarios can supply their own data through Cucumber DataTables and
  Scenario Outlines without embedding product combinations in step code.
- Browser and environment settings can be changed through system properties
  without modifying the source code.
