package com.example.goalkeeper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SubjectAdapter.OnNoteListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Subject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        subjects.add(new Subject("sport"));
        subjects.add(new Subject("Gezondheid"));
        subjects.add(new Subject("Irene Irriteren"));
        subjects.add(new Subject("School"));
        subjects.add(new Subject("Huishouden"));
        subjects.add(new Subject("Hobbies"));

        recyclerViewAdapter = new SubjectAdapter(subjects, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void OnNoteClick(int position) {
        Subject subject = subjects.get(position);
        String nameStr = subject.getName();
        Log.d("test", nameStr);
        Bundle bundle = new Bundle();
        bundle.putString("subjectName", nameStr);
        Intent toNextScreenIntent = new Intent(this, GoalsInSubject.class);
        toNextScreenIntent.putExtras(bundle);
        startActivity(toNextScreenIntent);
    }
}