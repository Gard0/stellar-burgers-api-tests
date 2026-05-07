package ru.praktikum.api.model;

public class UpdateUserRequest {

    private final String email;
    private final String password;
    private final String name;

    public UpdateUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
