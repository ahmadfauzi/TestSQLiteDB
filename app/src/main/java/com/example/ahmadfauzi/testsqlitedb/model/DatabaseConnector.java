package com.example.ahmadfauzi.testsqlitedb.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 5111100057 on 4/22/2015.
 */
public class DatabaseConnector {
    private MySQLiteHelper mySQLiteHelper;

    public DatabaseConnector(Context context){
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    public long insertFT(FoodTest foodTest){
        long foodtest_insert = 0;

        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_TABLE_FT, foodTest.getIdFT());
        values.put(MySQLiteHelper.COLUMN_NAME_TABLE_FT, foodTest.getNameFT());
        values.put(MySQLiteHelper.COLUMN_REAGENT_TABLE_FT, foodTest.getReagentFT());
        values.put(MySQLiteHelper.COLUMN_RESULT_TABLE_FT, foodTest.getResultFT());

        foodtest_insert = db.insert(MySQLiteHelper.TABLE_NAME_FT, null, values);
        db.close();
        return foodtest_insert;
    }

    public ArrayList<FoodTest> getFTList(){
        SQLiteDatabase db = mySQLiteHelper.getReadableDatabase();

        ArrayList<FoodTest> foodTestsList = new ArrayList<FoodTest>();

        Cursor cursor;
        cursor = db.query(MySQLiteHelper.TABLE_NAME_FT, null, null, null, null, null, null);
        int counter = 0;
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){
                FoodTest foodTest = new FoodTest();
                foodTest.setIdFT(cursor.getInt(cursor.getColumnIndex((MySQLiteHelper.COLUMN_ID_TABLE_FT))));
                foodTest.setNameFT(cursor.getString(cursor.getColumnIndex((MySQLiteHelper.COLUMN_NAME_TABLE_FT))));
                foodTest.setReagentFT(cursor.getString(cursor.getColumnIndex((MySQLiteHelper.COLUMN_REAGENT_TABLE_FT))));
                foodTest.setResultFT(cursor.getString(cursor.getColumnIndex((MySQLiteHelper.COLUMN_RESULT_TABLE_FT))));
                foodTestsList.add(foodTest);
                counter++;

                cursor.moveToNext();
            }
            Log.d("DatabaseConnector", "take list of FoodTest success, total: " + counter);
            cursor.close();
            db.close();
        }else{
            Log.d("DatabaseConnector", "take list of FoodTest failed ");
            cursor.close();
            db.close();
        }
        return foodTestsList;
    }
}
