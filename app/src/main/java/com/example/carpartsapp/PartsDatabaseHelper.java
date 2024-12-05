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

    public long insertPart(String name, String distributor, String description, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DISTRIBUTOR, distributor);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);

        return db.insert(TABLE_PARTS, null, values); // Returns the row ID of the newly inserted row
    }


    public ArrayList<String> getAllParts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> partsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PARTS;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int columnIndex = cursor.getColumnIndex(COLUMN_NAME);
                    if (columnIndex != -1) {
                        String partName = cursor.getString(columnIndex);
                        partsList.add(partName);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return partsList;
    }


    public Cursor getPartById(int partId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PARTS + " WHERE " + COLUMN_ID + " = ?";
        return db.rawQuery(selectQuery, new String[]{String.valueOf(partId)});
    }


    public void deletePart(int partId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARTS, COLUMN_ID + " = ?", new String[]{String.valueOf(partId)});
    }


    public int updatePart(int partId, String name, String distributor, String description, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DISTRIBUTOR, distributor);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);

        return db.update(TABLE_PARTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(partId)});
    }




}