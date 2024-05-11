package com.example.personalizedlearningexperienceapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Quiz extends AppCompatActivity {
    public interface MyApiService {
        @GET("api.php?amount=5&type=multiple")
        Call<QuizDataModel> getDataModel();
    }

    private TextView quesText, quesNum, scoreRes;
    private ProgressBar progressBar;
    private RadioGroup radioGroupOptions;
    private Button nextButton, restartButton;
    private List<QuizQuestion> questions;
    private int questionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initializeUI();
        nextButton.setOnClickListener(v -> handleNextButtonClick());
        loadQuizData();
    }

    private void initializeUI() {
        quesText = findViewById(R.id.question_text);
        quesNum = findViewById(R.id.question_number);
        progressBar = findViewById(R.id.progressBar);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        nextButton = findViewById(R.id.next_btn);
        scoreRes = findViewById(R.id.scoreRes);
        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> finish());
    }

    private void handleNextButtonClick() {
        RadioButton selectedButton = findViewById(radioGroupOptions.getCheckedRadioButtonId());
        if (selectedButton != null && questions != null && !questions.isEmpty()) {
            if (selectedButton.getText().toString().equals(questions.get(questionIndex).getCorrectAnswer())) {
                score++;
            }
        }
        if (questionIndex < questions.size() - 1) {
            questionIndex++;
            displayQuestion(questions.get(questionIndex));
        } else {
            showFinalScore();
        }
    }

    private void displayQuestion(QuizQuestion question) {
        quesText.setText(question.getQuestion());
        quesNum.setText("Question " + (questionIndex + 1));
        radioGroupOptions.removeAllViews();

        List<String> options = new ArrayList<>(question.getIncorrectAnswers());
        options.add(question.getCorrectAnswer());
        Collections.shuffle(options);

        for (String option : options) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioButton.setButtonTintList(getColorStateList(R.color.black));
            radioButton.setTextColor(getResources().getColor(R.color.black));
            radioGroupOptions.addView(radioButton);
        }
        radioGroupOptions.clearCheck(); // Clear any previous selection
    }

    private void showFinalScore() {
        progressBar.setVisibility(View.GONE);
        quesText.setVisibility(View.GONE);
        radioGroupOptions.setVisibility(View.GONE);
        quesNum.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        scoreRes.setText("Quiz completed! Your score: " + score + "/" + questions.size());
        scoreRes.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        restartButton.setText("Back to Home");
    }

    private void loadQuizData() {
        progressBar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiService myApiService = retrofit.create(MyApiService.class);

        myApiService.getDataModel().enqueue(new Callback<QuizDataModel>() {
            @Override
            public void onResponse(Call<QuizDataModel> call, Response<QuizDataModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    if (!questions.isEmpty()) {
                        displayQuestion(questions.get(questionIndex));
                    } else {
                        quesText.setText("No questions at the moment!");
                        nextButton.setEnabled(false);
                    }
                } else {
                    quesText.setText("Failed to load questions. Error: " + response.code());
                    nextButton.setEnabled(false);
                }
            }
            @Override
            public void onFailure(Call<QuizDataModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                quesText.setText("Failed to load data: " + t.getMessage());
                nextButton.setEnabled(false);
            }
        });
    }
}