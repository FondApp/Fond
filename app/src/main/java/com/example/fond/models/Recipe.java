package com.example.fond.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Recipe {
    String objectId;
    String title;
    String imageUrl;
    String summary;

//    NOTE: This constructor assumes our implementation will pass the recipe JSONObject like in our
//    previous projects. I'm not sure this is the best implementation if we're using retrofit, so
//    this is mostly a placeholder until we have the API networking done. We most likely will want
//    to use the following endpoints for this class:
//    - recipes/{id}/information
//    - recipes/{id}/analyzedInstructions
    public Recipe(JSONObject jsonObject) throws JSONException {
        objectId = jsonObject.getString("id");
        title = jsonObject.getString("title");
        imageUrl = jsonObject.getString("image");
        summary = jsonObject.getString("summary");
    }
}
