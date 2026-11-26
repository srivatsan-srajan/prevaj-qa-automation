
package com.prevaj.evaluation.stepdef.context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.prevaj.evaluation.ui.driver.DriverManager;
import com.prevaj.evaluation.ui.pages.CartPage;
import com.prevaj.evaluation.ui.pages.CheckoutPage;
import com.prevaj.evaluation.ui.pages.LoginPage;
import com.prevaj.evaluation.ui.pages.ProductsPage;

public class UIScenarioContext {

	private LoginPage loginPage;
	private ProductsPage productsPage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;

	private final List<String> selectedProducts = new ArrayList<>();

	private BigDecimal expectedSubtotal;

	private WebDriver driver() {
		return DriverManager.getDriver();
	}

	public LoginPage getLoginPage() {
		if (loginPage == null) {
			loginPage = new LoginPage(driver());
		}
		return loginPage;
	}

	public ProductsPage getProductsPage() {
		if (productsPage == null) {
			productsPage = new ProductsPage(driver());
		}
		return productsPage;
	}

	public CartPage getCartPage() {
		if (cartPage == null) {
			cartPage = new CartPage(driver());
		}
		return cartPage;
	}

	public CheckoutPage getCheckoutPage() {
		if (checkoutPage == null) {
			checkoutPage = new CheckoutPage(driver());
		}
		return checkoutPage;
	}
	
	public WebDriver getDriver() {
	    return driver();
	}

	public List<String> getSelectedProducts() {
		return selectedProducts;
	}

	public BigDecimal getExpectedSubtotal() {
		return expectedSubtotal;
	}

	public void setExpectedSubtotal(BigDecimal expectedSubtotal) {
		this.expectedSubtotal = expectedSubtotal;
	}
}
