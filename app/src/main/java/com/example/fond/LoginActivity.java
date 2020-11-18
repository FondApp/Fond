package com.example.fond;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Creating and matching view variables
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Setting a click listener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login button click");

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                loginUser(username, password);
            }
        });

    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to log in user");

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                // If e is not null, this means something went wrong
                if (e != null) {
                    Log.e(TAG, "Issue with login: " + e.toString());
                    Toast.makeText(LoginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Otherwise, navigate to main activity
                Log.i(TAG, "Successful user login");
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
