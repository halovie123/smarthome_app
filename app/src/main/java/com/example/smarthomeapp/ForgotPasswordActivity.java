package com.example.smarthome;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail, etNewPass, etConfirmPass;
    private Button btnCheckEmail, btnResetPass, btnBack;
    private LinearLayout layoutStep1, layoutStep2;
    private TextView tvTitle;
    private String verifiedEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        etEmail      = findViewById(R.id.etForgotEmail);
        etNewPass    = findViewById(R.id.etNewPassword);
        etConfirmPass= findViewById(R.id.etConfirmNewPassword);
        btnCheckEmail= findViewById(R.id.btnCheckEmail);
        btnResetPass = findViewById(R.id.btnResetPass);
        btnBack      = findViewById(R.id.btnForgotBack);
        layoutStep1  = findViewById(R.id.layoutStep1);
        layoutStep2  = findViewById(R.id.layoutStep2);
        tvTitle      = findViewById(R.id.tvForgotTitle);

        btnBack.setOnClickListener(v -> finish());

        // Bước 1: Kiểm tra email
        btnCheckEmail.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) { etEmail.setError("Nhập email"); return; }
            if (AccountManager.emailExists(this, email)) {
                verifiedEmail = email;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.VISIBLE);
                tvTitle.setText("Đặt mật khẩu mới");
            } else {
                etEmail.setError("Email không tồn tại trong hệ thống");
            }
        });

        // Bước 2: Đặt mật khẩu mới
        btnResetPass.setOnClickListener(v -> {
            String newPass  = etNewPass.getText().toString().trim();
            String confirm  = etConfirmPass.getText().toString().trim();
            if (newPass.length() < 6) { etNewPass.setError("Tối thiểu 6 ký tự"); return; }
            if (!newPass.equals(confirm)) { etConfirmPass.setError("Mật khẩu không khớp"); return; }

            if (AccountManager.resetPassword(this, verifiedEmail, newPass)) {
                Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}