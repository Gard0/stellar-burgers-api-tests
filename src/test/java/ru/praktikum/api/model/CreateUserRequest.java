package ru.praktikum.api.model;

public class CreateUserRequest {

    private final String email;
    private final String password;
    private final String name;

    public CreateUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
