package com.example.emotiondetection.models;

import static com.example.emotiondetection.utils.Constants.ROOT_URL;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SongsModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("artist")
    private String artist;
    @SerializedName("image")
    private String image;
    @SerializedName("song_file")
    private String song_file;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getImage() {
        return ROOT_URL + image;
    }

    public String getSong_file() {
        return ROOT_URL + song_file;
    }
}
