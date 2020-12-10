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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fond.BuildConfig;
import com.example.fond.R;
import com.example.fond.adapters.ListItemAdapter;
import com.example.fond.adapters.RecipeIngredientsAdapter;
import com.example.fond.adapters.RecipeInstructionAdapter;
import com.example.fond.data.model.ExtendedIngredient;
import com.example.fond.data.model.Recipe;
import com.example.fond.data.model.Step;
import com.example.fond.data.remote.SpoonacularService;
import com.example.fond.data.remote.SpoonacularUtils;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
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
    private RecyclerView rvIngredients;
    private RecyclerView rvInstructions;
    private SpoonacularService service;
    private List<String> instructions;
    private List<String> ingredients;
    private String apiKey = BuildConfig.SPOONACULAR_API_KEY;
    protected ListItemAdapter recipeInstructionAdapter;
    protected ListItemAdapter recipeIngredientsAdapter;

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
        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvInstructions = view.findViewById(R.id.rvInstructions);

        instructions = new ArrayList<>();
        ingredients = new ArrayList<>();


        recipeInstructionAdapter = new ListItemAdapter(getContext(), instructions);
        recipeIngredientsAdapter = new ListItemAdapter(getContext(), ingredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInstructions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInstructions.setAdapter(recipeInstructionAdapter);
        rvIngredients.setAdapter(recipeIngredientsAdapter);
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
                    final Markwon markwon = Markwon.create(getContext());
                    markwon.setMarkdown(tvRecipeSummary, recipe.getSummary());

                    getRecipeInstructions(recipe);
                    getRecipeIngredients(recipe);
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

    private void getRecipeIngredients(Recipe recipe) {
        List<ExtendedIngredient> extendedIngredients = recipe.getExtendedIngredients();
        Log.d(TAG, "extendedIngredients Size = " + extendedIngredients.size());
        for (int i = 0; i<extendedIngredients.size(); i++){
            ingredients.add(extendedIngredients.get(i).getOriginalString());
            Log.d(TAG, "Step" + i + extendedIngredients.get(i).getOriginalString() );
        }

        recipeIngredientsAdapter.notifyDataSetChanged();
    }

    private void getRecipeInstructions(Recipe recipe) {
        List<Step> steps= recipe.getAnalyzedInstructions().get(0).getSteps();
        Log.d(TAG, "steps length = " + steps.size());
        for (int i = 0; i < steps.size(); i++){
            instructions.add(steps.get(i).getStep());
            Log.d(TAG, "Step" + i + steps.get(i).getStep() );
        }

        recipeInstructionAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}