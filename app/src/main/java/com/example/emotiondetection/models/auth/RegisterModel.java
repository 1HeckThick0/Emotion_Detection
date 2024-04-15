package com.example.emotiondetection.models.auth;

public class RegisterModel {
    private final String user_type, name, contact, email, gender, password;


    public RegisterModel(String userType, String name, String contact, String email, String gender, String password) {
        user_type = userType;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }
}
