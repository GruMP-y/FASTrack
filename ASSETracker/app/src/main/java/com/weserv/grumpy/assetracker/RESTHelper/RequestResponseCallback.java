package com.weserv.grumpy.assetracker.RESTHelper;

import com.android.volley.VolleyError;

/**
 * Created by baylefra on 1/30/2016.
 */
public interface RequestResponseCallback {

    public void onResponse(String response);
    public void onErrorResponse(VolleyError error);
}


