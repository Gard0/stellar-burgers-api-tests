package ru.praktikum.api.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseApiTest {

    @Test
    public void shouldCreateOrderWithAuthorization() {
        registerRandomUser();
        List<String> ingredients = orderSteps.getValidIngredientIds();

        Response response = orderSteps.createAuthorizedOrder(ingredients, accessToken);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .body("order.ingredients.size()", greaterThan(0));
    }

    @Test
    public void shouldCreateOrderWithoutAuthorization() {
        List<String> ingredients = orderSteps.getValidIngredientIds();

        Response response = orderSteps.createUnauthorizedOrder(ingredients);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    public void shouldNotCreateOrderWithoutIngredients() {
        Response response = orderSteps.createOrderWithoutIngredients();

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void shouldNotCreateOrderWithInvalidIngredientHash() {
        Response response = orderSteps.createOrderWithInvalidIngredientHash();

        response.then()
                .statusCode(500)
                .body(containsString("Internal Server Error"));
    }
}
