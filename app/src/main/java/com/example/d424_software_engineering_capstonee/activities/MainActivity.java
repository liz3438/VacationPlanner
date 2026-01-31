package com.example.d424_software_engineering_capstonee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.Toolbar;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.d424_software_engineering_capstonee.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logoutButton, enterButton;
    private VideoView backgroundVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            navigateToLogin();
            return;
        }

        setContentView(R.layout.activity_main);
        enterButton = findViewById(R.id.enterButton);
        logoutButton = findViewById(R.id.logoutButton);
        backgroundVideo = findViewById(R.id.backgroundVideo);

        setUpBackgroundVideo();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }


    private void setUpBackgroundVideo() {
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_animation);
        backgroundVideo.setVideoURI(video);

        backgroundVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.setLooping(true);
            }
        });

        backgroundVideo.start();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(backgroundVideo != null) {
            backgroundVideo.start();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(backgroundVideo != null) {
            backgroundVideo.pause();
        }
    }

    private void logout(){
        mAuth.signOut();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}






