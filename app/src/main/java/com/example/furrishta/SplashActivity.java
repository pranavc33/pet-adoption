package com.example.furrishta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Splash screen duration in milliseconds
    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the ImageView and apply the fade-in animation
        ImageView logoImageView = findViewById(R.id.logoImageView);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImageView.startAnimation(fadeIn);

        // Delay navigation to the register page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToRegister();
            }
        }, SPLASH_DURATION);
    }

    private void navigateToRegister() {
        Intent intent = new Intent(SplashActivity.this, Register.class);
        startActivity(intent);
        finish(); // Finish the splash activity so user cannot go back to it
    }
}
