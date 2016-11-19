package com.sunikita.bbapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.sunikita.bbapp.content.ExerciseActivityHistoryDataProvider;

import java.util.Arrays;

/**
 * Created by StarLord on 2016-11-12.
 */

public class ExerciseRecordActivity extends AppCompatActivity implements AddLogDialogFragment.NoticeDialogListener{

    private RecyclerView recyclerView;
    private ExerciseActivityHistoryDataProvider mProvider;
    private String exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exerciseName = getIntent().getStringExtra("ExerciseName");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(exerciseName);
        }

        mProvider = new ExerciseActivityHistoryDataProvider(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.exerciseRecordRecyclerView);
        recyclerView.setAdapter(new ExerciseRecordAdaptor(mProvider.getActivityHistoryByName(exerciseName)));
    }

    public void addEntry(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        AppCompatDialogFragment newFragment = new AddLogDialogFragment();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onDialogPositiveClick(Pair<Float, Integer> record) {
        mProvider.addRecord(exerciseName, Arrays.asList(record));
        ExerciseRecordAdaptor adaptor = (ExerciseRecordAdaptor) recyclerView.getAdapter();
        adaptor.refreshRecords(mProvider.getActivityHistoryByName(exerciseName));
    }
}