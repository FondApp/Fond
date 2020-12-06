package com.example.fond.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fond.BuildConfig;
import com.example.fond.R;
import com.example.fond.data.model.Recipe;
import com.example.fond.data.remote.SpoonacularService;
import com.example.fond.data.remote.SpoonacularUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsFragment extends Fragment {
    public static final String ID = "id";
    public static final String TAG="RecipeDetailsFragment";

    private String id;
    private ImageView ivRecipeImage;
    private TextView tvTitle;
    private TextView tvRecipeSummary;
    private SpoonacularService service;
    private String apiKey = BuildConfig.SPOONACULAR_API_KEY;

    public static RecipeDetailsFragment newInstance(long id){
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        recipeDetailsFragment.setArguments(args);

        return  recipeDetailsFragment;
    }

    public RecipeDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//     called after onCreateView() and ensures that the fragment's root view is non-null.
//     Any view setup should happen here. E.g., view lookups, attaching listeners

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        service = SpoonacularUtils.getSpoonacularService();
        tvTitle = view.findViewById(R.id.tvTitle);
        tvRecipeSummary = view.findViewById(R.id.tvRecipeSummary);
        ivRecipeImage = view.findViewById(R.id.ivRecipeImage);

        if (getArguments() != null){
            id = String.valueOf(getArguments().getLong(ID));
//            Toast.makeText(getContext(), "Recipe id is "+ id, Toast.LENGTH_SHORT).show();
            loadRecipeDetails(id);
        }

    }

    private void loadRecipeDetails(String id) {
        service.getRecipeInformation(id, apiKey).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    Recipe recipe = response.body();
                    Log.d(TAG, "post loaded from API" + recipe);
                    tvTitle.setText(recipe.getTitle());
                    tvRecipeSummary.setText(recipe.getSummary());
                    Glide
                            .with(getContext())
                            .load(recipe.getImage())
                            .into(ivRecipeImage);
                } else {
                    Log.d(TAG, "ERROR, returned " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d(TAG, "ERROR getting recipe: "+ t.toString());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}