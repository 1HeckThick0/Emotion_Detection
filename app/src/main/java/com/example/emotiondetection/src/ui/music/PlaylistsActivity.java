package com.example.emotiondetection.src.ui.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.emotiondetection.R;
import com.example.emotiondetection.adapters.PlaylistAdapter;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.PlaylistModel;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.utils.UiHandlers;

import java.util.List;

public class PlaylistsActivity extends AppCompatActivity {
    ServerRequestHandler requestHandler;
    ProgressBar progressBar;
    GridView gridView;
    Toolbar toolbar;
    String emotion;
    PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        requestHandler = new ServerRequestHandler(getApplicationContext());
        Intent intent = getIntent();
        emotion = intent.getStringExtra("emotion");

        progressBar = findViewById(R.id.progressBar);
        gridView = findViewById(R.id.gridView);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(emotion + " Playlists");
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        getPlaylist();
    }

    private void getPlaylist() {
        progressBar.setVisibility(View.VISIBLE);
        requestHandler.GetPlaylist(emotion, new ServerResponseListener<ApiDataResponse<List<PlaylistModel>>>() {
            @Override
            public void onSuccess(ApiDataResponse<List<PlaylistModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isStatus()) {
                    playlistAdapter = new PlaylistAdapter(PlaylistsActivity.this, response.getData());
                    gridView.setAdapter(playlistAdapter);
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