package com.weserv.grumpy.assetracker.Core.Connection;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by baylefra on 1/30/2016.
 */
public interface RequestResponseCallback {

    public void onResponse(String response);
    public void onErrorResponse(VolleyError error);
}


