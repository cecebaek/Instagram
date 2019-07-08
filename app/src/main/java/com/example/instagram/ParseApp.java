package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register Post as Parse subclass
        ParseObject.registerSubclass(Post.class);
        // build Parse Configuration
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("cecebaek")
                .clientKey("cece423helloFBU")
                .server("http://cecebaek-fbu-instagram.herokuapp.com/parse")
                .build();
        // initialize the Configuration
        Parse.initialize(configuration);
    }
}
