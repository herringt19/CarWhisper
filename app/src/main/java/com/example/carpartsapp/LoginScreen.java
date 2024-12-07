package com.example.carpartsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private UserProfileDatabaseHelper userProfileDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        userProfileDb = new UserProfileDatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginScreen.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAuthenticated = userProfileDb.authenticateUser(username, password);
                    if (isAuthenticated) {
                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Navigate to the next screen
                        Intent intent = new Intent(LoginScreen.this, PartsMenu.class);


                        startActivity(intent);
                        finish(); // Prevent going back to login screen
                    } else {
                        Toast.makeText(LoginScreen.this, "Invalid username or password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}