package ru.praktikum.api.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientClient extends BaseClient {

    private static final String INGREDIENTS_PATH = "/api/ingredients";

    public Response getIngredients() {
        return given()
                .spec(requestSpec())
                .when()
                .get(INGREDIENTS_PATH);
    }
}
