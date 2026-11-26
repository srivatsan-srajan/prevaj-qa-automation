package com.prevaj.evaluation.ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverFactory implements DriverFactory {

	@Override
	public WebDriver createDriver(boolean headless) {

		EdgeOptions options = new EdgeOptions();

		if (headless) {
			options.addArguments("--headless=new");
		}

		return new EdgeDriver(options);
	}
}