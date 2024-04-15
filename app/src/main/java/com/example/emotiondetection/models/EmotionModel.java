package com.example.emotiondetection.models;

import com.google.gson.annotations.SerializedName;

public class EmotionModel {

    @SerializedName("emotion")
    private String emotion;

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
