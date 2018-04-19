package com.example.delle6330.assignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ansht on 2018-04-08.
 */

public class QuizResultsDatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "QUIZ_DATA";
    private static int VERSION_NUM = 1;
    static final String ID_COLUMN = " _id";
    static final String QUESTION_TYPE = "QUESTION_TYPE";
    static final String ANSWER = "USER_ANSWER";
    static final String RIGHT_ANSWER = "RIGHT_ANSWER";
//    public static String databaseName;

    QuizResultsDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ DATABASE_NAME + " ("
                + ID_COLUMN + " INTEGER PRIMARY KEY autoincrement, "+
                QUESTION_TYPE + " TEXT, " +
                ANSWER + " TEXT," +
                RIGHT_ANSWER + " TEXT);" );

        Log.i("QuizResultsData", "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + ";");
        onCreate(db);

        Log.i("QuizResultsData", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);

    }

}
