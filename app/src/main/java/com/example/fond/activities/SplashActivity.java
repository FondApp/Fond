package com.example.fond.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by mcfarkar on 09,December,2020
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    startActivity(new Intent(SplashActivity.this,LoginActivity.class));

    finish();

    }
}
