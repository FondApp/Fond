package com.example.fond.models;

import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("Post")
public class UserPost extends ParseObject {
    public static final String TAG = "UserPost";

    // Declaring all Parse post attributes
    public static final String KEY_USER = "user";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PROFILE_PIC = "user_profile";


    // Ensure that your subclass has a public default constructor
    public UserPost() {
        super();
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public String getUsername() {
        try {
            return getParseUser(KEY_USER).fetchIfNeeded().getUsername();
        }
        catch (Exception e) {
            Log.e(TAG, "Error fetching username.");
        }

        return null;
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile file) {
        put(KEY_IMAGE, file);
    }
    
    public ParseFile getUserProfile() {
        try {
            return getParseUser(KEY_USER).getParseFile(KEY_PROFILE_PIC);
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving profile pic");
        }

        return null;

    }

    // No setter for user profile needed (set in User class)

}