package com.prevaj.evaluation.ui.pages;

import org.openqa.selenium.By;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

	private final By checkoutButton = By.id("checkout");
	private final By cartBadge = By.className("shopping_cart_badge");

	public CartPage(WebDriver driver) {
		super(driver);
	}

	public int getProductQuantity(String productName) {
		return Integer.parseInt(visible(ProductLocators.itemQuantity(productName)).getText());
	}

	public void removeProduct(String productName) {
		click(ProductLocators.removeButton(productName));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ProductLocators.cartItem(productName)));
	}

	public int getCartItemCount() {
		List<WebElement> badges = driver.findElements(cartBadge);
		return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
	}

	public int waitForCartItemCount(int expectedCount) {
		wait.until(d -> getCartItemCount() == expectedCount);
		return getCartItemCount();
	}

	public void checkout() {
		click(checkoutButton);
	}

}
