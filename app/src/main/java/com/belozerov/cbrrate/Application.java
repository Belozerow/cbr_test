package com.belozerov.cbrrate;

import android.content.Context;

/**
 * Created: Belozerov
 * Date: 25.12.2015
 */
public class Application extends android.app.Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = this;
    }
}
