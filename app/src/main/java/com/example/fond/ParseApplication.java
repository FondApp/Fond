package com.example.fond;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: Figure out why receiving an occasional "com.parse.ParseRequest$ParseRequestException: i/o failure" error
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("BGw3ZXQSjmfO7mIwrcZ2FNNnytM8Ch0AeOM9Xnys")
                .clientKey("iQo1J2mgMMoXcogcMQ911Zv5PzsDfiXzNzFy74HF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
