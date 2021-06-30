package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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
        Bundle bundle = getIntent().getExtras();
        header.setText(bundle.getString("subjectName"));

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        goals.add(new Goal("testgoal1"));
        goals.add(new Goal("testGoal2"));

        recyclerViewAdapter = new GoalAdapter(goals);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}