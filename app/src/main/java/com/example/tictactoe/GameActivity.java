package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private MediaPlayer gameMusic;
    private MediaPlayer clickSound;
    private Button pauseResumeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize and start background music
        gameMusic = MediaPlayer.create(this, R.raw.gameview);
        gameMusic.setLooping(true);
        gameMusic.start();

        // Initialize click sound
        clickSound = MediaPlayer.create(this, R.raw.switch24);

        // Find the Pause/Resume button and set initial text to "Pause"
        pauseResumeButton = findViewById(R.id.pauseResumeMusicButton);
        pauseResumeButton.setText("Pause");

        // Toggle music on button click
        pauseResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameMusic.isPlaying()) {
                    gameMusic.pause();
                    pauseResumeButton.setText("Resume");
                } else {
                    gameMusic.start();
                    pauseResumeButton.setText("Pause");
                }
            }
        });

        // Set up Tic-Tac-Toe buttons on the game board
        GridLayout gridLayout = findViewById(R.id.gameBoard);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }

                        // Play click sound
                        if (clickSound != null) {
                            clickSound.start();
                        }

                        if (player1Turn) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }

                        roundCount++;

                        if (checkForWin()) {
                            if (player1Turn) {
                                player1Wins();
                            } else {
                                player2Wins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            player1Turn = !player1Turn;
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameMusic != null && gameMusic.isPlaying()) {
            gameMusic.pause();
            pauseResumeButton.setText("Music"); // Update button text
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameMusic != null && !gameMusic.isPlaying()) {
            gameMusic.start();
            pauseResumeButton.setText("NoMusic"); // Update button text
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameMusic != null) {
            gameMusic.release();
            gameMusic = null;
        }
        if (clickSound != null) {
            clickSound.release();
            clickSound = null;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        return field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("") ||
                field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("");
    }

    private void player1Wins() {
        openResultActivity("Player 1 wins!");
    }

    private void player2Wins() {
        openResultActivity("Player 2 wins!");
    }

    private void draw() {
        openResultActivity("Draw!");
    }

    private void openResultActivity(String resultMessage) {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("RESULT_MESSAGE", resultMessage);
        startActivity(intent);
        finish();
    }

    public void exitApp(View view) {
        finish();  // Close the activity
    }

}
