package com.prevaj.evaluation.ui.pages;

import org.openqa.selenium.By;

public final class ProductLocators {

	private ProductLocators() {
	}

	public static By addToCartButton(String productName) {
		return By.xpath(productItemPath(productName) + "//button");
	}

	public static By cartItem(String productName) {
		return By.xpath(productItemPath(productName));
	}

	public static By removeButton(String productName) {
		return By.xpath(productItemPath(productName) + "//button");
	}

	public static By itemQuantity(String productName) {
		return By.xpath(productItemPath(productName) + "//div[@data-test='item-quantity']");
	}

	private static String productItemPath(String productName) {
		return "//div[@data-test='inventory-item'][.//div" + "[@data-test='inventory-item-name' and normalize-space()="
				+ xpathLiteral(productName) + "]]";
	}

	private static String xpathLiteral(String value) {
		if (value.contains("'")) {
			return "\"" + value + "\"";
		}
		return "'" + value + "'";
	}
}