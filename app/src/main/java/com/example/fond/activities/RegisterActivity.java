package com.example.fond.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fond.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.FondTheme);
        setContentView(R.layout.activity_register);

        // Creating and matching view variables
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Setting a click listener on the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Attempting to register new user");

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Grab the username and password from the user
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Set the username and password to te ParseUser object
                user.setUsername(username);
                user.setPassword(password);

                // Call the API in the background
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        // If not null, there is a registration error
                        if (e != null) {
                            Log.e(TAG, "Registration error: " + e);
                            Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
                        }

                        // Otherwise, close this activity
                        Log.i(TAG, "Successful registration");
                        Toast.makeText(RegisterActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        goLoginActivity();
                        finish();
                    }
                });


            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
