package com.prevaj.evaluation.stepdef.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.prevaj.evaluation.stepdef.context.ApiScenarioContext;

import io.cucumber.java.en.Then;

public class UserSteps {

    private final ApiScenarioContext context;

    public UserSteps(ApiScenarioContext context) {
        this.context = context;
    }

    @Then("I receive error code {int} in response")
    public void verifyErrorResponse(int expectedStatus) {
        assertEquals(expectedStatus, context.getResponse().statusCode());
    }
}
