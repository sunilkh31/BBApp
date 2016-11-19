package com.sunikita.bbapp.content;

import static com.sunikita.bbapp.content.DbDefinition.ExerciseActivityHistory.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.BaseColumns._ID;

/**
 * Created by StarLord on 2016-11-12.
 */

public class ExerciseActivityHistoryDataProvider {

    private ExerciseDbHelper mDbHelper;
    private static final String entrySeparator = ";";
    private static final String repSeparator = ":";

    public ExerciseActivityHistoryDataProvider(Context context){
        mDbHelper = new ExerciseDbHelper(context);
    }

    public List<ExActivityRecord> getActivityHistoryByName(String exerciseName){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String whereClause = " " + EXERCISE_NAME + "=?";
        Cursor cursor = db.query(TABLE,
                new String[] {EXERCISE_NAME, DATE, ENTRY_RECORD},
                whereClause,
                new String[]{exerciseName.toLowerCase()},
                null, null, null);

        List<ExActivityRecord> activityHistory = new ArrayList<>();
        while(cursor.moveToNext()) {
            try {
                String date = cursor.getString(cursor.getColumnIndex(DATE));
                String history = cursor.getString(cursor.getColumnIndex(ENTRY_RECORD));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                ExActivityRecord record = new ExActivityRecord(dateFormat.parse(date));
                String[] entries = history.split(entrySeparator);
                for(String entry : entries) {
                    if(entry.isEmpty()) break;
                    String[] en = entry.split(repSeparator);
                    record.addRecord(Float.parseFloat(en[0]), Integer.parseInt(en[1]));
                }
                activityHistory.add(record);
            } catch (ParseException e) {
                Log.e("Error", Log.getStackTraceString(e));
            }
        }

        cursor.close();
        db.close();
        return activityHistory;
    }

    public void addRecord(String exerciseName, List<Pair<Float, Integer>> entries) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String whereClause = " " + EXERCISE_NAME + "=? AND " + DATE + "=?";
        Cursor cursor = db.query(TABLE,
                new String[]{EXERCISE_NAME, DATE, ENTRY_RECORD},
                whereClause,
                new String[]{exerciseName.toLowerCase(), date},
                null, null, null);

        String history = "";
        while(cursor.moveToNext()) {
            history = cursor.getString(cursor.getColumnIndex(ENTRY_RECORD));
        }

        for(Pair<Float, Integer> entry : entries){
            history += entry.first.toString() + repSeparator + entry.second.toString();
            history += entrySeparator;
        }

        ContentValues values = new ContentValues();
        values.put(EXERCISE_NAME, exerciseName.toLowerCase());
        values.put(DATE, date);
        values.put(ENTRY_RECORD, history);

        db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        cursor.close();
        db.close();
    }

    public List<String> getExercises() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(DbDefinition.Exercises.TABLE,
                new String[] {DbDefinition.Exercises.EXERCISE_NAME}, null, null, null, null, null);

        List<String> exercises = new ArrayList<>();
        while(cursor.moveToNext()) {
            String value = cursor.getString(cursor.getColumnIndex(DbDefinition.Exercises.EXERCISE_NAME));
            exercises.add(capitalize(value));
        }

        cursor.close();
        db.close();
        return exercises;
    }

    public void addExercise(String exerciseName) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXERCISE_NAME, exerciseName.toLowerCase());
        db.insertWithOnConflict(DbDefinition.Exercises.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if (Character.isWhitespace(ch)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
}
