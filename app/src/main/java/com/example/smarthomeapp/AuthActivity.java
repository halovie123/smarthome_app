package com.example.smarthomeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private Button btnLogIn, btnSignIn;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignIn = findViewById(R.id.btnSignIn);

        updateButtonState();

        btnLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void updateButtonState() {
        if (isLoginMode) {
            btnLogIn.setSelected(true);
            btnSignIn.setSelected(false);
        } else {
            btnLogIn.setSelected(false);
            btnSignIn.setSelected(true);
        }
    }
}
