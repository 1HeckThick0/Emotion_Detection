package com.example.emotiondetection.models.response;

public class ApiEmptyDataResponse extends ApiDataResponse<Void> {
    public ApiEmptyDataResponse(boolean status, String message) {
        super(status, message, null);
    }
}
