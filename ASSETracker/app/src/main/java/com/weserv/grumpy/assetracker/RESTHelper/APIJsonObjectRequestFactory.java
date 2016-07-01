package com.weserv.grumpy.assetracker.RESTHelper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by baylefra on 1/30/2016.
 */
public class APIJsonObjectRequestFactory extends APIRequestFactory {

    private Map<String, String> mParams;
    private JSONObjectResponseCallback callback;

    public APIJsonObjectRequestFactory(String ip, String apiCategory, String requestMessageCategory, int callType) {
        super(ip, apiCategory, requestMessageCategory, callType);
    }

    public APIJsonObjectRequestFactory(String ip, String apiCategory, String requestMessageCategory,String parameter, int callType) {
        super(ip, apiCategory, requestMessageCategory, parameter, callType);
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }

    public JSONObjectResponseCallback getCallback() {
        return callback;
    }

    public void registerCallback(JSONObjectResponseCallback callback){
        this.callback = callback;
    }

    public JsonObjectRequest createRequest() {
        final Map<String, String> parms = getParams();
        JsonObjectRequest request;

        request = new JsonObjectRequest(getCallType(),getURL(),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObjectResponseCallback callback = getCallback();
                        if (callback != null) {
                            callback.onJSONResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: need to handle onErrorResponse from the caller class
                JSONObjectResponseCallback callback = getCallback();
                if (callback != null) {
                    callback.onJSONErrorResponse(error);
                }
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Put email to parameter
                Map<String, String> map = parms;

                return map;
            }
        };
        {

        };
            return request;
        }
    }
