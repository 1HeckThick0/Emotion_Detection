package com.example.emotiondetection.src.ui;

import static com.example.emotiondetection.utils.Constants.IMAGE_TYPE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.emotiondetection.R;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.EmotionModel;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.models.response.ApiEmptyDataResponse;
import com.example.emotiondetection.src.ui.music.PlaylistsActivity;
import com.example.emotiondetection.utils.FileUtils;
import com.example.emotiondetection.utils.MediaUtils;
import com.example.emotiondetection.utils.UiHandlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageFragment extends Fragment {
    ServerRequestHandler handler;
    private MediaUtils mediaUtils;
    Button upload, submit;
    ImageView image;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    TextView emotion;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        handler = new ServerRequestHandler(getActivity());
        mediaUtils = new MediaUtils(getActivity());
        upload = v.findViewById(R.id.upload);
        image = v.findViewById(R.id.image);
        submit = v.findViewById(R.id.submit);
        progressBar = v.findViewById(R.id.progressBar);
        emotion = v.findViewById(R.id.emotion);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upload.setOnClickListener(v -> mediaUtils.showMediaDialog(mediaLauncher, IMAGE_TYPE));

        submit.setOnClickListener(v -> {
            if (image != null) {
                UiHandlers.showProgress(progressBar, submit);
                UploadImage();
            }
        });
    }

    private void UploadImage() {
        File imageFile = FileUtils.bitmapToFile(imageBitmap, requireContext());
        String filePath = FileUtils.getRealPathFromUri(getContext(), Uri.fromFile(imageFile));
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file_content", file.getName(), requestFile);

        handler.uploadImage(imagePart, new ServerResponseListener<ApiDataResponse<EmotionModel>>() {
            @Override
            public void onSuccess(ApiDataResponse<EmotionModel> response) {
                UiHandlers.hideProgress(progressBar, submit);
                if (response.isStatus()) {
                    assert response.getData() != null;
//                    emotion.setText(response.getData().getEmotion());
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


    private final ActivityResultLauncher<Intent> mediaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    imageBitmap = null;

                    if (data != null && data.getData() != null) {
                        // Image selection from gallery
                        try {
                            InputStream inputStream = requireActivity().getContentResolver().openInputStream(data.getData());
                            imageBitmap = BitmapFactory.decodeStream(inputStream);
                            assert inputStream != null;
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                        // capture image
                        imageBitmap = (Bitmap) data.getExtras().get("data");
                    }
                    image.setImageBitmap(imageBitmap);
                    emotion.setText("");
                } else {
                    UiHandlers.shortToast(getActivity(), "Failed to access image");
                }
            });
}