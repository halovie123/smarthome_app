package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private Button btnTabLogin, btnTabSignIn;
    private LinearLayout layoutLogin, layoutSignIn;
    private EditText etLoginEmail, etLoginPassword;
    private EditText etSignInEmail, etSignInPassword, etSignInConfirm;
    private Button btnLogin, btnSignIn;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_auth);
        initViews();
        btnTabLogin.setOnClickListener(v -> showLoginTab());
        btnTabSignIn.setOnClickListener(v -> showSignInTab());
        setupLogin();
        setupSignIn();
        showLoginTab();

        tvForgotPassword.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }

    private void initViews() {
        btnTabLogin   = findViewById(R.id.btnTabLogin);
        btnTabSignIn  = findViewById(R.id.btnTabSignIn);
        layoutLogin   = findViewById(R.id.layoutLogin);
        layoutSignIn  = findViewById(R.id.layoutSignIn);
        etLoginEmail  = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin      = findViewById(R.id.btnLogin);
        etSignInEmail = findViewById(R.id.etSignInEmail);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        etSignInConfirm  = findViewById(R.id.etSignInConfirmPassword);
        btnSignIn     = findViewById(R.id.btnSignIn);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    private void showLoginTab() {
        btnTabLogin.setBackgroundResource(R.drawable.tab_active_bg);
        btnTabLogin.setTextColor(getResources().getColor(android.R.color.white));
        btnTabSignIn.setBackgroundResource(android.R.color.transparent);
        btnTabSignIn.setTextColor(getResources().getColor(R.color.blue_primary));
        layoutLogin.setVisibility(View.VISIBLE);
        layoutSignIn.setVisibility(View.GONE);
    }

    private void showSignInTab() {
        btnTabSignIn.setBackgroundResource(R.drawable.tab_active_bg);
        btnTabSignIn.setTextColor(getResources().getColor(android.R.color.white));
        btnTabLogin.setBackgroundResource(android.R.color.transparent);
        btnTabLogin.setTextColor(getResources().getColor(R.color.blue_primary));
        layoutLogin.setVisibility(View.GONE);
        layoutSignIn.setVisibility(View.VISIBLE);
    }

    private void setupLogin() {
        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString().trim();
            String pass  = etLoginPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email))  { etLoginEmail.setError("Nhập email"); return; }
            if (TextUtils.isEmpty(pass))   { etLoginPassword.setError("Nhập mật khẩu"); return; }

            if (AccountManager.login(this, email, pass)) {
                AccountManager.saveCurrentUser(this, email);
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSignIn() {
        btnSignIn.setOnClickListener(v -> {
            String email   = etSignInEmail.getText().toString().trim();
            String pass    = etSignInPassword.getText().toString().trim();
            String confirm = etSignInConfirm.getText().toString().trim();
            if (TextUtils.isEmpty(email))  { etSignInEmail.setError("Nhập email"); return; }
            if (pass.length() < 6)         { etSignInPassword.setError("Tối thiểu 6 ký tự"); return; }
            if (!pass.equals(confirm))     { etSignInConfirm.setError("Mật khẩu không khớp"); return; }

            if (AccountManager.register(this, email, pass)) {
                Toast.makeText(this, "Đăng ký thành công! Hãy đăng nhập.", Toast.LENGTH_LONG).show();
                showLoginTab();
                etLoginEmail.setText(email);
            } else {
                etSignInEmail.setError("Email này đã được đăng ký!");
            }
        });
    }
}