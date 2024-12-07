package com.example.carpartsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PartsMenu extends AppCompatActivity {
    PartsDatabaseHelper partsDb;
    private SharedPreferences sharedPreferences;
    private TextView textViewParts;
    private Button buttonDisplay, buttonAddCart, buttonDisplayCart;
    private EditText editTextAddPart;
    private String selectedParts = "";
    private float totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_menu);

        // Initialize the database helper
        partsDb = new PartsDatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CartSummary", MODE_PRIVATE);

        // Initialize buttons/text fields
        textViewParts = findViewById(R.id.textViewParts);
        buttonDisplay = findViewById(R.id.buttonDisplay);
        buttonAddCart = findViewById(R.id.buttonAddCart);
        buttonDisplayCart = findViewById(R.id.buttonDisplayCart);
        editTextAddPart = findViewById(R.id.editTextAddPart);

        buttonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> partsList = partsDb.getAllParts();
                String allParts = "";
                for (String part : partsList) {
                    allParts += part + "\n";
                }
                textViewParts.setText(allParts);
            }
        });

        buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addPartID = editTextAddPart.getText().toString();
                String addPart = partsDb.getPartById(Integer.parseInt(addPartID));
                float addPartPrice = partsDb.getPriceById(Integer.parseInt(addPartID));

                if (addPart != null) {
                    selectedParts += addPart + "\n";
                    totalPrice += addPartPrice;
                    Toast.makeText(PartsMenu.this, "Part added to cart!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PartsMenu.this, "Failed to add part to cart.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDisplayCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCart();
            }
        });

        insertPart("Motor", "Toyota", "Good motor", 300.96F);

        // Example: Insert a part into the database
        insertPart("Engine Oil", "Car Supplies Co.", "High-quality synthetic engine oil", 29.99F);
        /*
        // Example: Retrieve all parts from the database
        ArrayList<String> partsList = getAllParts();
        for (String part : partsList) {
            Toast.makeText(this, part, Toast.LENGTH_SHORT).show(); // Display parts in Toasts
        }

        // Example: Update an existing part
        updatePart(1, "Updated Engine Oil", "Updated Distributor", "Updated Description", 34.99);

        // Example: Delete a part
        deletePart(1);*/
    }

    // Method to insert a new part into the database
    private void insertPart(String name, String distributor, String description, float price) {
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

    private void saveCartToSavedPreferences(String selectedItems, float totalCost) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedItems", selectedItems);
        editor.putFloat("totalCost", totalCost);
        editor.apply();
    }

    private void displayCart() {
        saveCartToSavedPreferences(selectedParts, totalPrice);

        Intent intent = new Intent(PartsMenu.this, CartSummary.class);
        startActivity(intent);
    }
}
