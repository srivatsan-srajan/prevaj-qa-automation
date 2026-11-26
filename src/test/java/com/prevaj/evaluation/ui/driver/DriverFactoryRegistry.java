package com.prevaj.evaluation.ui.driver;

import java.util.Map;

public final class DriverFactoryRegistry {

	private static final Map<String, DriverFactory> FACTORIES = Map.of("chrome", new ChromeDriverFactory(), "firefox",
			new FirefoxDriverFactory(), "edge", new EdgeDriverFactory());

	private DriverFactoryRegistry() {
	}

	public static DriverFactory getFactory(String browser) {

		if (browser == null || browser.isBlank()) {
			throw new IllegalArgumentException("Browser must be specified");
		}

		DriverFactory factory = FACTORIES.get(browser.trim().toLowerCase());

		if (factory == null) {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		return factory;
	}
}