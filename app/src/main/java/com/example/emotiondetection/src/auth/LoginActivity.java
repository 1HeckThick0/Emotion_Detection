package com.example.emotiondetection.src.auth;

import static com.example.emotiondetection.utils.Constants.USERTYPE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.emotiondetection.R;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.UserSession;
import com.example.emotiondetection.models.auth.LoginModel;
import com.example.emotiondetection.models.response.ApiDataResponse;
import com.example.emotiondetection.src.HomeActivity;
import com.example.emotiondetection.utils.SharedPref;
import com.example.emotiondetection.utils.UiHandlers;

public class LoginActivity extends AppCompatActivity {

    Button Login, SignUp;
    EditText Email, Password;
    ProgressBar progressBar;
    ServerRequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestHandler = new ServerRequestHandler(getApplicationContext());

        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_password);

        Login = findViewById(R.id.login_submit);
        SignUp = findViewById(R.id.sign_up);

        progressBar = findViewById(R.id.progressBar);

        Login.setOnClickListener(v -> {
            if (isValidated()) {
                UiHandlers.showProgress(progressBar, Login);
                login();
            }
        });
        SignUp.setOnClickListener(v -> {
            Intent SignUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(SignUpIntent);
        });
    }


    private void login() {
        LoginModel loginModel = new LoginModel(
                Email.getText().toString(),
                Password.getText().toString(),
                USERTYPE
        );

        requestHandler.loginUser(loginModel, new ServerResponseListener<ApiDataResponse<UserSession>>() {
            @Override
            public void onSuccess(ApiDataResponse<UserSession> response) {
                UiHandlers.hideProgress(progressBar, Login);
                UiHandlers.shortToast(getApplicationContext(), response.getMessage());
                if (response.isStatus()) {
                    SharedPref.setIsAuthenticated(getApplicationContext(), true);
                    SharedPref.saveUserSession(getApplicationContext(), response.getData());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                UiHandlers.hideProgress(progressBar, Login);
                UiHandlers.shortToast(getApplicationContext(), errorMessage);
            }
        });
    }

    private boolean isValidated() {
        if (Email.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Email id is required");
            return false;
        }
        if (Password.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Password is required");
            return false;
        }
        return true;
    }
}