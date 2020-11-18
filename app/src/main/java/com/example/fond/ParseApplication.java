package com.example.fond;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    String clientKey = BuildConfig.CLIENT_KEY;
    String appId = BuildConfig.APP_ID;

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(appId)
                .clientKey(clientKey)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
