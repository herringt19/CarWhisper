package com.example.carpartsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CartSummary extends AppCompatActivity {

    private TextView title_tv, selectedParts_tv, totalCost_tv;
    private Button backToMenu_btn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_summary);

        sharedPreferences = getSharedPreferences("CartSummary", MODE_PRIVATE);

        title_tv = findViewById(R.id.title_tv);
        selectedParts_tv = findViewById(R.id.selectedParts_tv);
        totalCost_tv = findViewById(R.id.totalCost_tv);
        backToMenu_btn = findViewById(R.id.backToMenu_btn);

        String selectedItems = sharedPreferences.getString("selectedItems", "");
        String totalCost = sharedPreferences.getString("totalCost", "");

        selectedParts_tv.setText("Selected Items: \n" + selectedItems);
        totalCost_tv.setText("Total Cost: \n" + totalCost);

        backToMenu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartSummary.this, PartsMenu.class);
                startActivity(intent);
            }
        });
    }
}
