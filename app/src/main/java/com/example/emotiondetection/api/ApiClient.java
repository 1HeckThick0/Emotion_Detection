package com.example.emotiondetection.api;


import static com.example.emotiondetection.utils.Constants.ROOT_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient apiClient;
    private static Retrofit retrofit;


    private ApiClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder.build()).build();
    }
    public  static synchronized ApiClient getInstance()
    {
        if(apiClient == null)
        {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public RetrofitApi getApi()
    {

        return retrofit.create(RetrofitApi.class);
    }
}


