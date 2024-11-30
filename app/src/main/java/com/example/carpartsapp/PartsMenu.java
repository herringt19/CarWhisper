package com.example.carpartsapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PartsMenu extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_menu);

        sharedPreferences = getSharedPreferences("CartSummary", MODE_PRIVATE);



    }
}
