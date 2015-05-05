package com.example.ahmadfauzi.testsqlitedb.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 5111100057 on 4/22/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "foodtestV2.db",
    TABLE_NAME_FT = "foodtest",
    COLUMN_ID_TABLE_FT = "idFT",
    COLUMN_NAME_TABLE_FT = "nameFT",
    COLUMN_REAGENT_TABLE_FT = "reagentFT",
    COLUMN_RESULT_TABLE_FT = "resultFT",
    COLUMN_PHOTO_TABLE_FT = "photoFT";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME_FT + " (" + COLUMN_ID_TABLE_FT + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_TABLE_FT + " TEXT NOT NULL," + COLUMN_REAGENT_TABLE_FT + " TEXT NOT NULL," + COLUMN_RESULT_TABLE_FT + " TEXT NOT NULL," + COLUMN_PHOTO_TABLE_FT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(getClass().getName(), "Upgrade db from version " + oldVersion + " to " + newVersion + " that erase all data");
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_FT);
    }
}
