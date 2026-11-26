package com.prevaj.evaluation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

	private final By shoppingCart = By.className("shopping_cart_link");
	private final By inventoryContainer = By.cssSelector("[data-test='inventory-container']");

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public void addProductToCart(String productName) {
		click(ProductLocators.addToCartButton(productName));
	}

	public void openShoppingCart() {
		click(shoppingCart);
	}

	public boolean isDisplayed() {
		return visible(inventoryContainer).isDisplayed();
	}
}
