package com.example.emotiondetection.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emotiondetection.R;
import com.example.emotiondetection.models.SongsModel;
import com.example.emotiondetection.src.ui.music.SongPlayer;
import com.example.emotiondetection.utils.FileUtils;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.List;

public class SongsAdapter extends BaseAdapter {

    List<SongsModel> songsModelList;
    Context context;

    public SongsAdapter(List<SongsModel> songsModelList, Context context) {
        this.songsModelList = songsModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return songsModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.songs_layout, parent, false);
        }

        RelativeLayout relativeLayout = v.findViewById(R.id.song_list);
        ShapeableImageView song_image = v.findViewById(R.id.song_image);
        TextView song_title = v.findViewById(R.id.song_title);
        TextView song_artist = v.findViewById(R.id.song_artist);

        SongsModel model = songsModelList.get(position);

        Glide.with(context).load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(song_image);
        song_title.setText(model.getTitle());
        song_artist.setText(model.getArtist());


        relativeLayout.setOnClickListener(v1 -> {
            Intent intent = new Intent(context, SongPlayer.class);
            intent.putExtra("song", (Serializable) model);
            context.startActivity(intent);
        });
        return v;
    }
}
