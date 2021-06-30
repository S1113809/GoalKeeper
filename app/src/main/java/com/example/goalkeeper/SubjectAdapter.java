package com.example.goalkeeper;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private OnNoteListener mOnNoteListener;

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card,parent,false);
        SubjectViewHolder subjectViewHolder = new SubjectViewHolder(v, mOnNoteListener);
        return subjectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.textView.setText(subjects.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    private List<Subject> subjects;
    public SubjectAdapter(List<Subject> subjects, OnNoteListener onNoteListener){
        this.subjects = subjects;
        this.mOnNoteListener = onNoteListener;
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        OnNoteListener onNoteListener;

        public SubjectViewHolder(View v, OnNoteListener onNoteListener){
            super(v);
            textView = v.findViewById(R.id.subjectTitle);
            this.onNoteListener = onNoteListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}
