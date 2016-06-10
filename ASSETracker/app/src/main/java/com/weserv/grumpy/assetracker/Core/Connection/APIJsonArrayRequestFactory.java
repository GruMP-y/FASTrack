package com.weserv.grumpy.assetracker.Core.Connection;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import java.util.Map;

/**
 * Created by baylefra on 1/30/2016.
 */
public class APIJsonArrayRequestFactory extends APIRequestFactory {

    private Map<String, String> mParams;
    private JSONArrayResponseCallback callback;

    public APIJsonArrayRequestFactory(String ip, String apiCategory, String requestMessageCategory, int callType) {
        super(ip, apiCategory, requestMessageCategory, callType);
    }

    public APIJsonArrayRequestFactory(String ip, String apiCategory, String requestMessageCategory, String  parameter,int callType) {
        super(ip, apiCategory, requestMessageCategory, parameter, callType);
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }

    public JSONArrayResponseCallback getCallback() {
        return callback;
    }

    public void registerCallback(JSONArrayResponseCallback callback){
        this.callback = callback;
    }

    public JsonArrayRequest createRequest() {
        final Map<String, String> parms = getParams();
        JsonArrayRequest request = new JsonArrayRequest(getCallType(),getURL(), null, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {

                    JSONArrayResponseCallback callback = getCallback();
                    if (callback != null) {
                        callback.onJSONArrayResponse(response);
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: need to handle onErrorResponse from the caller class
                JSONArrayResponseCallback callback = getCallback();
                if (callback != null) {
                    callback.onJSONArrayErrorResponse(error);
                }
            }

        });

        return request;
    }
    }