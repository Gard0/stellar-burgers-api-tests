package ru.praktikum.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.api.client.UserClient;
import ru.praktikum.api.model.CreateUserRequest;
import ru.praktikum.api.model.LoginRequest;
import ru.praktikum.api.model.UpdateUserRequest;
import ru.praktikum.api.utils.UserGenerator;

public class UserSteps {

    private final UserClient userClient = new UserClient();

    @Step("Сгенерировать уникального пользователя")
    public CreateUserRequest generateRandomUser() {
        return UserGenerator.randomUser();
    }

    @Step("Создать пользователя")
    public Response createUser(CreateUserRequest request) {
        return userClient.createUser(request);
    }

    @Step("Авторизовать пользователя")
    public Response login(CreateUserRequest request) {
        return userClient.login(new LoginRequest(request.getEmail(), request.getPassword()));
    }

    @Step("Авторизовать пользователя с неверным паролем")
    public Response loginWithInvalidPassword(CreateUserRequest request, String invalidPassword) {
        return userClient.login(new LoginRequest(request.getEmail(), invalidPassword));
    }

    @Step("Изменить данные пользователя с авторизацией")
    public Response updateAuthorized(UpdateUserRequest request, String accessToken) {
        return userClient.updateUser(request, accessToken);
    }

    @Step("Изменить данные пользователя без авторизации")
    public Response updateUnauthorized(UpdateUserRequest request) {
        return userClient.updateUserWithoutAuth(request);
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String accessToken) {
        return userClient.deleteUser(accessToken);
    }

    @Step("Удалить пользователя, если токен существует")
    public void deleteUserIfPossible(String accessToken) {
        if (accessToken != null && !accessToken.isBlank()) {
            deleteUser(accessToken);
        }
    }

    @Step("Получить access token из ответа")
    public String extractAccessToken(Response response) {
        return response.then().extract().path("accessToken");
    }
}
