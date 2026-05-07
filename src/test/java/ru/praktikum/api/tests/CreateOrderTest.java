package ru.praktikum.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseApiTest {

    @Test
    @DisplayName("Создаем заказ с авторизацией")
    @Description("Ожидаем успешное создание заказа авторизованным пользователем")
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
    @DisplayName("Создаем заказ без авторизации")
    @Description("Ожидаем успешное создание заказа")
    public void shouldCreateOrderWithoutAuthorization() {
        List<String> ingredients = orderSteps.getValidIngredientIds();

        Response response = orderSteps.createUnauthorizedOrder(ingredients);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создаем заказ без ингредиентов")
    @Description("Ожидаем ошибку при создании заказа")
    public void shouldNotCreateOrderWithoutIngredients() {
        Response response = orderSteps.createOrderWithoutIngredients();

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создаем заказ с неверным хешем ингредиентов")
    @Description("Ожидаем ошибку сервера Internal Server Error")
    public void shouldNotCreateOrderWithInvalidIngredientHash() {
        Response response = orderSteps.createOrderWithInvalidIngredientHash();

        response.then()
                .statusCode(500)
                .body(containsString("Internal Server Error"));
    }
}
