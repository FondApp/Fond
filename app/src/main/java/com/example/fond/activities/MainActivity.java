package com.example.fond.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fond.fragments.ComposeFragment;
import com.example.fond.fragments.ProfileFragment;
import com.example.fond.fragments.RecipeDetailsFragment;
import com.example.fond.fragments.SavedRecipesFragment;
import com.example.fond.fragments.SearchRecipeFragment;
import com.example.fond.fragments.UserFeedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.fond.R;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements UserFeedFragment.OnPostButtonSelectedListener,
                                                               ComposeFragment.OnSubmitListener,
                                                               SearchRecipeFragment.onRecipeSelectedListener,
        ProfileFragment.OnLogoutButtonSelectedListener
{
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

    @Override
    public void onDataSubmit(ParseException e) {
        // If not null, go back to user feed fragment and launch a toast to inform user
        if (e != null) {
            Log.i(TAG, "Error in saving post - returning to User Feed");
            Toast.makeText(this, "Photo not saved", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, go back to user feed fragment, without the toast
            Log.i(TAG, "Returning back to User Feed; photo saved");
            Toast.makeText(this, "Photo saved!", Toast.LENGTH_SHORT).show();
        }

        userFeedFragment = new UserFeedFragment();
        fragmentManager.beginTransaction().replace(R.id.flContainer, userFeedFragment).commit();
    }


    @Override
    public void onRecipeListenerClick(long id) {
        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(id);
        fragmentManager.beginTransaction().replace(R.id.flContainer, recipeDetailsFragment).commit();
    }

    @Override
    public void onLogoutButtonClick(Context context) {
        Log.i(TAG, "Loggingout");
        Toast.makeText(context, "Goodbye!", Toast.LENGTH_SHORT).show();
        ParseUser.logOut();
        Intent loginscreen = new Intent(this, LoginActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginscreen);
        this.finish();
    }
}