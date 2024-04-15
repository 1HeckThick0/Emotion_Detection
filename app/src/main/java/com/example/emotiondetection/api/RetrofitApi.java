package com.example.emotiondetection.api;


import com.example.emotiondetection.models.BotResponse;
import com.example.emotiondetection.models.EmotionModel;
import com.example.emotiondetection.models.PlaylistModel;
import com.example.emotiondetection.models.SongsModel;
import com.example.emotiondetection.models.UserMessage;
import com.example.emotiondetection.models.UserSession;
import com.example.emotiondetection.models.auth.LoginModel;
import com.example.emotiondetection.models.auth.RegisterModel;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.models.response.ApiEmptyDataResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitApi {
    @POST("create_user/")
    Call<ApiEmptyDataResponse> registerUser(@Body RegisterModel registerModel);

    @POST("login_user/")
    Call<ApiDataResponse<UserSession>> loginUser(@Body LoginModel loginModel);

    @Multipart
    @POST("upload_image/")
    Call<ApiDataResponse<EmotionModel>> uploadImage(
            @Part MultipartBody.Part image_file
    );


    @GET("get_playlist/")
    Call<ApiDataResponse<List<PlaylistModel>>> get_playlist(
            @Query("emotion") String emotion
    );

    @GET("get_songs/")
    Call<ApiDataResponse<List<SongsModel>>> get_songsList(
            @Query("playlist") String emotion
    );

    @FormUrlEncoded
    @POST("upload_input/")
    Call<ApiDataResponse<EmotionModel>> uploadInput(
            @Field("input") String input
    );

    @POST("webhook")
    Call<List<BotResponse>> sendMessage(@Body UserMessage userMessage);
}
