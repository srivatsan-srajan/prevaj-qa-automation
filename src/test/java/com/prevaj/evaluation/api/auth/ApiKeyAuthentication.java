package com.prevaj.evaluation.api.auth;

import io.restassured.builder.RequestSpecBuilder;

public class ApiKeyAuthentication implements AuthenticationStrategy {

	private final String headerName;
	private final String apiKey;

	public ApiKeyAuthentication(String headerName, String apiKey) {
		this.headerName = headerName;
		this.apiKey = apiKey;
	}

	@Override
	public void apply(RequestSpecBuilder builder) {
		builder.addHeader(headerName, apiKey);
	}
}