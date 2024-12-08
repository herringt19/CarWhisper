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
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Example data
        Object[][] initialParts = {
                {"Motor", "Toyota", "Good motor", "300.96"},
                {"Engine Oil", "Car Supplies Co.", "High-quality synthetic engine oil", "29.99"},
                {"Rubber Rings", "Elastic Enterprise", "Shock adsorbent tires", "44.99"},
                {"Titanium Rear", "Cold Stone's Body Shop", "Heavy resistant tailgate", "140.50"},
                {"Bass Busters", "Radical Radio", "Subwoofers", "99.99"},
                {"Water Bouncer", "Crystal Clear Windows", "Windshield wipers", "11.99"},
                {"Rolling Cleats", "Outback Auto", "Off-Road tires", "51.99"},
                {"Zeppelin's Pillow", "Safety First Co.", "Thick airbag", "77.99"},
                {"The Truck's Shed", "Uncle Ed's Cars", "Heavy duty toolbox", "60.99"},
                {"Shiny Domes", "Regal Rims", "Chrome rims", "80.99"}
        };

        for (Object[] part : initialParts) {
            values.clear();
            values.put(COLUMN_NAME, (String) part[0]);
            values.put(COLUMN_DISTRIBUTOR, (String) part[1]);
            values.put(COLUMN_DESCRIPTION, (String) part[2]);
            values.put(COLUMN_PRICE, (String) part[3]);
            db.insert(TABLE_PARTS, null, values);
        }
    }

    public ArrayList<String> getAllParts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> partsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PARTS;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                    int priceIndex = cursor.getColumnIndex((COLUMN_PRICE));

                    if (idIndex != -1 && nameIndex != -1 && priceIndex != -1) {
                        int partId = cursor.getInt(idIndex);
                        String partName = cursor.getString(nameIndex);
                        String partPrice = cursor.getString(priceIndex);

                        // Combine ID and Name into a single string
                        String partEntry = "ID: " + partId + " " + partName + " - $" + partPrice;
                        partsList.add(partEntry);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return partsList;
    }

    public String getPartById(int partId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String part = null;

        String selectQuery = "SELECT * FROM " + TABLE_PARTS + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(partId)});

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                part = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            }
            cursor.close();
        }
        db.close();
        return part;
    }

    public float getPriceById(int partId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String priceSt = null;
        float price = 0;

        String selectQuery = "SELECT * FROM " + TABLE_PARTS + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(partId)});

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                priceSt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            }
            cursor.close();
        }
        db.close();

        if (priceSt != null) {
            price = Float.parseFloat(priceSt);
        }
        return price;
    }
}