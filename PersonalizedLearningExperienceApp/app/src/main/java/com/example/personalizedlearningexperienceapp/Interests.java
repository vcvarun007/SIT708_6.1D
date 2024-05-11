package com.example.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Interests extends AppCompatActivity {
    Button nextButton, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interests);
        nextButton = findViewById(R.id.next_button);
        btn1 = findViewById(R.id.app_dev);
        btn2 = findViewById(R.id.testing);
        btn3 = findViewById(R.id.algo);
        btn4 = findViewById(R.id.data_structure);
        btn5 = findViewById(R.id.web_dev);
        btn6 = findViewById(R.id.dev_ops);
        btn7 = findViewById(R.id.software_engineer);
        btn8 = findViewById(R.id.consultant);
        choice = findViewById(R.id.selected_interest);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn1.getText());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn2.getText());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn3.getText());
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn4.getText());
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn5.getText());
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn6.getText());
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn7.getText());
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice.setText(btn8.getText());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Interests.this, MainActivity.class);
                intent.putExtra("Interest", choice.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}