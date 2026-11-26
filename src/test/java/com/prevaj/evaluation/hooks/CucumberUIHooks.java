
package com.prevaj.evaluation.hooks;

import org.openqa.selenium.WebDriver;

import com.prevaj.evaluation.config.FrameworkConfig;
import com.prevaj.evaluation.diagnostics.UIDiagnostics;
import com.prevaj.evaluation.ui.driver.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberUIHooks {

	private final UIDiagnostics diagnostics = new UIDiagnostics();

	@Before(value = "@ui", order = 0)
	public void setUp() {

		String browser = FrameworkConfig.get("browser");
		boolean headless = FrameworkConfig.getBoolean("headless", false);

		DriverManager.startDriver(browser, headless);
	}

	@After(value = "@ui", order = 0)
	public void tearDown(Scenario scenario) {

		try {
			if (scenario.isFailed() && DriverManager.hasDriver()) {
				WebDriver driver = DriverManager.getDriver();
				diagnostics.capture(driver, scenario);
			}
		} finally {
			DriverManager.quitDriver();
		}
	}
}
