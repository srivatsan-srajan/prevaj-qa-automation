package com.prevaj.evaluation.stepdef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.prevaj.evaluation.config.FrameworkConfig;
import com.prevaj.evaluation.stepdef.context.UiScenarioContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UiTestSteps {

	private static final int TAX_DECIMAL_PLACES = 2;
	private static final BigDecimal PERCENTAGE_DIVISOR = BigDecimal.valueOf(100);

	private final UiScenarioContext context;

	public UiTestSteps(UiScenarioContext context) {
		this.context = context;
	}

	@Given("I am on the home page")
	public void navigateToHomePage() {
		context.getDriver().get(FrameworkConfig.get("ui.base.url"));
	}

	@Given("I login in with the following details")
	public void login(DataTable dataTable) {
		Map<String, String> credentials = dataTable.asMaps().get(0);
		context.getLoginPage().performLogin(
				credentials.get("userName"),
				credentials.get("Password")
		);
	}

	@When("I login with username {string} and password {string}")
	public void loginWithCredentials(String username, String password) {
		context.getLoginPage().performLogin(username, password);
	}

	@Then("I should see the products page")
	public void verifyProductsPage() {
		assertTrue(context.getProductsPage().isDisplayed(), "Products page was not displayed after login");
	}

	@Then("I should see the login error {string}")
	public void verifyLoginError(String expectedError) {
		assertEquals(expectedError, context.getLoginPage().getLoginError(), "Unexpected login error message");
	}

	@Then("I should remain on the login page")
	public void verifyLoginPageDisplayed() {
		assertTrue(context.getLoginPage().isLoginPageDisplayed(), "User should remain on the login page");
	}

	@Given("I add the following items to the basket")
	public void addItemsToBasket(DataTable dataTable) {

		List<String> selectedProducts = context.getSelectedProducts();

		selectedProducts.clear();
		selectedProducts.addAll(dataTable.asList());

		for (String product : selectedProducts) {
			context.getProductsPage().addProductToCart(product);
		}
	}

	@Then("I should see {int} items added to the shopping cart")
	public void verifyShoppingCartCount(int expectedCount) {
		assertEquals(expectedCount, context.getCartPage().waitForCartItemCount(expectedCount),
				"Shopping cart count does not match the expected count");
	}

	@Given("I click on the shopping cart")
	public void openShoppingCart() {
		context.getProductsPage().openShoppingCart();
	}

	@Given("I verify that the QTY count for each selected item should be {int}")
	public void verifyItemQuantities(int expectedQuantity) {

		for (String product : context.getSelectedProducts()) {
			assertEquals(expectedQuantity, context.getCartPage().getProductQuantity(product),
					"Incorrect quantity for product: " + product);
		}
	}

	@Given("I remove the following items:")
	public void removeItem(DataTable dataTable) {

		List<String> products = dataTable.asList();

	    for (String product : products) {

	        assertTrue(context.getSelectedProducts().contains(product),
	                "Product was not selected: " + product);

	        context.getCartPage().removeProduct(product);
	        context.getSelectedProducts().remove(product);
	    }
	}

	@Given("I click on the CHECKOUT button")
	public void clickCheckout() {
		context.getCartPage().checkout();
	}

	@Given("I type {string} for First Name")
	public void enterFirstName(String firstName) {
		context.getCheckoutPage().enterFirstName(firstName);
	}

	@Given("I type {string} for Last Name")
	public void enterLastName(String lastName) {
		context.getCheckoutPage().enterLastName(lastName);
	}

	@Given("I type {string} for ZIP\\/Postal Code")
	public void enterPostalCode(String postalCode) {
		context.getCheckoutPage().enterPostalCode(postalCode);
	}

	@When("I click on the CONTINUE button")
	public void continueToOverview() {
		context.getCheckoutPage().continueToOverview();
	}

	@When("I submit the checkout information")
	public void submitCheckoutInformation() {
		context.getCheckoutPage().submitInformation();
	}

	@Then("I should see the checkout error {string}")
	public void verifyCheckoutError(String expectedError) {
		assertEquals(expectedError, context.getCheckoutPage().getCheckoutError(),
				"Unexpected checkout validation message");
	}

	@Then("I should remain on the checkout information page")
	public void verifyCheckoutInformationPage() {
		assertTrue(context.getCheckoutPage().isCheckoutInformationPageDisplayed(),
				"User should remain on the checkout information page");
	}

	@Then("Item total will be equal to the total of items on the list")
	public void verifyItemSubtotal() {

		BigDecimal expectedSubtotal = context.getCheckoutPage().getItemPrices().stream().reduce(BigDecimal.ZERO,
				BigDecimal::add);

		context.setExpectedSubtotal(expectedSubtotal);

		assertEquals(0, expectedSubtotal.compareTo(context.getCheckoutPage().getItemSubtotal()),
				"Checkout subtotal does not match the sum of item prices");
	}

	@Then("a Tax rate of {int} % is applied to the total")
	public void verifyTaxRate(int expectedTaxRate) {

		BigDecimal expectedSubtotal = context.getExpectedSubtotal();

		BigDecimal expectedTax = expectedSubtotal.multiply(BigDecimal.valueOf(expectedTaxRate))
				.divide(PERCENTAGE_DIVISOR, TAX_DECIMAL_PLACES, RoundingMode.HALF_UP);

		BigDecimal expectedTotal = expectedSubtotal.add(expectedTax);

		assertEquals(0, expectedTax.compareTo(context.getCheckoutPage().getTax()),
				"Displayed tax does not match the expected tax rate");

		assertEquals(0, expectedTotal.compareTo(context.getCheckoutPage().getTotal()),
				"Displayed total does not match subtotal plus tax");
	}
}
