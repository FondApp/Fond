package com.example.fond.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.fond.fragments.ComposeFragment;
import com.example.fond.fragments.ProfileFragment;
import com.example.fond.fragments.SavedRecipesFragment;
import com.example.fond.fragments.SearchRecipeFragment;
import com.example.fond.fragments.UserFeedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.fond.R;

public class MainActivity extends AppCompatActivity implements UserFeedFragment.OnPostButtonSelectedListener {
    public static final String TAG = "MainActivity";
    protected Fragment userFeedFragment;
    protected Fragment searchRecipeFragment;
    protected Fragment savedRecipesFragment;
    protected Fragment profileFragment;
    protected FragmentManager fragmentManager;
    protected Fragment composeFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        userFeedFragment = new UserFeedFragment();
        searchRecipeFragment = new SearchRecipeFragment();
        savedRecipesFragment = new SavedRecipesFragment();
        profileFragment = new ProfileFragment();


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

    @Override
    public void onPostButtonClick(Context context) {
        Log.i(TAG,"Launching the compose fragment");

        // Launch the Compose Fragment
        composeFragment = new ComposeFragment();
        fragmentManager.beginTransaction().replace(R.id.flContainer, composeFragment).commit();
    }
}