package com.example.emotiondetection.src.auth;

import static com.example.emotiondetection.utils.Constants.USERTYPE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.emotiondetection.R;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.auth.RegisterModel;
import com.example.emotiondetection.models.response.ApiEmptyDataResponse;
import com.example.emotiondetection.utils.UiHandlers;
import com.example.emotiondetection.utils.Utils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button Register, SignIN;
    ProgressBar progressBar;
    ImageButton BackButton;
    AutoCompleteTextView Gender;
    EditText Name, Contact, Email, Password;
    String selectedGender;
    ServerRequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestHandler = new ServerRequestHandler(getApplicationContext());

        Name = findViewById(R.id.user_name);
        Contact = findViewById(R.id.user_phone);
        Email = findViewById(R.id.user_email);
        Password = findViewById(R.id.user_password);
        Gender = findViewById(R.id.user_gender);


        Register = findViewById(R.id.user_register);
        SignIN = findViewById(R.id.login_now);
        BackButton = findViewById(R.id.backButton);

        progressBar = findViewById(R.id.progressBar);

        Register.setOnClickListener(this);
        SignIN.setOnClickListener(this);
        BackButton.setOnClickListener(this);


        Utils.setGender(Gender, getApplicationContext());
        Gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = (String) parent.getItemAtPosition(position);
            }
        });
    }

    private void createUser() {
        UiHandlers.showProgress(progressBar, Register);
        RegisterModel registerModel = new RegisterModel(
                USERTYPE,
                Name.getText().toString(),
                Contact.getText().toString(),
                Email.getText().toString(),
                selectedGender,
                Password.getText().toString()

        );
        requestHandler.registerUser(registerModel, new ServerResponseListener<ApiEmptyDataResponse>() {
            @Override
            public void onSuccess(ApiEmptyDataResponse response) {
                UiHandlers.hideProgress(progressBar, Register);
                UiHandlers.shortToast(getApplicationContext(), response.getMessage());
                if (response.isStatus()) {
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                UiHandlers.hideProgress(progressBar, Register);
                UiHandlers.shortToast(getApplicationContext(), errorMessage);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_now || v.getId() == R.id.backButton) {
            getOnBackPressedDispatcher().onBackPressed();
        } else if (v.getId() == R.id.user_register) {
            if (validateUser()) {
                createUser();
            }
        }
    }

    private boolean validateUser() {
        String gender = Gender.getText().toString().trim();

        if (Name.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Name Field is required");
            return false;
        }
        if (Contact.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Phone number is required");
            return false;
        }
        if (!Patterns.PHONE.matcher(Contact.getText().toString()).matches() || Contact.length() < 10) {
            UiHandlers.shortToast(getApplicationContext(), "Invalid phone number");
            return false;
        }
        if (Email.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Email id is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
            UiHandlers.shortToast(getApplicationContext(), "Invalid email id");
            return false;
        }
        if (gender.isEmpty()) {
            UiHandlers.shortToast(getApplicationContext(), "please select gender");
            return false;
        }
        if (Password.length() == 0) {
            UiHandlers.shortToast(getApplicationContext(), "Password is required");
            return false;
        }

        return true;
    }
}