package com.prevaj.evaluation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FrameworkConfig {

	private static final Properties PROPERTIES = new Properties();

	static {
		try (InputStream input = FrameworkConfig.class.getClassLoader()
				.getResourceAsStream("config/framework.properties")) {

			if (input == null) {
				throw new IllegalStateException("Unable to find config/framework.properties");
			}

			PROPERTIES.load(input);

		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private FrameworkConfig() {
	}

	public static String get(String key) {
		String value = System.getProperty(key);

		if (value == null || value.isBlank()) {
			value = PROPERTIES.getProperty(key);
		}

		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Missing or empty configuration property: " + key);
		}

		return value.trim();
	}

	public static String getApiKey() {
		String apiKey = System.getenv("REQRES_API_KEY");

		if (apiKey == null || apiKey.isBlank()) {
			apiKey = System.getProperty("api.key");
		}

		if (apiKey == null || apiKey.isBlank()) {
			throw new IllegalStateException("Missing API key. Set REQRES_API_KEY or supply -Dapi.key");
		}

		return apiKey.trim();
	}

	public static int getTimeout(String key) {
		try {
			return Integer.parseInt(get(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Configuration value must be an integer: " + key, e);
		}
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = System.getProperty(key);

		if (value == null || value.isBlank()) {
			value = PROPERTIES.getProperty(key);
		}

		if (value == null || value.isBlank()) {
			return defaultValue;
		}

		if (!value.trim().equalsIgnoreCase("true") && !value.trim().equalsIgnoreCase("false")) {
			throw new IllegalArgumentException("Configuration value must be true or false: " + key);
		}

		return Boolean.parseBoolean(value.trim());
	}
}
