package com.example.emotiondetection.src.ui.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.ui.PlayerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emotiondetection.R;
import com.example.emotiondetection.models.SongsModel;
import com.example.emotiondetection.utils.ExoPlayerManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class SongPlayer extends AppCompatActivity {
    PlayerView playerView;
    MaterialCardView back;
    TextView song_title;
    //    ShapeableImageView song_image;
    ExoPlayerManager exoPlayerManager;

    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        SongsModel song = (SongsModel) getIntent().getSerializableExtra("song");

        System.out.println(song);
        assert song != null;
        uri = song.getSong_file();

        playerView = findViewById(R.id.playerView);
        back = findViewById(R.id.back);
        song_title = findViewById(R.id.song_title);
//        song_image = findViewById(R.id.song_image);


        exoPlayerManager = new ExoPlayerManager(this, playerView);

        song_title.setText(song.getTitle());
        playerView.setBackground(Drawable.createFromPath(song.getImage()));
//        Glide.with(this).load(song.getImage())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(song_image);

        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayerManager.playVideo(String.valueOf(uri));
    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayerManager.releasePlayer();
    }
}