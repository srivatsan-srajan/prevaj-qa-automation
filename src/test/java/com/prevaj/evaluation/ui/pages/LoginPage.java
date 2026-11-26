package com.prevaj.evaluation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	private final By username = By.id("user-name");
	private final By password = By.id("password");
	private final By loginButton = By.id("login-button");
	private final By loginError = By.cssSelector("[data-test='error']");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void performLogin(String userName, String passwordValue) {
		visible(username).clear();
		type(username, userName);

		visible(password).clear();
		type(password, passwordValue);

		click(loginButton);
	}

	public String getLoginError() {
		return text(loginError);
	}

	public boolean isLoginPageDisplayed() {
		return visible(loginButton).isDisplayed();
	}
}
