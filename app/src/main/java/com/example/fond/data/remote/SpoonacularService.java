package com.example.fond.data.remote;

import com.example.fond.data.model.Recipe;
import com.example.fond.data.model.RecipeList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpoonacularService {
    @GET("/recipes/random?number=30")
    Call<RecipeList> getRandomRecipes(
            @Query("apiKey") String apiKey
    );

}
