package com.weserv.grumpy.assetracker.Core.Connection;

import android.util.Log;

import com.android.volley.Request;
import com.weserv.grumpy.assetracker.Utils.Common;

/**
 * Created by baylefra on 1/30/2016.
 */
public abstract class APIRequestFactory {

    private String mAPICategory;
    private String mRequestMessageCategory;
    private int mCallType;
    private String mURL;
    private String mParameter;

    public APIRequestFactory(String ip, String apiCategory, String requestMessageCategory, int callType) {
        this.mAPICategory = apiCategory;
        this.mRequestMessageCategory = requestMessageCategory;
        this.mCallType = callType;
        ConstructURL(ip);
    }

    public APIRequestFactory(String ip, String apiCategory, String requestMessageCategory, String parameter, int callType) {
        this.mAPICategory = apiCategory;
        this.mRequestMessageCategory = requestMessageCategory;
        this.mCallType = callType;
        this.mParameter = parameter;
        ConstructURL(ip, mParameter);
    }

    public String getAPICategory() {
        return mAPICategory;
    }

    public String getRequestMessageCategory() {
        return mRequestMessageCategory;
    }

    public int getCallType() {
        return mCallType;
    }

    public String getURL() {

        return mURL;
    }



    public void ConstructURL(String ip) {

        String urlTemplate = MessageConstants.URL_TEMPLATE;
        mURL = String.format(urlTemplate, ip, mAPICategory, mRequestMessageCategory);
        Log.d(Common.LOGNAME,"Volley mURL->" + mURL.toString());
    }

    public void ConstructURL(String ip, String parameter) {

        String urlTemplate = MessageConstants.URL_TEMPLATE_WITH_PARAM;
        mURL = String.format(urlTemplate, ip, mAPICategory, mRequestMessageCategory, parameter);
        Log.d(Common.LOGNAME,"Volley mURL->" + mURL.toString());
    }

    public abstract Request createRequest();


}
