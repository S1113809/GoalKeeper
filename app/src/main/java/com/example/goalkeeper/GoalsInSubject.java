package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GoalsInSubject extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Goal> goals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_in_subject);

        TextView header = findViewById(R.id.header);
        TextView description = findViewById(R.id.descriptionView);
        Bundle bundle = getIntent().getExtras();
        header.setText(bundle.getString("subjectName"));
        description.setText(bundle.getString("subjectDesc"));

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        goals.add(new Goal("testgoal1"));
        goals.add(new Goal("testGoal2"));

        recyclerViewAdapter = new GoalAdapter(goals);
        recyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSubjectName = bundle.getString("subjectName");
                String currentSubjectDesc = bundle.getString("subjectDesc");
                int currentSubjectID = bundle.getInt("subjectId", 0);
                Bundle createBundle = new Bundle();
                createBundle.putString("subjectName", currentSubjectName);
                createBundle.putString("subjectDesc", currentSubjectDesc);
                createBundle.putInt("subjectId", currentSubjectID);
                Intent createGoalIntent = new Intent(getApplicationContext(), createGoal.class);
                createGoalIntent.putExtras(createBundle);
                startActivity(createGoalIntent);
            }
        });
    }
}