package com.example.fond.networking;

import android.app.Application;

import com.example.fond.models.UserPost;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    String clientKey = BuildConfig.CLIENT_KEY;
    String appId = BuildConfig.APP_ID;

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Registering the UserPost class
        ParseObject.registerSubclass(UserPost.class);

        // Initializing the Parse SDK
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(appId)
                .clientKey(clientKey)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
