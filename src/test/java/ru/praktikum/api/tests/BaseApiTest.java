package ru.praktikum.api.tests;

import org.junit.After;
import ru.praktikum.api.model.CreateUserRequest;
import ru.praktikum.api.steps.OrderSteps;
import ru.praktikum.api.steps.UserSteps;

public abstract class BaseApiTest {

    protected final UserSteps userSteps = new UserSteps();
    protected final OrderSteps orderSteps = new OrderSteps();
    protected String accessToken;

    @After
    public void tearDown() {
        userSteps.deleteUserIfPossible(accessToken);
    }

    protected CreateUserRequest registerRandomUser() {
        CreateUserRequest user = userSteps.generateRandomUser();
        accessToken = userSteps.extractAccessToken(userSteps.createUser(user));
        return user;
    }
}
