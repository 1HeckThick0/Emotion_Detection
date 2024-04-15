package com.example.emotiondetection.models;

import static com.example.emotiondetection.utils.Constants.ROOT_URL;

import com.google.gson.annotations.SerializedName;

public class ChatModel {
    private String message, logo, name, playlistName, playlistId;
    private boolean showClick = false;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogo() {
        return ROOT_URL + logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public boolean isShowClick() {
        return showClick;
    }

    public void setShowClick(boolean showClick) {
        this.showClick = showClick;
    }
}
