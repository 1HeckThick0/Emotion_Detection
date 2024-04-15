package com.example.emotiondetection.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emotiondetection.R;
import com.example.emotiondetection.models.PlaylistModel;
import com.example.emotiondetection.src.ui.music.SongsActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PlaylistAdapter extends BaseAdapter {
    Context context;
    List<PlaylistModel> playlistModels;

    public PlaylistAdapter(Context context, List<PlaylistModel> playlistModels) {
        this.context = context;
        this.playlistModels = playlistModels;
    }

    @Override
    public int getCount() {
        return playlistModels.size();
    }

    @Override
    public Object getItem(int position) {
        return playlistModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.playlist_laylout, parent, false);
        }

        MaterialCardView playlists_card = v.findViewById(R.id.playlists_card);
        ImageView imageView = v.findViewById(R.id.playlist_logo);
        TextView title = v.findViewById(R.id.playlist_title);
        TextView description = v.findViewById(R.id.playlist_description);

        PlaylistModel model = playlistModels.get(position);

        Glide.with(context).load(model.getImage()).into(imageView);
        title.setText(model.getTitle());
        description.setText(model.getDescription());

        playlists_card.setOnClickListener(v1 -> {
            Intent intent = new Intent(context, SongsActivity.class);
            intent.putExtra("playlist", model.getId());
            context.startActivity(intent);
        });

        return v;
    }
}
