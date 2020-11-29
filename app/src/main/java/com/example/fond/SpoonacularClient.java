package com.example.fond;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpoonacularClient {
    @GET("/recipes/random?api_key={api_key}")
    Call<List<SpoonacularRecipe>> randomRecipes(@Path("api_key") String api_key);

}
