package com.example.carpartsapp;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PartsMenu extends AppCompatActivity {
    PartsDatabaseHelper partsDb;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_menu);

        // Initialize the database helper
        partsDb = new PartsDatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CartSummary", MODE_PRIVATE);

        // Example: Insert a part into the database
        insertPart("Engine Oil", "Car Supplies Co.", "High-quality synthetic engine oil", 29.99);

        // Example: Retrieve all parts from the database
        ArrayList<String> partsList = getAllParts();
        for (String part : partsList) {
            Toast.makeText(this, part, Toast.LENGTH_SHORT).show(); // Display parts in Toasts
        }

        // Example: Update an existing part
        updatePart(1, "Updated Engine Oil", "Updated Distributor", "Updated Description", 34.99);

        // Example: Delete a part
        deletePart(1);
    }

    // Method to insert a new part into the database
    private void insertPart(String name, String distributor, String description, double price) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("distributor", distributor);
        values.put("description", description);
        values.put("price", price);

        long result = partsDb.getWritableDatabase().insert("Parts", null, values);
        if (result == -1) {
            Toast.makeText(this, "Failed to insert part", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Part inserted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get all parts from the database
    private ArrayList<String> getAllParts() {
        ArrayList<String> partsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Parts";
        Cursor cursor = partsDb.getReadableDatabase().rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    // Ensure column "name" exists in your table schema
                    int columnIndex = cursor.getColumnIndex("name");
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

    // Method to update an existing part in the database
    private void updatePart(int partId, String name, String distributor, String description, double price) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("distributor", distributor);
        values.put("description", description);
        values.put("price", price);

        int rowsAffected = partsDb.getWritableDatabase().update("Parts", values, "part_id = ?", new String[]{String.valueOf(partId)});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Part updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update part", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete a part from the database
    private void deletePart(int partId) {
        int rowsDeleted = partsDb.getWritableDatabase().delete("Parts", "part_id = ?", new String[]{String.valueOf(partId)});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Part deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete part", Toast.LENGTH_SHORT).show();
        }
    }
}
