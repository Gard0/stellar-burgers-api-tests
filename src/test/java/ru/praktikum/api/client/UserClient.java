package ru.praktikum.api.client;

import io.restassured.response.Response;
import ru.praktikum.api.model.CreateUserRequest;
import ru.praktikum.api.model.LoginRequest;
import ru.praktikum.api.model.UpdateUserRequest;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String REGISTER_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String USER_PATH = "/api/auth/user";

    public Response createUser(CreateUserRequest request) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .post(REGISTER_PATH);
    }

    public Response login(LoginRequest request) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .post(LOGIN_PATH);
    }

    public Response updateUser(UpdateUserRequest request, String accessToken) {
        return given()
                .spec(authorizedRequestSpec(accessToken))
                .body(request)
                .when()
                .patch(USER_PATH);
    }

    public Response updateUserWithoutAuth(UpdateUserRequest request) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .patch(USER_PATH);
    }

    public Response deleteUser(String accessToken) {
        return given()
                .spec(authorizedRequestSpec(accessToken))
                .when()
                .delete(USER_PATH);
    }
}
