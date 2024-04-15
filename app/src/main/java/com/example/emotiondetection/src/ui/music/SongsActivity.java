package com.example.emotiondetection.src.ui.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.emotiondetection.R;
import com.example.emotiondetection.adapters.PlaylistAdapter;
import com.example.emotiondetection.adapters.SongsAdapter;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.SongsModel;
import com.example.emotiondetection.models.UserSession;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.utils.UiHandlers;

import java.util.List;

public class SongsActivity extends AppCompatActivity {
    ServerRequestHandler requestHandler;
    Toolbar toolbar;
    ListView listView;
    ProgressBar progressBar;
    SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        requestHandler = new ServerRequestHandler(getApplicationContext());
        String playlist = getIntent().getStringExtra("playlist");

        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        getSongs(playlist);
    }

    private void getSongs(String playlist) {
        progressBar.setVisibility(View.VISIBLE);
        requestHandler.GetSongList(playlist, new ServerResponseListener<ApiDataResponse<List<SongsModel>>>() {
            @Override
            public void onSuccess(ApiDataResponse<List<SongsModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isStatus()) {
                    songsAdapter = new SongsAdapter(response.getData(), SongsActivity.this);
                    listView.setAdapter(songsAdapter);
                    songsAdapter.notifyDataSetChanged();
                } else {
                    UiHandlers.shortToast(getApplicationContext(), response.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                UiHandlers.shortToast(getApplicationContext(), errorMessage);

            }
        });
    }
}