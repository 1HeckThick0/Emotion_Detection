package com.example.emotiondetection.models;

import static com.example.emotiondetection.utils.Constants.ROOT_URL;

import com.google.gson.annotations.SerializedName;

public class PlaylistModel {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("emotion")
    private String emotion;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return ROOT_URL + image;
    }
}
