package com.example.interviewrgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;

public class MainLauncher extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        logo = findViewById(R.id.launcher_logo);
        MediaPlayer sound = MediaPlayer.create(this,R.raw.logo_transition_sound);
        Animation animate = AnimationUtils.loadAnimation(this,R.anim.logo_translate);
        sound.start();
        logo.startAnimation(animate);
        Handler handler = new Handler();
        Intent userDetailsIntent = new Intent(MainLauncher.this,UserDetails.class);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(userDetailsIntent);
            }
        },3000);
    }
}