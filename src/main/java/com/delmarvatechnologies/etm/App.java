package com.delmarvatechnologies.etm;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rithm on 11/11/2017.
 */

public class App  extends Application {

    public static final String TAG = App.class.getSimpleName();

    private static App instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static synchronized App getInstance()
    {
        return instance;
    }

}