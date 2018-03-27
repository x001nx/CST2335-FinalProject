package com.example.delle6330.assignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dell E6330 on 3/4/2018.
 */

public class OSDatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "OS_DATABASE";
    public final static Integer VERSION = 2;
    public final static String TABLE_NAME = "STATIONS";
    public final static String STATION_NUMBER= "STATION_NUMBER";
    public final static String STATION_NAME = "STATION_NAME";

    private final static String QUERY_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + STATION_NUMBER + " text PRIMARY KEY, " + STATION_NAME + " text);";


    OSDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE);
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_NAME +";");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) // newVer < oldVer
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
        onCreate(db);  //make a new database
    }

}
