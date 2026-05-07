package ru.praktikum.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class GetUserOrdersTest extends BaseApiTest {

    @Test
    @DisplayName("Получаем заказы пользователя")
    @Description("Ожидаем получение списка заказов пользователя")
    public void shouldGetOrdersOfAuthorizedUser() {
        registerRandomUser();
        List<String> ingredients = orderSteps.getValidIngredientIds();
        orderSteps.createAuthorizedOrder(ingredients, accessToken);

        Response response = orderSteps.getAuthorizedUserOrders(accessToken);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Получаем заказы без авторизации")
    @Description("Ожидаем ошибку авторизации")
    public void shouldNotGetOrdersOfUnauthorizedUser() {
        Response response = orderSteps.getUnauthorizedUserOrders();

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
