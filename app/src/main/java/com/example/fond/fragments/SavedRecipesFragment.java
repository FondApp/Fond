package com.example.fond.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fond.R;
import com.example.fond.adapters.RecipeSavedAdapter;
import com.example.fond.models.ParseRecipe;
import com.example.fond.models.RecipeFavorites;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesFragment extends Fragment {

    public static final String TAG = "SavedRecipesFragment";
    private RecyclerView rvSavedRecipes;
    protected RecipeSavedAdapter adapter;
    protected List<ParseRecipe> allRecipes;
    private onRecipeSelectedListener listener;


    public SavedRecipesFragment() {
        // Required empty public constructor
    }

    public interface onRecipeSelectedListener {
        void onRecipeListenerClick(long id);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if the activity actually implements the interface
        if (context instanceof onRecipeSelectedListener) {
            // Assign the context to the listener
            listener = (onRecipeSelectedListener) context;
        }
        // If we forgot to implement the listener, throw an error with a reminder
        else {
            throw new ClassCastException(context.toString()
                    + " must implement SearchRecipeFragment.onRecipeSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        onCreate() is called to do initial creation of the fragment (Data initialization)
        setHasOptionsMenu(true);  // Need this so that onCreateOptionsMenu is called in the fragment
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        onCreateView() is called by Android once the Fragment should inflate a view
        return inflater.inflate(R.layout.fragment_saved_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//     called after onCreateView() and ensures that the fragment's root view is non-null.
//     Any view setup should happen here. E.g., view lookups, attaching listeners

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.miSearch);
        searchItem.setVisible(true);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Saved Recipes");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), "You searched for " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

        rvSavedRecipes = getView().findViewById(R.id.rvSavedRecipes);
        allRecipes = new ArrayList<>();

        adapter = new RecipeSavedAdapter(getContext(), allRecipes, new RecipeSavedAdapter.SavedRecipeItemListener() {
            @Override
            public void onRecipeClick(long id) {
                Toast.makeText(getContext(), "Recipe id is "+ id, Toast.LENGTH_SHORT).show();
                listener.onRecipeListenerClick(id);
            }
        });

        rvSavedRecipes.setAdapter(adapter);
        rvSavedRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        querySavedRecipes();
    }

    protected void querySavedRecipes() {
        ParseQuery<RecipeFavorites> query = ParseQuery.getQuery("Recipe_Favorites");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.include("recipeId");

        final List<ParseRecipe> queriedRecipes = new ArrayList<>();

        query.findInBackground(new FindCallback<RecipeFavorites>(){

            @Override
            public void done(List<RecipeFavorites> recipeFavorites, ParseException e) {
                if (recipeFavorites != null) {
                    for (RecipeFavorites recipeFavorite : recipeFavorites ){
                        Log.i(TAG, "Result: " + recipeFavorite.toString());
                        ParseRecipe parseRecipe = (ParseRecipe) recipeFavorite.getParseObject("recipeId");
                        Log.i(TAG, "ParseRecipe: "+ parseRecipe);
                        queriedRecipes.add(parseRecipe);
                    }
                    adapter.updateAll(queriedRecipes);
                } else if (e != null) {
                    Log.e(TAG, "Error: "+ e);
                }
            }
        });

    }
}