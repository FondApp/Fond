package com.example.fond.data.remote;

import com.example.fond.data.model.ComplexSearchResults;
import com.example.fond.data.model.Recipe;
import com.example.fond.data.model.RecipeList;
import com.example.fond.data.model.SimilarRecipe;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SpoonacularService {
    @GET("/recipes/random?number=30")
    Call<RecipeList> getRandomRecipes(
            @Query("apiKey") String apiKey
    );

    @GET("/recipes/complexSearch?instructionsRequired=true&number=100")
    Call<ComplexSearchResults> getSearchedRecipes(
            @Query("apiKey") String apiKey,
            @QueryMap Map<String, String> options
    );

    @GET("/recipes/informationBulk")
    Call<List<Recipe>> getRecipeBulk(
            @Query("apiKey") String apiKey,
            @Query("ids") String ids
    );

    @GET("/recipes/{id}/information")
    Call<Recipe> getRecipeInformation(
            @Path("id") String id,
            @Query("apiKey") String apiKey
    );

    @GET("/recipes/{id}/similar")
    Call<List<SimilarRecipe>> getSimilarRecipes(
            @Path("id") String id,
            @Query("apiKey") String apiKey
    );
}
