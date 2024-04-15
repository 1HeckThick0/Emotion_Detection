package com.example.emotiondetection.utils;

import android.content.Context;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class ExoPlayerManager {
    private ExoPlayer player;
    PlayerView playerView;

    public ExoPlayerManager(Context context, PlayerView playerView) {
        this.playerView = playerView;
        player = new ExoPlayer.Builder(context).build();
        playerView.setPlayer(player);
    }

    public void playVideo(String videoUrl) {
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    player.play();
                }
            }
        });
    }

    public void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
