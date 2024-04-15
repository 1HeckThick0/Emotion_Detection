package com.example.emotiondetection.models;

import com.google.gson.annotations.SerializedName;

public class BotResponse {

    @SerializedName("recipient_id")
    private String recipient_id;
    @SerializedName("text")
    private String text;

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
