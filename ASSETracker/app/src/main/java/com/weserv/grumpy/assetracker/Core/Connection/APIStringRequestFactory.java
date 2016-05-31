package com.weserv.grumpy.assetracker.Core.Connection;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by baylefra on 1/30/2016.
 */
public class APIStringRequestFactory extends APIRequestFactory {

    private Map<String, String> mParams;
    private RequestResponseCallback callback;

    public APIStringRequestFactory(String ip, String apiCategory, String requestMessageCategory, int callType) {
        super(ip, apiCategory, requestMessageCategory, callType);
    }

    public APIStringRequestFactory(String ip, String apiCategory, String requestMessageCategory, String parameter,int callType) {
        super(ip, apiCategory, requestMessageCategory,parameter, callType);
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }

    public RequestResponseCallback getCallback() {
        return callback;
    }

    public void registerCallback(RequestResponseCallback callback){
        this.callback = callback;
    }

    public StringRequest createRequest() {
        final Map<String, String> parms = getParams();
        StringRequest request;

        request = new StringRequest(getCallType(), getURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO: need to handle onResponse from the caller class
                RequestResponseCallback callback = getCallback();
                if (callback != null) {
                    callback.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: need to handle onErrorResponse from the caller class
                RequestResponseCallback callback = getCallback();
                if (callback != null) {
                    callback.onErrorResponse(error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Put email to parameter
                Map<String, String> map = parms;

                return map;
            }
        };

        return request;
    }
}
