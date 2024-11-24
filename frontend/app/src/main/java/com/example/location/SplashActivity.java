package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2); // Your splash screen layout

        // Get views
        ImageView splashLogo = findViewById(R.id.splash_logo);
        ImageView textLogo = findViewById(R.id.id_textlogo);

        // Load animations
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation);

        // Start animations simultaneously
        splashLogo.startAnimation(logoAnimation);
        textLogo.startAnimation(textAnimation);

        // Listen for animation end
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start next activity after animations complete
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close splash screen
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
