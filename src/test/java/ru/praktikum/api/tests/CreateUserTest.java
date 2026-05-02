package ru.praktikum.api.tests;

import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.api.model.CreateUserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends BaseApiTest {

    @Test
    public void shouldCreateUniqueUser() {
        CreateUserRequest user = userSteps.generateRandomUser();

        Response response = userSteps.createUser(user);
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
    public void shouldNotCreateExistingUser() {
        CreateUserRequest user = registerRandomUser();

        Response response = userSteps.createUser(user);

        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void shouldNotCreateUserWithoutRequiredField() {
        CreateUserRequest userWithoutName = new CreateUserRequest(
                ru.praktikum.api.utils.UserGenerator.randomEmail(),
                "Test123!_",
                null
        );

        Response response = userSteps.createUser(userWithoutName);

        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
