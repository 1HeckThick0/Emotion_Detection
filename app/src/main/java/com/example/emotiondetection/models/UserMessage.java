package com.example.emotiondetection.models;

public class UserMessage {
    final private String sender,message;

    public UserMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
