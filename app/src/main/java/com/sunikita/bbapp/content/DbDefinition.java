package com.sunikita.bbapp.content;

import android.provider.BaseColumns;

/**
 * Created by StarLord on 2016-07-24.
 */
public class DbDefinition {

    public static final String DB_NAME = "com.sunikita.BBApp.test";
    public static final int DB_VERSION = 3;

    public class ExerciseActivityHistory implements BaseColumns {
        public static final String TABLE = "ExerciseActivityHistory";
        public static final String EXERCISE_NAME = "exerciseName";
        public static final String DATE = "entryDate";
        public static final String ENTRY_RECORD = "entryRecord";
    }

    public class Exercises implements BaseColumns {
        public static final String TABLE = "Exercises";
        public static final String EXERCISE_NAME = "exerciseName";
    }
}
