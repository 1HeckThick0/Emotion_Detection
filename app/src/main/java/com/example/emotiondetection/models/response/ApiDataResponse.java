package com.example.emotiondetection.models.response;

import androidx.annotation.Nullable;

public class ApiDataResponse<T> {
    private boolean status;
    private String message;
    @Nullable
    private T data;

    public ApiDataResponse(boolean status, String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}

// Specialized version for cases where there is no data
