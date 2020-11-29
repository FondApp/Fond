package com.example.fond.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.fond.RecipeSearchAdapter;
import com.example.fond.data.model.Recipe;
import com.example.fond.data.model.RecipeList;
import com.example.fond.data.remote.SpoonacularService;
import com.example.fond.data.remote.SpoonacularUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchRecipeFragment extends Fragment {
    public static final String TAG="SearchRecipeFragment";
    private RecyclerView rvSearchRecipes;
    protected RecipeSearchAdapter adapter;
    protected List<Recipe> allRecipes;
    private SpoonacularService service;
    private String apiKey = BuildConfig.SPOONACULAR_API_KEY;
    // protected  RecipeAdapter adapter;


    public searchRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        onCreate() is called to do initial creation of the fragment (Data initialization)
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