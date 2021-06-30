package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GoalsInSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_in_subject);

        TextView header = findViewById(R.id.header);
        Bundle bundle = getIntent().getExtras();
        header.setText(bundle.getString("subjectName"));

    }
}