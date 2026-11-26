package com.prevaj.evaluation.stepdef.ui;

import com.prevaj.evaluation.stepdef.context.UIScenarioContext;

import io.cucumber.java.en.When;

public class LoginSteps {

    private final UIScenarioContext context;

    public LoginSteps(UIScenarioContext context) {
        this.context = context;
    }

    @When("I login with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        context.getLoginPage().performLogin(username, password);
    }
}