package com.delmarvatechnologies.etm.singletons;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.delmarvatechnologies.etm.App;

/**
 * Created by rithm on 11/11/2017.
 */

public class NetworkRequestQueue {

    private static final String TAG = NetworkRequestQueue.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static NetworkRequestQueue mInstance;

    private NetworkRequestQueue() {}

    public static synchronized NetworkRequestQueue getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkRequestQueue();
        }
        return mInstance;
    }

    public void addToRequestQueue(Request req, String tag) {
        req.setTag(tag.isEmpty() ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(App.getInstance().getApplicationContext());
        }

        return mRequestQueue;
    }

    public void cancelPendingRequest(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
