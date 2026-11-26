package com.prevaj.evaluation.diagnostics;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import io.cucumber.java.Scenario;

public class UiDiagnostics {

	public void capture(WebDriver driver, Scenario scenario) {

		captureScreenshot(driver, scenario);
		captureCurrentUrl(driver, scenario);
		captureBrowserConsole(driver, scenario);
	}

	private void captureScreenshot(WebDriver driver, Scenario scenario) {
		try {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			scenario.attach(screenshot, "image/png", "Failure Screenshot");

		} catch (Exception e) {
			System.err.println("Unable to capture failure screenshot: " + e.getMessage());
		}
	}

	private void captureCurrentUrl(WebDriver driver, Scenario scenario) {
		try {
			scenario.attach(driver.getCurrentUrl(), "text/plain", "Current URL");

		} catch (Exception e) {
			System.err.println("Unable to capture current URL: " + e.getMessage());
		}
	}

	private void captureBrowserConsole(WebDriver driver, Scenario scenario) {
		try {
			LogEntries logs = driver.manage().logs().get(LogType.BROWSER);

			StringBuilder consoleOutput = new StringBuilder();

			for (LogEntry entry : logs) {
				consoleOutput.append(entry.getLevel()).append(" | ").append(entry.getMessage())
						.append(System.lineSeparator());
			}

			if (!consoleOutput.isEmpty()) {
				scenario.attach(consoleOutput.toString(), "text/plain", "Browser Console Logs");
			}

		} catch (Exception e) {
			System.err.println("Unable to capture browser console logs: " + e.getMessage());
		}
	}
}
