package com.weserv.grumpy.assetracker.RESTHelper;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by baylefra on 1/29/2016.
 */
public class HttpVolleyRequestSender{

    private Context caller;

    public HttpVolleyRequestSender(Activity caller) {
        this.caller = caller.getApplicationContext();
    }

    public void sendRequest(APIRequestFactory request) {
        RequestQueue requestQueue = Volley.newRequestQueue(caller);

        Volley.newRequestQueue(caller).add(request.createRequest());
    }

}
