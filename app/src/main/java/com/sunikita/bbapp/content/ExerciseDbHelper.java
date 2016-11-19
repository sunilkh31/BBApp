package com.sunikita.bbapp.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by StarLord on 2016-11-12.
 */
public class ExerciseDbHelper extends SQLiteOpenHelper {

    public ExerciseDbHelper(Context context) {
        super(context, DbDefinition.DB_NAME, null, DbDefinition.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String exerciseHistory = "CREATE TABLE " + DbDefinition.ExerciseActivityHistory.TABLE + " ( " +
                DbDefinition.ExerciseActivityHistory.EXERCISE_NAME + " TEXT PRIMARY KEY NOT NULL, " +
                DbDefinition.ExerciseActivityHistory.DATE + " TEXT NOT NULL, " +
                DbDefinition.ExerciseActivityHistory.ENTRY_RECORD + " TEXT NOT NULL);";

        String exercises = "CREATE TABLE " + DbDefinition.Exercises.TABLE + " ( " +
                DbDefinition.Exercises.EXERCISE_NAME + " TEXT PRIMARY KEY NOT NULL);";

        sqLiteDatabase.execSQL(exerciseHistory);
        sqLiteDatabase.execSQL(exercises);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbDefinition.ExerciseActivityHistory.TABLE);
        onCreate(sqLiteDatabase);
    }
}
