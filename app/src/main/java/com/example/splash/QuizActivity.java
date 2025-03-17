package com.example.splash;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

     TextView userNameTextView, questionNumber, questionText;
     RadioGroup optionsGroup;
     RadioButton option1, option2, option3, option4;
     Button previousButton, nextButton;

    private String username;
    private int currentQuestionIndex = 0;
    private int score = 0;
     int[] userAnswers = new int[10];

    // Question bank
     String[] questions = {
            "What is the largest desert in the world?",
            "Which gas makes up most of Earth's atmosphere?",
            "Who painted the Mona Lisa?",
            "What is the smallest prime number?",
            "Which continent has the most countries?",
            "What is the main ingredient in guacamole?",
            "Who discovered gravity?",
            "What is the longest river in the world?",
            "Which organ pumps blood in the human body?",
            "What is the primary source of energy for Earth?"
    };

     String[][] options = {
            {"Sahara", "Gobi", "Antarctic", "Kalahari"},
            {"Oxygen", "Nitrogen", "Carbon Dioxide", "Helium"},
            {"Van Gogh", "Picasso", "Da Vinci", "Monet"},
            {"1", "2", "3", "5"},
            {"Asia", "Europe", "Africa", "South America"},
            {"Tomato", "Avocado", "Onion", "Pepper"},
            {"Einstein", "Newton", "Galileo", "Tesla"},
            {"Amazon", "Nile", "Yangtze", "Mississippi"},
            {"Liver", "Brain", "Heart", "Lungs"},
            {"Sun", "Moon", "Wind", "Ocean"}
    };

     int[] correctAnswers = {2, 1, 2, 1, 2, 1, 1, 1, 2, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        username = getIntent().getStringExtra("USERNAME");

        userNameTextView = findViewById(R.id.userNameTextView);
        questionNumber = findViewById(R.id.questionNumber);
        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        previousButton = findViewById(R.id.btnPrevious);
        nextButton = findViewById(R.id.nextButton);

        userNameTextView.setText("Welcome, " + username + "!");

        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }

        loadQuestion(currentQuestionIndex);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();
                currentQuestionIndex--;
                loadQuestion(currentQuestionIndex);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveAnswer();

                if (currentQuestionIndex < 9) {
                    currentQuestionIndex++;
                    loadQuestion(currentQuestionIndex);
                } else {
                    calculateScore();
                    goToResult();
                }
            }
        });
    }

    private void loadQuestion(int index) {
        questionText.setText(questions[index]);
        questionNumber.setText("       Question " + (index + 1) + "/10");

        option1.setText(options[index][0]);
        option2.setText(options[index][1]);
        option3.setText(options[index][2]);
        option4.setText(options[index][3]);

        if (userAnswers[index] != -1) {
            ((RadioButton) optionsGroup.getChildAt(userAnswers[index])).setChecked(true);
        } else {
            optionsGroup.clearCheck();
        }

        previousButton.setEnabled(index > 0);
        nextButton.setText(index == 9 ? "Finish" : "Next");
    }

    private void saveAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            int answerIndex = optionsGroup.indexOfChild(findViewById(selectedId));
            userAnswers[currentQuestionIndex] = answerIndex;
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < 10; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
    }

    private void goToResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }
}