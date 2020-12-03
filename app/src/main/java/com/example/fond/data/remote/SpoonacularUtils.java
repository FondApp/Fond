package com.example.fond.data.remote;

public class SpoonacularUtils {
    public static final String BASE_URL = "https://api.spoonacular.com";
    public static SpoonacularService getSpoonacularService() {
        return RetrofitClient.getClient(BASE_URL).create(SpoonacularService.class);
    }

}
