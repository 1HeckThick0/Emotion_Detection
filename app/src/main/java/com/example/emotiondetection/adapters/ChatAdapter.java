package com.example.emotiondetection.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emotiondetection.R;
import com.example.emotiondetection.models.ChatModel;
import com.example.emotiondetection.src.ui.music.SongsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<ChatModel> chatModelList;
    Context context;

    public ChatAdapter(List<ChatModel> chatModelList, Context context) {
        this.chatModelList = chatModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatModel chat = chatModelList.get(position);

        holder.user.setText(chat.getName());
        holder.message.setText(chat.getMessage());
        Glide.with(context)
                .load(chat.getLogo())
                .centerCrop()
                .disallowHardwareConfig()
                .into(holder.logo);

        if (chat.isShowClick()) {
            holder.playlist.setVisibility(View.VISIBLE);
        } else {
            holder.playlist.setVisibility(View.GONE);
        }

        holder.playlist.setOnClickListener(v -> {
            Intent intent = new Intent(context, SongsActivity.class);
            intent.putExtra("playlist", chat.getPlaylistId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView logo;
        TextView user, message;
        Button playlist;

        public ViewHolder(@NonNull View v) {
            super(v);
            logo = v.findViewById(R.id.user_logo);
            user = v.findViewById(R.id.user_name);
            message = v.findViewById(R.id.text_message);
            playlist = v.findViewById(R.id.textButton);
        }
    }
}
