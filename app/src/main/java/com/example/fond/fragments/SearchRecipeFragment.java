package com.example.fond.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fond.BuildConfig;
import com.example.fond.R;
import com.example.fond.adapters.RecipeSearchAdapter;
import com.example.fond.data.model.ComplexSearchResults;
import com.example.fond.data.model.Recipe;
import com.example.fond.data.model.RecipeList;
import com.example.fond.data.remote.SpoonacularService;
import com.example.fond.data.remote.SpoonacularUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRecipeFragment extends Fragment {
    public static final String TAG="SearchRecipeFragment";
    private RecyclerView rvSearchRecipes;
    protected RecipeSearchAdapter adapter;
    protected List<Recipe> allRecipes;
    private SpoonacularService service;
    private String apiKey = BuildConfig.SPOONACULAR_API_KEY;
    // protected  RecipeAdapter adapter;


    public SearchRecipeFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = SpoonacularUtils.getSpoonacularService();
        rvSearchRecipes = view.findViewById(R.id.rvSearchRecipes);
        allRecipes = new ArrayList<>();

        adapter = new RecipeSearchAdapter(getContext(), allRecipes, new RecipeSearchAdapter.RecipeItemListener() {
            @Override
            public void onRecipeClick(long id) {
                Toast.makeText(getContext(), "Recipe id is "+ id, Toast.LENGTH_SHORT).show();
            }
        });

        rvSearchRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchRecipes.setAdapter(adapter);

        loadRandomRecipes();

        // Create layout for one row in list
        // Create the adapter
        // Create thee data source
        // Set the adapter on the recycler view
        // Set the layout manager on the recycler view
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
        searchView.setQueryHint("Filter...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), "You searched for " + query, Toast.LENGTH_SHORT).show();

                final Map<String, String> recipeData = new HashMap<>();
                recipeData.put("query", query);

                service.getSearchedRecipes(apiKey, recipeData).enqueue(new Callback<ComplexSearchResults>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<ComplexSearchResults> call, Response<ComplexSearchResults> response) {
                        if(response.isSuccessful()){

                            String recipeIds = response.body().getSearchIds();
                            Log.d(TAG, "Recipe IDs loaded: " + recipeIds);

                            loadBulkRecipes(recipeIds);

                        }else{
                            Log.d(TAG, "ERROR pulling up recipe IDs: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ComplexSearchResults> call, Throwable t) {
                        Log.d(TAG, "ERROR: "+ t.toString());
                    }
                });




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadBulkRecipes(String recipeIds) {
        service.getRecipeBulk(apiKey, recipeIds).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    adapter.updateRecipes(response.body());
                    Log.d(TAG, "posts loaded from API" + response.body().toString());
                } else {
                    Log.d(TAG, "ERROR, returned " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "ERROR getting bulk recipes: "+ t.toString());
            }
        });
    }

    private void loadRandomRecipes() {
        service.getRandomRecipes(apiKey).enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                if(response.isSuccessful()){
                    adapter.updateRecipes(response.body().getRecipes());
                    Log.d(TAG, "posts loaded from API" + response.body().toString());
                } else {
                    Log.d(TAG, "ERROR, returned " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable t) {
                Log.d(TAG, "ERROR: "+ t.toString());
            }
        });
    }
}