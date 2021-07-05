package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class createGoal extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        Button registerBtn = findViewById(R.id.finishGoalBtn);
        registerBtn.setOnClickListener(this);

        Button backBtn = findViewById(R.id.createGoalBackBtn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishGoalBtn:{
                break;
            }

            case R.id.createGoalBackBtn:{
                Bundle recievedBundle = getIntent().getExtras();
                Bundle sendBundle = new Bundle();
                sendBundle.putString("subjectName", recievedBundle.getString("subjectName"));
                sendBundle.putString("subjectDesc", recievedBundle.getString("subjectDesc"));
                sendBundle.putInt("subjectId", recievedBundle.getInt("subjectId"));
                Intent toPrevScreenIntent = new Intent(this, GoalsInSubject.class);
                toPrevScreenIntent.putExtras(sendBundle);
                startActivity(toPrevScreenIntent);
                break;
            }
        }
    }
}