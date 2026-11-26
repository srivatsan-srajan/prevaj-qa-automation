package com.prevaj.evaluation.api.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.prevaj.evaluation.api.clients.ReqResApiClient;
import com.prevaj.evaluation.api.payloads.CreateUserRequest;
import com.prevaj.evaluation.api.payloads.LoginPayload;
import com.prevaj.evaluation.api.payloads.UserListResponse;
import com.prevaj.evaluation.api.payloads.UserPayload;

import io.restassured.response.Response;

public class UserModule {

	private final ReqResApiClient apiClient;

	public UserModule() {
		this(new ReqResApiClient());
	}

	public UserModule(ReqResApiClient apiClient) {
		this.apiClient = Objects.requireNonNull(apiClient);
	}

	public UserListResponse getUsersOnPage(int page) {
		Response response = apiClient.getUsersByPage(page);
		response.then().statusCode(200);
		return response.as(UserListResponse.class);
	}

	public UserListResponse getAllUsers(UserListResponse firstPage) {

		if (firstPage == null || firstPage.getData() == null) {
			throw new IllegalArgumentException("The first page response is required");
		}

		List<UserPayload> allUsers = new ArrayList<>(firstPage.getData());

		int totalPages = firstPage.getTotalPages();

		for (int page = firstPage.getPage() + 1; page <= totalPages; page++) {

			UserListResponse pageResult = getUsersOnPage(page);

			if (pageResult.getData() == null) {
				throw new IllegalStateException("No user data returned for page: " + page);
			}

			allUsers.addAll(pageResult.getData());
		}

		firstPage.setData(allUsers);

		return firstPage;
	}

	public Response getUserById(int userId) {
		return apiClient.getUserById(userId);
	}

	public UserPayload createUser(CreateUserRequest user) {
	    Response response = apiClient.createUser(user);
	    response.then().statusCode(201);
	    return response.as(UserPayload.class);
	}

	public Response login(LoginPayload login) {
		return apiClient.login(login);
	}

	public UserListResponse getUsersWithDelay(int delayInSeconds, int page) {
		Response response = apiClient.getUsersWithDelay(delayInSeconds, page);
		response.then().statusCode(200);
		return response.as(UserListResponse.class);
	}
}
