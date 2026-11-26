package com.prevaj.evaluation.ui.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class ChromeDriverFactory implements DriverFactory {

	@Override
	public WebDriver createDriver(boolean headless) {

		ChromeOptions options = new ChromeOptions();

		Map<String, Object> preferences = new HashMap<>();
		preferences.put("credentials_enable_service", false);
		preferences.put("profile.password_manager_enabled", false);
		preferences.put("profile.password_manager_leak_detection", false);

		options.setExperimentalOption("prefs", preferences);

		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);

		options.setCapability("goog:loggingPrefs", logs);

		if (headless) {
			options.addArguments("--headless=new");
		}

		return new ChromeDriver(options);
	}
}