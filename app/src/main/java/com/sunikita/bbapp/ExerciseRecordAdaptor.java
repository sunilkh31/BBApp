package com.sunikita.bbapp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunikita.bbapp.content.ExActivityRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StarLord on 2016-11-12.
 */

public class ExerciseRecordAdaptor
        extends RecyclerView.Adapter<ExerciseRecordAdaptor.ExerciseRecordListViewHolder>{

    private List<ExActivityRecord> activityRecords;

    public ExerciseRecordAdaptor(List<ExActivityRecord> activityRecords) {
        this.activityRecords = activityRecords;
    }

    @Override
    public ExerciseRecordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exercise_record_rowlayout, parent, false);
        return new ExerciseRecordListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseRecordListViewHolder holder, int position) {
        final ExActivityRecord activityRecord = activityRecords.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        String formattedDate = formatter.format(activityRecord.getEntryDate());
        holder.exerciseRecordDate.setText(formattedDate);
        LinearLayoutManager manager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.exerciseSingleRecordRecyclerView.setLayoutManager(manager);
        holder.exerciseSingleRecordRecyclerView.setAdapter(new ExerciseSingleRecordAdaptor(activityRecord.getRecords()));
    }

    @Override
    public int getItemCount() {
        return activityRecords.size();
    }

    public void refreshRecords(List<ExActivityRecord> activityRecords) {
        this.activityRecords.clear();
        this.activityRecords.addAll(activityRecords);
        this.notifyDataSetChanged();
    }

    public static class ExerciseRecordListViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseRecordDate;
        private RecyclerView exerciseSingleRecordRecyclerView;

        public ExerciseRecordListViewHolder(View itemView) {
            super(itemView);
            exerciseRecordDate = (TextView) itemView.findViewById(R.id.exerciseRecordDate);
            exerciseSingleRecordRecyclerView = (RecyclerView) itemView.findViewById(R.id.exerciseSingleRecordRecyclerView);
        }
    }

    public class ExerciseSingleRecordAdaptor
            extends RecyclerView.Adapter<ExerciseSingleRecordAdaptor.ExerciseSingleRecordListViewHolder>{

        private List<Pair<String, String>> records = new ArrayList<>();

        public ExerciseSingleRecordAdaptor(List<Pair<Float, Integer>> records) {
            this.records.add(new Pair<>("lbs: ", "Rep: "));
            for(Pair<Float, Integer> value : records) {
                if(value.first.intValue() == value.first) {
                    this.records.add(new Pair<>(String.valueOf(value.first.intValue()), value.second.toString()));
                } else {
                    this.records.add(new Pair<>(value.first.toString(), value.second.toString()));
                }
            }
        }

        @Override
        public ExerciseSingleRecordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.exercise_record_single_rowlayout, parent, false);
            return new ExerciseSingleRecordListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExerciseSingleRecordListViewHolder holder, int position) {
            Pair<String, String> record = this.records.get(position);
            holder.weightValue.setText(record.first);
            holder.repsValue.setText(record.second);
        }

        @Override
        public int getItemCount() {
            return this.records.size();
        }

        public class ExerciseSingleRecordListViewHolder extends RecyclerView.ViewHolder {

            private TextView repsValue;
            private TextView weightValue;

            public ExerciseSingleRecordListViewHolder(View itemView) {
                super(itemView);
                repsValue = (TextView) itemView.findViewById(R.id.repsRecordValue);
                weightValue = (TextView) itemView.findViewById(R.id.weightRecordValue);
            }
        }
    }
}
