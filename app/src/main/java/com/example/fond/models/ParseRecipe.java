package com.example.fond.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ParseRecipe")
public class ParseRecipe extends ParseObject {

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_SPOONACULAR_RECIPEID = "recipeId";

    public ParseRecipe() { super(); }

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title){
        put(KEY_TITLE, title);
    }

    public String getImageUrl() {
        return getString(KEY_IMAGE_URL);
    }

    public void setImageUrl(String imageUrl) {
        put(KEY_IMAGE_URL, imageUrl);
    }

    public String getSummary() {
        return getString(KEY_SUMMARY);
    }

    public void setSummary(String summary){
        put(KEY_SUMMARY, summary);
    }

    public void setRecipeId(Integer recipeId){
        put(KEY_SPOONACULAR_RECIPEID, recipeId);
    }

    public int getRecipeId() {
        return getInt(KEY_SPOONACULAR_RECIPEID);
    }




}
