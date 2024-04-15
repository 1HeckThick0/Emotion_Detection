package com.example.emotiondetection.src.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.emotiondetection.R;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.EmotionModel;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.src.ui.music.PlaylistsActivity;
import com.example.emotiondetection.utils.MediaUtils;
import com.example.emotiondetection.utils.SpeechRecognitionHelper;
import com.example.emotiondetection.utils.UiHandlers;

public class MicFragment extends Fragment implements SpeechRecognitionHelper.SpeechRecognitionListener {

    ServerRequestHandler requestHandler;
    private MediaUtils mediaUtils;
    Button start, submit;
    ImageView image;
    Bitmap audioBitmap;
    TextView Text;
    Uri audioUri;
    ProgressBar progressBar;

    SpeechRecognitionHelper speechRecognitionHelper;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mic, container, false);
        requestHandler = new ServerRequestHandler(getContext());
        speechRecognitionHelper = new SpeechRecognitionHelper(requireContext(), this);

        mediaUtils = new MediaUtils(getActivity());
        start = v.findViewById(R.id.start);
        image = v.findViewById(R.id.image);
        Text = v.findViewById(R.id.audio_uri);
        submit = v.findViewById(R.id.submit);
        progressBar = v.findViewById(R.id.progressBar);
        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start.setOnClickListener(v -> {
            Text.setText("Listening....");
            start.setEnabled(false);
            speechRecognitionHelper.startListening();

        });
        submit.setOnClickListener(v -> {
            if (Text.length() == 0) {
                UiHandlers.shortToast(getActivity(), "Please capture audio");
            } else {
                UiHandlers.showProgress(progressBar, submit);
                uploadInput();
            }
        });
    }

    private void uploadInput() {
        requestHandler.uploadInput(Text.getText().toString(), new ServerResponseListener<ApiDataResponse<EmotionModel>>() {
            @Override
            public void onSuccess(ApiDataResponse<EmotionModel> response) {
                UiHandlers.hideProgress(progressBar, submit);

                if (response.isStatus()) {
                    assert response.getData() != null;
                    String emotion = response.getData().getEmotion();
                    Intent intent = new Intent(getContext(), PlaylistsActivity.class);
                    intent.putExtra("emotion", emotion);
                    startActivity(intent);
                } else {
                    UiHandlers.shortToast(getContext(), response.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                UiHandlers.hideProgress(progressBar, submit);
                UiHandlers.shortToast(getContext(), errorMessage);
            }
        });
    }


    @Override
    public void onSpeechRecognitionResult(String recognizedText) {
        Text.setText(recognizedText);
        start.setEnabled(true);
    }

    @Override
    public void onNoSpeechDetected() {

    }

    @Override
    public void onListeningPaused() {

    }

    @Override
    public void onListeningStopped() {

    }

    @Override
    public void onResume() {
        super.onResume();
        // Additional setup or start listening if needed
    }

    @Override
    public void onPause() {
        super.onPause();
        speechRecognitionHelper.stopListening();
    }
}