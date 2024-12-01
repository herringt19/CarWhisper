package com.example.carpartsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PartsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PartCollection.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PARTS = "Parts";
    private static final String COLUMN_ID = "part_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DISTRIBUTOR = "distributor";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";


    public PartsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PARTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DISTRIBUTOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_PRICE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS);
        onCreate(db);


    }
}