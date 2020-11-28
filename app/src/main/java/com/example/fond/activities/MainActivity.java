package com.example.fond.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

import com.example.fond.fragments.profileFragment;
import com.example.fond.fragments.savedRecipesFragment;
import com.example.fond.fragments.searchRecipeFragment;
import com.example.fond.fragments.userFeedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.fond.R;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment userFeedFragment = new userFeedFragment();
        final Fragment searchRecipeFragment = new searchRecipeFragment();
        final Fragment savedRecipesFragment = new savedRecipesFragment();
        final Fragment profileFragment = new profileFragment();


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_user_feed:
                                fragment = userFeedFragment;
                                break;
                            case R.id.action_search:
                                fragment = searchRecipeFragment;
                                break;
                            case R.id.action_recipe:
                                fragment = savedRecipesFragment;
                                break;
                            case R.id.action_profile:
                                fragment = profileFragment;
                                break;
                            default:
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_user_feed);
    }
}