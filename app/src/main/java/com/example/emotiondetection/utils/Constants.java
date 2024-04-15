package com.example.emotiondetection.utils;

import android.Manifest;
import android.os.Build;

public interface Constants {

    String IP = "192.168.137.164";
    String ROOT_URL = "http:" + IP + ":8000";

    String USERTYPE = "user";

    int REQUEST_CODE_PERMISSIONS = 100;
    int REQUEST_CODE_PICK_IMAGE = 1001;
    int REQUEST_CODE_PICK_AUDIO = 1002;
    int REQUEST_CODE_CAPTURE_IMAGE = 1003;
    int REQUEST_CODE_CAPTURE_AUDIO = 1004;

    String IMAGE_TYPE = "image";
    String AUDIO_TYPE = "audio";
    String USER_LOGO = "/media/user.png";

}
