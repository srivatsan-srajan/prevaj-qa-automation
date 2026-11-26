package com.prevaj.evaluation.ui.driver;

import org.openqa.selenium.WebDriver;

public interface DriverFactory {

	WebDriver createDriver(boolean headless);
}