package com.example.fond.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Recipe_Favorites")
public class RecipeFavorites extends ParseObject {
    public static final String KEY_USER = "userId";
    public static final String KEY_RECIPE = "recipeId";

    public ParseUser getUser(){return getParseUser(KEY_USER);}
    public void setUser(ParseUser user) {put(KEY_USER, user);}

    public ParseRecipe getRecipe(){return (ParseRecipe) getParseObject(KEY_RECIPE);}
    public void setRecipe(ParseRecipe recipe) {put(KEY_RECIPE, recipe);}
}
