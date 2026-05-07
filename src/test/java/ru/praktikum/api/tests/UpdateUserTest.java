package ru.praktikum.api.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.api.model.CreateUserRequest;
import ru.praktikum.api.model.UpdateUserRequest;
import ru.praktikum.api.utils.UserGenerator;

import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseApiTest {

    @Test
    @DisplayName("Обновляем Имя пользователя")
    @Description("Ожидаем обновленное имя 'Updated Mikhail'")
    public void shouldUpdateUserNameWithAuthorization() {
        CreateUserRequest user = registerRandomUser();
        String updatedName = "Updated Mikhail";

        Response response = userSteps.updateAuthorized(
                new UpdateUserRequest(null, null, updatedName),
                accessToken
        );

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()))
                .body("user.name", equalTo(updatedName));
    }

    @Test
    @DisplayName("Обновляем email пользователя")
    @Description("Ожидаем обновленный email пользователя")
    public void shouldUpdateUserEmailWithAuthorization() {
        registerRandomUser();
        String updatedEmail = UserGenerator.randomEmail();

        Response response = userSteps.updateAuthorized(
                new UpdateUserRequest(updatedEmail, null, null),
                accessToken
        );

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(updatedEmail.toLowerCase()));
    }

    @Test
    @DisplayName("Обновляем пароль пользователя")
    @Description("Ожидаем успешное обновление пароля")
    public void shouldUpdateUserPasswordWithAuthorization() {
        CreateUserRequest user = registerRandomUser();
        String updatedPassword = "NewPass123!";

        Response response = userSteps.updateAuthorized(
                new UpdateUserRequest(null, updatedPassword, null),
                accessToken
        );

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail().toLowerCase()))
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Пытаемся обновить имя без авторизации")
    @Description("Ожидаем ошибку авторизации при обновлении имени без токена")
    public void shouldNotUpdateUserNameWithoutAuthorization() {
        Response response = userSteps.updateUnauthorized(new UpdateUserRequest(null, null, "Updated Mikhail"));

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Пытаемся обновить email без авторизации")
    @Description("Ожидаем ошибку авторизации при обновлении email без токена")
    public void shouldNotUpdateUserEmailWithoutAuthorization() {
        Response response = userSteps.updateUnauthorized(new UpdateUserRequest(UserGenerator.randomEmail(), null, null));

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Пытаемся обновить пароль без авторизации")
    @Description("Ожидаем ошибку авторизации при обновлении пароля без токена")
    public void shouldNotUpdateUserPasswordWithoutAuthorization() {
        Response response = userSteps.updateUnauthorized(new UpdateUserRequest(null, "NewPass123!", null));

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
