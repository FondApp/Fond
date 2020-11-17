package com.example.fond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Toast.makeText(MainActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_search:
                                Toast.makeText(MainActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_recipe:
                                Toast.makeText(MainActivity.this, "Recipe!", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_profile:
                                Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}