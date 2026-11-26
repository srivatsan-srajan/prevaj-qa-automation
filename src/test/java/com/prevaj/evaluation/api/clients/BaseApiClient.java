package com.prevaj.evaluation.api.clients;

import static io.restassured.RestAssured.given;

import com.prevaj.evaluation.api.auth.AuthenticationStrategy;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {

	private final RequestSpecification requestSpecification;

	protected BaseApiClient(String baseUrl, AuthenticationStrategy authentication) {

		if (baseUrl == null || baseUrl.isBlank()) {
			throw new IllegalArgumentException("Base URL must not be blank");
		}

		if (authentication == null) {
			throw new IllegalArgumentException("Authentication strategy cannot be null");
		}

		RequestSpecBuilder builder = new RequestSpecBuilder().setBaseUri(baseUrl).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON);

		authentication.apply(builder);

		requestSpecification = builder.build();
	}

	protected RequestSpecification request() {
		return given().spec(requestSpecification);
	}
}
