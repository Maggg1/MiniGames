package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;  // Declare MediaPlayer for background music
    private boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MediaPlayer and start music
        mediaPlayer = MediaPlayer.create(this, R.raw.main); // Ensure the music file is in res/raw folder
        mediaPlayer.setLooping(true);  // Make the music loop
        playMusic();  // Start the music when the activity starts

        // Set click listeners for buttons
        findViewById(R.id.tictactoe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rockpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RockPaperScissorsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Play music if it's not already playing
    private void playMusic() {
        if (!isMusicPlaying) {
            mediaPlayer.start();
            isMusicPlaying = true;
        }
    }

    // Stop the music
    private void stopMusic() {
        if (isMusicPlaying) {
            mediaPlayer.pause();
            isMusicPlaying = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the music if the activity goes to background
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            isMusicPlaying = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the music when the activity comes back to the foreground
        if (mediaPlayer != null && !isMusicPlaying) {
            playMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the media player when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
