package com.example.emotiondetection.utils;

import static com.example.emotiondetection.utils.Constants.REQUEST_CODE_PERMISSIONS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class PermissionUtils {

    private static final String[] REQUIRED_PERMISSIONS_ANDROID_13_AND_ABOVE = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
    private static final String[] REQUIRED_PERMISSIONS_BELOW_ANDROID_13 = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    static String[] requiredPermissions = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ? REQUIRED_PERMISSIONS_ANDROID_13_AND_ABOVE
            : REQUIRED_PERMISSIONS_BELOW_ANDROID_13;

    public static void requestPermissions(Activity activity) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(new String[0]), REQUEST_CODE_PERMISSIONS);
        }
    }

    public static boolean hasPermissions(Context context) {
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void handlePermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Activity activity) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (!allGranted) {
                // Handle the case when some permissions are not granted
                // You can show a message or disable certain features
                UiHandlers.AlertDialog(activity, "Warning !", "Please allow all these permissions? Otherwise you cannot perform actions", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(activity);
                    }
                });
            }
        }
    }
}
