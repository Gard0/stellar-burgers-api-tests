package ru.praktikum.api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.api.config.ApiConfig;

public abstract class BaseClient {

    private final RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(ApiConfig.BASE_URL)
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    protected RequestSpecification requestSpec() {
        return baseSpec;
    }

    protected RequestSpecification authorizedRequestSpec(String accessToken) {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseSpec)
                .addHeader("Authorization", accessToken)
                .build();
    }
}
