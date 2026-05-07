package ru.praktikum.api.client;

import io.restassured.response.Response;
import ru.praktikum.api.model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    private static final String ORDERS_PATH = "/api/orders";

    public Response createOrder(OrderRequest request) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .post(ORDERS_PATH);
    }

    public Response createOrder(OrderRequest request, String accessToken) {
        return given()
                .spec(authorizedRequestSpec(accessToken))
                .body(request)
                .when()
                .post(ORDERS_PATH);
    }

    public Response getUserOrders(String accessToken) {
        return given()
                .spec(authorizedRequestSpec(accessToken))
                .when()
                .get(ORDERS_PATH);
    }

    public Response getUserOrdersWithoutAuth() {
        return given()
                .spec(requestSpec())
                .when()
                .get(ORDERS_PATH);
    }
}
