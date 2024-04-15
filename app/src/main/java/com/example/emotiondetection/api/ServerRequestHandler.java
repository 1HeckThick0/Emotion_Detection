package com.example.emotiondetection.api;

import static com.example.emotiondetection.utils.Constants.IP;

import android.content.Context;

import androidx.annotation.NonNull;


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
import com.example.emotiondetection.utils.UiHandlers;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerRequestHandler {

    private final RetrofitApi retrofitApi;
    private final Context context;

    public ServerRequestHandler(Context context) {
        this.context = context;
        this.retrofitApi = ApiClient.getInstance().getApi();
    }

    public void registerUser(RegisterModel registerModel, ServerResponseListener<ApiEmptyDataResponse> listener) {
        Call<ApiEmptyDataResponse> call = retrofitApi.registerUser(registerModel);
        enqueueCall(call, listener);
    }

    public void loginUser(LoginModel loginModel, ServerResponseListener<ApiDataResponse<UserSession>> listener) {
        Call<ApiDataResponse<UserSession>> call = retrofitApi.loginUser(loginModel);
        enqueueCall(call, listener);
    }

    public void uploadImage(MultipartBody.Part image_file, ServerResponseListener<ApiDataResponse<EmotionModel>> listener) {
        Call<ApiDataResponse<EmotionModel>> call = retrofitApi.uploadImage(image_file);
        enqueueCall(call, listener);
    }


    public void GetPlaylist(String emotion, ServerResponseListener<ApiDataResponse<List<PlaylistModel>>> listener) {
        Call<ApiDataResponse<List<PlaylistModel>>> call = retrofitApi.get_playlist(emotion);
        enqueueCall(call, listener);
    }

    public void GetSongList(String playlist, ServerResponseListener<ApiDataResponse<List<SongsModel>>> listener) {
        Call<ApiDataResponse<List<SongsModel>>> call = retrofitApi.get_songsList(playlist);
        enqueueCall(call, listener);
    }

    public void uploadInput(String input, ServerResponseListener<ApiDataResponse<EmotionModel>> listener) {
        Call<ApiDataResponse<EmotionModel>> call = retrofitApi.uploadInput(input);
        enqueueCall(call, listener);
    }

    public void sendMessage(UserMessage userMessage, ServerResponseListener<List<BotResponse>> listener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + IP + ":5005/webhooks/rest/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Call<List<BotResponse>> call = api.sendMessage(userMessage);
        enqueueCall(call, listener);
    }

    private <T> void enqueueCall(Call<T> call, final ServerResponseListener<T> listener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Error in API response: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                UiHandlers.shortToast(context, t.getMessage());
            }
        });
    }
}
