package com.example.emotiondetection.utils;

import static com.example.emotiondetection.utils.Constants.AUDIO_TYPE;
import static com.example.emotiondetection.utils.Constants.IMAGE_TYPE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;

public class MediaUtils {

    private final Activity activity;

    public MediaUtils(Activity activity) {
        this.activity = activity;
    }


    public void pickImage(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    public void captureImage(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }

    public void pickAudio(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    public void captureAudio(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        launcher.launch(intent);
    }


    public void showMediaDialog(ActivityResultLauncher<Intent> mediaLauncher, String mediaType) {
        CharSequence[] options;

        if (mediaType.equals(IMAGE_TYPE)) {
            options = new CharSequence[]{"Capture Image", "Upload Image"};
        } else if (mediaType.equals(AUDIO_TYPE)) {
            options = new CharSequence[]{"Record Audio", "Upload Audio"};
        } else {
            // Handle unsupported media types
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose an option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Capture Media
                        if (mediaType.equals(IMAGE_TYPE)) {
                            captureImage(mediaLauncher);
                        } else {
                            captureAudio(mediaLauncher);
                        }
                        break;
                    case 1:
                        // Upload Media
                        if (mediaType.equals(IMAGE_TYPE)) {
                            pickImage(mediaLauncher);
                        } else {
                            pickAudio(mediaLauncher);
                        }
                        break;
                }
            }
        });
        builder.show();
    }

}
