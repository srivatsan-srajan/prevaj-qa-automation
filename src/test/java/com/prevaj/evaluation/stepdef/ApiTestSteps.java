package com.prevaj.evaluation.stepdef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.prevaj.evaluation.api.modules.UserModule;
import com.prevaj.evaluation.api.payloads.CreateUserRequest;
import com.prevaj.evaluation.api.payloads.LoginPayload;
import com.prevaj.evaluation.api.payloads.UserPayload;
import com.prevaj.evaluation.stepdef.context.ApiScenarioContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApiTestSteps {

	private final ApiScenarioContext context;
	private final UserModule userModule;

	public ApiTestSteps(ApiScenarioContext context) {
		this.context = context;
		this.userModule = new UserModule();
	}

	@Given("I get the default list of users on page {int}")
	public void getDefaultListOfUsers(int page) {
		var userList = userModule.getUsersOnPage(page);

		context.setUserList(userList);
		context.setExpectedTotalUsers(userList.getTotal());
	}

	@When("I get the list of all users within every page")
	public void getAllUsers() {
		context.setUserList(userModule.getAllUsers(context.getUserList()));
	}

	@Then("I should see total users count equals the number of user ids")
	public void verifyTotalUserCount() {

		var userList = context.getUserList();

		assertNotNull(userList);
		assertNotNull(userList.getData());

		assertEquals(context.getExpectedTotalUsers(), userList.getData().size(),
				"Total users count does not match the number of retrieved users");

		Set<Integer> userIds = new HashSet<>();

		for (UserPayload user : userList.getData()) {
			assertTrue(user.getId() > 0, "User ID should be positive");

			assertTrue(userIds.add(user.getId()), "Duplicate user ID found: " + user.getId());
		}
	}

	@When("I make a search for user {int}")
	public void getUserById(int userId) {
		context.setResponse(userModule.getUserById(userId));
	}

	@Then("I should see the following user data")
	public void verifyRetrievedUserData(DataTable dataTable) {

		var response = context.getResponse();

		assertEquals(200, response.statusCode());

		UserPayload actualUser = response.jsonPath().getObject("data", UserPayload.class);

		assertNotNull(actualUser, "User data should be present in response");

		Map<String, String> expected = dataTable.asMaps().get(0);

		assertEquals(expected.get("first_name"), actualUser.getFirstName());

		assertEquals(expected.get("email"), actualUser.getEmail());
	}

	@Then("the response status should be {int}")
	public void verifyResponseStatus(int expectedStatus) {
		assertEquals(expectedStatus, context.getResponse().statusCode(), "Unexpected HTTP response status");
	}

	@When("I create a user with following {word} {word}")
	public void createNewUser(String name, String job) {
		context.setCreatedUser(userModule.createUser(new CreateUserRequest(name, job)));
	}

	@Then("response should contain the following data")
	public void verifyCreatedUser(DataTable dataTable) {

		UserPayload createdUser = context.getCreatedUser();

		assertNotNull(createdUser);

		Map<String, String> expected = dataTable.asMaps().get(0);
		List<String> fields = dataTable.row(0);

		for (String field : fields) {

			switch (field) {
			case "name" -> assertEquals(expected.get(field), createdUser.getName());

			case "job" -> assertEquals(expected.get(field), createdUser.getJob());

			case "id" -> assertTrue(createdUser.getId() > 0, "Created user should have an ID");

			case "createdAt" -> {
				assertNotNull(createdUser.getCreatedAt());
				assertFalse(createdUser.getCreatedAt().isBlank(), "Created timestamp should not be empty");
			}

			default -> throw new IllegalArgumentException("Unsupported response field: " + field);
			}
		}
	}

	@When("I wait for the user list with delay {int} seconds on page {int}")
	public void waitForUserLoad(int delayInSeconds, int page) {

		long startTime = System.currentTimeMillis();

		var delayedResponse = userModule.getUsersWithDelay(delayInSeconds, page);

		context.setResponseTimeMillis(System.currentTimeMillis() - startTime);

		context.setDelayedUsers(delayedResponse.getData());
	}

	@Then("the response should take at least {int} seconds")
	public void verifyResponseTime(int expectedDelaySeconds) {

		assertTrue(context.getResponseTimeMillis() >= expectedDelaySeconds * 1000L,
				"Response took less time than expected");
	}

	@Then("I should see that every user has a unique id")
	public void verifyUniqueIdForUsers() {

		List<UserPayload> delayedUsers = context.getDelayedUsers();

		assertNotNull(delayedUsers);
		assertFalse(delayedUsers.isEmpty());

		Set<Integer> userIds = new HashSet<>();

		for (UserPayload user : delayedUsers) {
			assertTrue(userIds.add(user.getId()), "Duplicate user ID found: " + user.getId());
		}
	}

	@Given("I login with the following data")
	public void loginWithData(DataTable dataTable) {

		Map<String, String> credentials = dataTable.asMaps().get(0);

		LoginPayload payload = new LoginPayload(credentials.get("Email"), credentials.get("Password"));

		context.setResponse(userModule.login(payload));
	}

	@Then("I should see the following response error")
	public void verifyLoginError(DataTable dataTable) {

		String expectedError = dataTable.asMaps().get(0).get("error");

		assertEquals(expectedError, context.getResponse().jsonPath().getString("error"));
	}
}
