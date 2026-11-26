package com.prevaj.evaluation.api.clients;

import com.prevaj.evaluation.api.auth.ApiKeyAuthentication;
import com.prevaj.evaluation.api.payloads.CreateUserRequest;
import com.prevaj.evaluation.api.payloads.LoginPayload;
import com.prevaj.evaluation.config.FrameworkConfig;

import io.restassured.response.Response;

public class ReqResApiClient extends BaseApiClient {

	public ReqResApiClient() {
	    super(
	        FrameworkConfig.get("reqres.base.url"),
	        new ApiKeyAuthentication("x-api-key", FrameworkConfig.getApiKey())
	    );
	}

	private static final String USERS_ENDPOINT = "/api/users";
	private static final String LOGIN_ENDPOINT = "/api/login";

	public Response getUsersByPage(int page) {
		return request().queryParam("page", page).get(USERS_ENDPOINT);
	}

	public Response getUserById(int userId) {
		return request().pathParam("id", userId).get(USERS_ENDPOINT + "/{id}");
	}

	public Response createUser(CreateUserRequest user) {
		return request().body(user).post(USERS_ENDPOINT);
	}

	public Response login(LoginPayload login) {
		return request().body(login).post(LOGIN_ENDPOINT);
	}

	public Response getUsersWithDelay(int delayInSeconds, int page) {
		return request().queryParam("delay", delayInSeconds).queryParam("page", page).get(USERS_ENDPOINT);
	}
}
