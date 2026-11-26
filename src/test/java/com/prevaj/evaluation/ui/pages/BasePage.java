package com.prevaj.evaluation.ui.pages;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.prevaj.evaluation.config.FrameworkConfig;

public abstract class BasePage {

	protected final WebDriver driver;
	protected final WebDriverWait wait;

	protected BasePage(WebDriver driver) {
		this.driver = Objects.requireNonNull(driver, "WebDriver cannot be null");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConfig.getTimeout("timeout.seconds")));
	}

	protected WebElement visible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected void click(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	protected void type(By locator, String value) {
		visible(locator).sendKeys(value);
	}

	protected String text(By locator) {
		return visible(locator).getText();
	}
}
