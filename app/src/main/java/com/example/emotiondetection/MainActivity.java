package com.example.emotiondetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.emotiondetection.src.HomeActivity;
import com.example.emotiondetection.src.auth.LoginActivity;
import com.example.emotiondetection.utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;
        boolean isAuthenticated = SharedPref.getIsAuthenticated(getApplicationContext());

        if (isAuthenticated) {
            intent = new Intent(MainActivity.this, HomeActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        new Handler().postDelayed(() ->
        {
            startActivity(intent);
            finish();
        }, 2500);
    }
}