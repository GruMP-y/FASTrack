package com.weserv.grumpy.assetracker.Core;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.Core.Connection.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.JSONArrayResponseCallback;
import com.weserv.grumpy.assetracker.Core.Connection.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.UI.MyApp;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Vans on 9/27/2015.
 */
public class Assets extends Observable
        implements RequestResponseCallback, JSONObjectResponseCallback, JSONArrayResponseCallback {

    protected JSONArray assets;
    protected JSONArray myAssets;
    protected JSONArray forAcceptance;
    protected MyApp app;

    protected int current_action = 0;

    public JSONArray getAssets() {
        return assets;
    }

    public void setAssets(JSONArray assets) {
        this.assets = assets;
        categorizeAssets();
    }

    public JSONArray getMyAssets() {
        return myAssets;
    }

    public JSONArray getForAcceptance() {
        return forAcceptance;
    }

    private void categorizeAssets() {
        try {
            myAssets = new JSONArray();
            forAcceptance = new JSONArray();
            JSONObject jsonAsset;

            for (int i = 0; i < assets.length(); i++) {
                jsonAsset = assets.getJSONObject(i);
                if (jsonAsset.getInt(Common.FLD_ASSET_STAT_ID) == 4) {
                    //assigned
                    if (jsonAsset.getInt(Common.FLD_ASSIGN_STAT_ID) == 2) {
                        //waiting for acceptance
                        forAcceptance.put(jsonAsset);
                    } else if ((jsonAsset.getInt(Common.FLD_ASSIGN_STAT_ID) == 3)) {
                        //accepted
                        myAssets.put(jsonAsset);
                    }
                }
            }
        } catch (JSONException ex) {
        }

    }

    public JSONObject getMyAssetByAssetTag(String assetTag){
        JSONObject asset;
        try {

            for (int i = 0; i < myAssets.length(); i++) {
                asset = myAssets.getJSONObject(i);
                if (asset.getString(Common.FLD_ASSET_TAG).equals(assetTag)){
                    return asset;
                }
            }
        }
        catch (JSONException ex){
        }
       return null;
    }

    public JSONObject getForAcceptanceByAssetTag(String assetTag){
        JSONObject asset;
        try {

            for (int i = 0; i < forAcceptance.length(); i++) {
                asset = forAcceptance.getJSONObject(i);
                if (asset.getString(Common.FLD_ASSET_TAG).equals(assetTag)){
                    return asset;
                }
            }
        }
        catch (JSONException ex){
        }
        return null;
    }


    public boolean retrieveFromWS(Activity anActivity,MyApp anApp)
    {
        this.app = anApp;
        String ip = app.getHostAddress();
        String empID = app.getSession().getEmployeeID();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENT_MESSAGE_CAT_GET_ASSIGNMENT_BY_EMPLOYEE_ID;
        int callType = Request.Method.GET;

        APIJsonArrayRequestFactory request = new APIJsonArrayRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);

        current_action = Common.PROCESS_LOADING_ASSETLIST;

        Log.i(Common.LOGNAME, "Assets : Retrieving from WS.");
        //Assume always true for now
        return true;
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
       // Log.i(Common.LOGNAME, "Main : JSONArray received ->" + response.toString());
        Log.d(Common.LOGNAME,"Assets onJSONArrayRespons");
        this.setAssets(response);

        Log.d(Common.LOGNAME, "Assets : MyAssets -> " + this.getMyAssets().toString());
        //Log.d(Common.LOGNAME, "Assets : For Acceptance -> " + this.getForAcceptance().toString());

        Session session = app.getSession();
        session.setAssetList(this);
        app.setSession(session);

        setChanged();
        notifyObservers();
    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        Log.i(Common.LOGNAME,"Assets : Error -> "+error.getMessage());
        setChanged();
        notifyObservers(Common.ERROR);
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
