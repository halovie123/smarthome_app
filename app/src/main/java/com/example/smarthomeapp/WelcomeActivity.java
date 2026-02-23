package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        TextView tvDescription = findViewById(R.id.tvDescription);
        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_up));
        tvTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_up_delay1));
        tvSubtitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_up_delay2));
        tvDescription.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_up_delay2));
        btnGetStarted.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_up_delay3));

        btnGetStarted.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, AuthActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}