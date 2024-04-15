package com.example.emotiondetection.api;

public interface ServerResponseListener<T> {
    void onSuccess(T response);
    void onFailure(String errorMessage);
}
