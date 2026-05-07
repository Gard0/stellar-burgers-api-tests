package ru.praktikum.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.api.model.CreateUserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseApiTest {

    @Test
    @DisplayName("Логинимся существующим пользователем")
    @Description("Ожидаем авторизацию существующего пользователя")
    public void shouldLoginExistingUser() {
        CreateUserRequest user = registerRandomUser();

        Response response = userSteps.login(user);
        accessToken = userSteps.extractAccessToken(response);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Логинимся с неверными учетными данными")
    @Description("Ожидаем ошибку при неверном пароле")
    public void shouldNotLoginWithInvalidCredentials() {
        CreateUserRequest user = registerRandomUser();

        Response response = userSteps.loginWithInvalidPassword(user, "WrongPassword123");

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
