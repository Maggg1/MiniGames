package com.example.tictactoe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RockPaperScissorsActivity extends AppCompatActivity {

    private TextView computerChoiceLabel, playerChoiceLabel, resultLabel, scoreLabel;
    private ImageView playerImage, computerImage; // ImageViews for player and computer choices
    private int playerScore = 0;
    private MediaPlayer mediaPlayer;  // Declare MediaPlayer for background music
    private boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_paper_scissors);

        // Initialize views
        computerChoiceLabel = findViewById(R.id.computer_choice);
        playerChoiceLabel = findViewById(R.id.player_choice);
        resultLabel = findViewById(R.id.result);
        scoreLabel = findViewById(R.id.score);

        // Initialize ImageViews
        playerImage = findViewById(R.id.player_image);
        computerImage = findViewById(R.id.computer_image);

        // Initialize buttons
        Button rockButton = findViewById(R.id.rock_button);
        Button paperButton = findViewById(R.id.paper_button);
        Button scissorsButton = findViewById(R.id.scissors_button);

        // Set button click listeners
        rockButton.setOnClickListener(v -> playGame("Rock"));
        paperButton.setOnClickListener(v -> playGame("Paper"));
        scissorsButton.setOnClickListener(v -> playGame("Scissors"));

        // Initialize MediaPlayer and start music
        mediaPlayer = MediaPlayer.create(this, R.raw.rps); // Ensure the music file is in res/raw folder
        mediaPlayer.setLooping(true);  // Make the music loop
        playMusic();  // Start the music when the activity starts
    }

    private void playGame(String playerChoice) {
        String computerChoice = getComputerChoice();
        String result = getResult(playerChoice, computerChoice);

        // Update labels
        computerChoiceLabel.setText("Computer: " + computerChoice);
        playerChoiceLabel.setText("Player: " + playerChoice);
        resultLabel.setText("Result: " + result);

        // Update images
        setImageForChoice(playerImage, playerChoice);
        setImageForChoice(computerImage, computerChoice);

        // Update score if player wins
        if (result.equals("Player wins")) {
            playerScore++;
            scoreLabel.setText("Your score: " + playerScore);
        }
    }

    private String getComputerChoice() {
        String[] choices = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        int index = random.nextInt(3);
        return choices[index];
    }

    private String getResult(String player, String computer) {
        if (player.equals(computer)) {
            return "It's a tie";
        } else if ((player.equals("Rock") && computer.equals("Scissors")) ||
                (player.equals("Scissors") && computer.equals("Paper")) ||
                (player.equals("Paper") && computer.equals("Rock"))) {
            return "Player wins";
        } else {
            return "Computer wins";
        }
    }

    private void setImageForChoice(ImageView imageView, String choice) {
        switch (choice) {
            case "Rock":
                imageView.setImageResource(R.drawable.rock);
                break;
            case "Paper":
                imageView.setImageResource(R.drawable.paper);
                break;
            case "Scissors":
                imageView.setImageResource(R.drawable.scissors);
                break;
        }
    }

    // Play music if not already playing
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

    // Exit the app
    public void exitApp(View view) {
        stopMusic();  // Stop music when exiting
        finish();  // Close the activity
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
        // Resume the music when activity comes back to the foreground
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
