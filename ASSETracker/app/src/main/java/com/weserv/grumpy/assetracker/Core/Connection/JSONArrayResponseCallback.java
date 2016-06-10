package com.weserv.grumpy.assetracker.Core.Connection;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by baylefra on 2/1/2016.
 */
public interface JSONArrayResponseCallback {
    public void onJSONArrayResponse(JSONArray response);
    public void onJSONArrayErrorResponse(VolleyError error);
}
