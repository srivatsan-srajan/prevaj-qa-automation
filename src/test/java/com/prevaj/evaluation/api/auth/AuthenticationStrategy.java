package com.prevaj.evaluation.api.auth;

import io.restassured.builder.RequestSpecBuilder;

public interface AuthenticationStrategy {

    void apply(RequestSpecBuilder builder);
}