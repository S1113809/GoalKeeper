package com.example.goalkeeper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    @NonNull
    @Override
    public GoalAdapter.GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card, parent, false);
        GoalViewHolder goalViewHolder = new GoalViewHolder(v);
        return goalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalAdapter.GoalViewHolder holder, int position) {
        holder.textView.setText(goals.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    private List<Goal> goals;

    public GoalAdapter(List<Goal> goals){
        this.goals = goals;
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public GoalViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.Title);
        }
    }
}
