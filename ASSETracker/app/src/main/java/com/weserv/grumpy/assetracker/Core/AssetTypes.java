package com.weserv.grumpy.assetracker.Core;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.JSONArrayResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.RESTHelper.RequestResponseCallback;
import com.weserv.grumpy.assetracker.View.MyApp;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Observable;

/**
 * Created by Developer on 4/13/2016.
 */
public class AssetTypes extends Observable
        implements RequestResponseCallback, JSONObjectResponseCallback, JSONArrayResponseCallback {

    private ArrayList<AssetType> assetTypes;
    private JSONArray jsonAssetTypes;
    private MyApp app;

    private int current_action = 0;

    public boolean retrieveFromWS(Activity anActivity,MyApp anApp,int anAssetClass)
    {
        this.app = anApp;
        String ip = app.getHostAddress();
        String assetClass = Integer.toString(anAssetClass);
        String api = MessageConstants.ASSETS_API;
        String message = MessageConstants.ASSETS_MESSAGE_CAT_GET_ASSET_TYPES;
        int callType = Request.Method.GET;

        APIJsonArrayRequestFactory request = new APIJsonArrayRequestFactory(ip, api, message,assetClass, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);

        current_action = Common.PROCESS_LOADING_ASSETTYPES;

        Log.i(Common.LOGNAME, "AssetTypes : Retrieving from WS.");
        //Assume always true for now
        return true;
    }

    private void fillAssetTypes()
    {
        assetTypes = new ArrayList<AssetType>();

        for(int i=0; i < jsonAssetTypes.length(); i++)
        {
            AssetType temp = new AssetType();

            try {
                temp.setAssetTypeFromJSON(jsonAssetTypes.getJSONObject(i));
                assetTypes.add(temp);
            }catch(Exception ex)
            {
                Log.d(Common.LOGNAME,"AssetTypes : Error ->" + ex.toString());
            }
        }
    }

    public ArrayList<String> getAssetTypeDescriptions()
    {
        ArrayList<String> data = new ArrayList<String>();

        for(int i=0; i< this.assetTypes.size(); i++)
        {
            data.add(assetTypes.get(i).getDescription());
        }

        return data;
    }

    public int getAssetTypeID(int position)
    {
        return assetTypes.get(position).getTypeID();
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
        Log.i(Common.LOGNAME, "AssetTypes : JSONArrayResponse received -> "+ response.toString());

        jsonAssetTypes = response;
        fillAssetTypes();

        Session session = app.getSession();
        session.setAssetTypes(this);
        app.setSession(session);

        setChanged();
        notifyObservers();
    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        Log.i(Common.LOGNAME,"AssetTypes : Error -> "+error.getMessage());
        setChanged();
        notifyObservers("ERROR");
    }

    @Override
    public void onJSONResponse(JSONObject response) {

    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
