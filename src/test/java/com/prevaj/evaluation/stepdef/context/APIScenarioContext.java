
package com.prevaj.evaluation.stepdef.context;

import java.util.List;

import com.prevaj.evaluation.api.payloads.UserListResponse;
import com.prevaj.evaluation.api.payloads.UserPayload;

import io.restassured.response.Response;

public class APIScenarioContext {

	private UserListResponse userList;
	private int expectedTotalUsers;
	private UserPayload createdUser;
	private Response response;
	private List<UserPayload> delayedUsers;
	private long responseTimeMillis;

	public UserListResponse getUserList() {
		return userList;
	}

	public void setUserList(UserListResponse userList) {
		this.userList = userList;
	}

	public int getExpectedTotalUsers() {
		return expectedTotalUsers;
	}

	public void setExpectedTotalUsers(int expectedTotalUsers) {
		this.expectedTotalUsers = expectedTotalUsers;
	}

	public UserPayload getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(UserPayload createdUser) {
		this.createdUser = createdUser;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List<UserPayload> getDelayedUsers() {
		return delayedUsers;
	}

	public void setDelayedUsers(List<UserPayload> delayedUsers) {
		this.delayedUsers = delayedUsers;
	}

	public long getResponseTimeMillis() {
		return responseTimeMillis;
	}

	public void setResponseTimeMillis(long responseTimeMillis) {
		this.responseTimeMillis = responseTimeMillis;
	}
}
