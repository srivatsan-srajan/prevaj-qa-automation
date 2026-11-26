package com.prevaj.evaluation.ui.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

	private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

	private DriverManager() {
	}

	public static void startDriver(String browser, boolean headless) {

		if (DRIVER.get() != null) {
			throw new IllegalStateException("WebDriver is already initialized for this thread");
		}

		DriverFactory factory = DriverFactoryRegistry.getFactory(browser);

		DRIVER.set(factory.createDriver(headless));
	}

	public static WebDriver getDriver() {

		WebDriver driver = DRIVER.get();

		if (driver == null) {
			throw new IllegalStateException("WebDriver has not been initialized for this thread");
		}

		return driver;
	}

	public static boolean hasDriver() {
		return DRIVER.get() != null;
	}

	public static void quitDriver() {

		WebDriver driver = DRIVER.get();

		try {
			if (driver != null) {
				driver.quit();
			}
		} finally {
			DRIVER.remove();
		}
	}
}