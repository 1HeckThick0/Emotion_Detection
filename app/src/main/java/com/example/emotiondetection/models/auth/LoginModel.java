package com.example.emotiondetection.models.auth;

public class LoginModel {
    private final String email, password, user_type;

    public LoginModel(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.user_type = userType;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_type() {
        return user_type;
    }
}
