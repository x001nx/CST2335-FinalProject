package com.example.delle6330.assignment1;

import android.content.Context;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Svetlana Netchaeva on 2018-04-08.
 * Class manages database creation and version management
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "movie";
    public static final String KEY_ID = "ID";
    public static final String KEY_title = "title";
    public static final String KEY_actors = "actors";
    public static final String KEY_length = "length";
    public static final String KEY_description = "description";
    public static final String KEY_rating = "rating";
    public static final String KEY_genre = "genre";
    public static final String KEY_url = "url";
    public static String DATABASE_NAME = "movies.db";
    public static int VERSION_NUM = 2;

    /**
     *  constructor that opens database file
     */
    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * method executes a string SQL statement
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL("CREATE TABLE " + TABLE_NAME +
                    "(" +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_title + " STRING, " + KEY_actors + " STRING, " +
                    KEY_length + " STRING, " + KEY_description + " STRING, " +
                    KEY_rating + " STRING, " + KEY_genre + " STRING, " + KEY_url + " STRING);"
            );
        } catch (Exception e) {
            Log.d("table create bad" ,e.getMessage());
        }
    }


    /**
     * method called when the database needs to be upgraded
     * @param db
     * @param oldVer
     * @param newVer
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
        Log.i("MovieDbHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }
}
