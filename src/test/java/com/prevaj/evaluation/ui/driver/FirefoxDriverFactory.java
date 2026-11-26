package com.prevaj.evaluation.ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverFactory implements DriverFactory {

	@Override
	public WebDriver createDriver(boolean headless) {

		FirefoxOptions options = new FirefoxOptions();

		if (headless) {
			options.addArguments("-headless");
		}

		return new FirefoxDriver(options);
	}
}