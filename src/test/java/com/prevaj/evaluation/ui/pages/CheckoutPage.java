package com.prevaj.evaluation.ui.pages;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

	private final By firstName = By.id("first-name");
	private final By lastName = By.id("last-name");
	private final By postalCode = By.id("postal-code");
	private final By continueButton = By.id("continue");
	private final By itemPrices = By.className("inventory_item_price");
	private final By itemSubtotal = By.cssSelector("[data-test='subtotal-label']");
	private final By tax = By.cssSelector("[data-test='tax-label']");
	private final By total = By.cssSelector("[data-test='total-label']");
	private final By checkoutError = By.cssSelector("[data-test='error']");

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	public void enterFirstName(String first) {
		type(firstName, first);
	}

	public void enterLastName(String last) {
		type(lastName, last);
	}

	public void enterPostalCode(String zip) {
		type(postalCode, zip);
	}

	public void continueToOverview() {
		click(continueButton);
		wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
	}

	public void submitInformation() {
		click(continueButton);
	}

	public String getCheckoutError() {
		return text(checkoutError);
	}

	public boolean isCheckoutInformationPageDisplayed() {
		return driver.getCurrentUrl().contains("checkout-step-one.html");
	}

	public List<BigDecimal> getItemPrices() {
		visible(itemPrices);
		return driver.findElements(itemPrices).stream().map(element -> amount(element.getText())).toList();
	}

	public BigDecimal getItemSubtotal() {
		return amount(text(itemSubtotal));
	}

	public BigDecimal getTax() {
		return amount(text(tax));
	}

	public BigDecimal getTotal() {
		return amount(text(total));
	}

	private BigDecimal amount(String text) {
		return new BigDecimal(text.replaceAll("[^0-9.]", ""));
	}
}
