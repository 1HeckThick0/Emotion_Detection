package com.example.emotiondetection.models;

import com.google.gson.annotations.SerializedName;

public class UserSession {
    @SerializedName("id")
    private String id;

    @SerializedName("user_type")
    private String userType;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
