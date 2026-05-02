package ru.praktikum.api.utils;

import ru.praktikum.api.model.CreateUserRequest;

import java.util.UUID;

public final class UserGenerator {

    private static final String PASSWORD = "Test123!_";
    private static final String NAME = "Mikhail123";

    private UserGenerator() {
    }

    public static CreateUserRequest randomUser() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return new CreateUserRequest(
                "mikhail.api." + suffix + "@mail.test",
                PASSWORD,
                NAME
        );
    }

    public static String randomEmail() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return "mikhail.api." + suffix + "@mail.test";
    }
}
