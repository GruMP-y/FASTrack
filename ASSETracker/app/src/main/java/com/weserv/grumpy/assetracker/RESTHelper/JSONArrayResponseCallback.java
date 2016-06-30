package com.weserv.grumpy.assetracker.RESTHelper;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by baylefra on 2/1/2016.
 */
public interface JSONArrayResponseCallback {
    public void onJSONArrayResponse(JSONArray response);
    public void onJSONArrayErrorResponse(VolleyError error);
}
