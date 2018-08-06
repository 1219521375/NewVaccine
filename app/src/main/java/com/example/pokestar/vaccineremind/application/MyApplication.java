package com.example.pokestar.vaccineremind.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by PokeStar on 2018/7/24.
 */

public class MyApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

    }


    public static Context getAppContext() {
        return appContext;
    }
}
