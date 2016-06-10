package com.weserv.grumpy.assetracker.Core.Connection;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by baylefra on 2/1/2016.
 */
public interface JSONObjectResponseCallback {
    public void onJSONResponse(JSONObject response);
    public void onJSONErrorResponse(VolleyError error);
}
