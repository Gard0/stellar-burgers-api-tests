package ru.praktikum.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.api.client.IngredientClient;
import ru.praktikum.api.client.OrderClient;
import ru.praktikum.api.model.OrderRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSteps {

    private static final String INVALID_INGREDIENT_HASH = "badhash";

    private final IngredientClient ingredientClient = new IngredientClient();
    private final OrderClient orderClient = new OrderClient();

    @Step("Получить список ингредиентов")
    public Response getIngredients() {
        return ingredientClient.getIngredients();
    }

    @Step("Получить валидные id ингредиентов для заказа")
    public List<String> getValidIngredientIds() {
        Response response = getIngredients();
        return response.then()
                .extract()
                .jsonPath()
                .getList("data._id")
                .stream()
                .map(String::valueOf)
                .limit(2)
                .collect(Collectors.toList());
    }

    @Step("Создать заказ с авторизацией")
    public Response createAuthorizedOrder(List<String> ingredients, String accessToken) {
        return orderClient.createOrder(new OrderRequest(ingredients), accessToken);
    }

    @Step("Создать заказ без авторизации")
    public Response createUnauthorizedOrder(List<String> ingredients) {
        return orderClient.createOrder(new OrderRequest(ingredients));
    }

    @Step("Создать заказ без ингредиентов")
    public Response createOrderWithoutIngredients() {
        return orderClient.createOrder(new OrderRequest(Collections.emptyList()));
    }

    @Step("Создать заказ с неверным хешем ингредиентов")
    public Response createOrderWithInvalidIngredientHash() {
        return orderClient.createOrder(new OrderRequest(Collections.singletonList(INVALID_INGREDIENT_HASH)));
    }

    @Step("Получить заказы пользователя с авторизацией")
    public Response getAuthorizedUserOrders(String accessToken) {
        return orderClient.getUserOrders(accessToken);
    }

    @Step("Получить заказы пользователя без авторизации")
    public Response getUnauthorizedUserOrders() {
        return orderClient.getUserOrdersWithoutAuth();
    }
}
