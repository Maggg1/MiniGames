package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private MediaPlayer resultMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize MediaPlayer and start background music
        resultMusic = MediaPlayer.create(this, R.raw.result);
        resultMusic.setLooping(true);
        resultMusic.start();

        TextView resultTextView = findViewById(R.id.resultTextView);
        Button replayButton = findViewById(R.id.replayButton);
        Button exitButton = findViewById(R.id.exitButton);

        // Get the result from the intent
        String result = getIntent().getStringExtra("RESULT_MESSAGE");
        resultTextView.setText(result);

        // Set the Replay button to start a new game
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish(); // Close ResultActivity
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Close all activities and exit the app
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (resultMusic != null && resultMusic.isPlaying()) {
            resultMusic.pause(); // Pause music when activity goes to the background
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resultMusic != null) {
            resultMusic.start(); // Resume music when activity returns to the foreground
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resultMusic != null) {
            resultMusic.release(); // Release MediaPlayer resources
            resultMusic = null;
        }
    }

    public void exitApp(View view) {
        finish();  // Close the activity
    }
}
