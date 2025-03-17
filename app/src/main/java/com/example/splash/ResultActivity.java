package com.example.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

     TextView scoreboardTitle, playerName, resultText;
     ImageButton backButton;
     Button shareButton;

     String username;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        scoreboardTitle = findViewById(R.id.scoreboardTitle);
        playerName = findViewById(R.id.playerName);
        resultText = findViewById(R.id.resultText);
        backButton = findViewById(R.id.backButton);
        shareButton = findViewById(R.id.shareButton);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        if (username == null) username = "Guest"; // Fallback if username is null
        score = intent.getIntExtra("SCORE", 0); // Default to 0 if score not found

        playerName.setText(username);
        scoreboardTitle.setText("Score: " + score + "/10");

        if (score >= 8) {
            resultText.setText("Excellent!");
        } else if (score >= 5) {
            resultText.setText("Good Job!");
        } else {
            resultText.setText("Oops, Try Again!");
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareScore();
            }
        });
    }

    private void shareScore() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "I scored " + score + "/10 in the quiz!";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Quiz Result");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share your score via"));
    }
}