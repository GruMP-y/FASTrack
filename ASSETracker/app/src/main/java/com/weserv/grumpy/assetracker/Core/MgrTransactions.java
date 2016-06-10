package com.weserv.grumpy.assetracker.Core;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.Core.Connection.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.UI.MyApp;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Trashvin on 5/25/16.
 */
public class MgrTransactions extends Assets {
    @Override
    public void setAssets(JSONArray assets) {
        this.assets = assets;
        categorizeAssets();
    }

    //Get the transactions
    public boolean retrieveFromWS(Activity anActivity, MyApp anApp)
    {
        this.app = anApp;
        String ip = app.getHostAddress();
        String empID = app.getSession().getEmployeeID();
        String api = MessageConstants.TRANSACTIONS_API;
        String message = MessageConstants.TRANSACTIONS_MESSAGE_CAT_GET_MGR_TRANS_BY_EMPLOYEE_ID;
        int callType = Request.Method.GET;

        APIJsonArrayRequestFactory request = new APIJsonArrayRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);

        current_action = Common.PROCESS_LOADING_ASSETLIST;

        Log.i(Common.LOGNAME, "MGRTransactions : Retrieving from WS.");
        //Assume always true for now
        return true;
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
        //Log.i(Common.LOGNAME, "MISTransactions : JSONArray received ->" + response.toString());

        //this.setAssets(response);
        this.setAssets(response);
        Session session = app.getSession();
        session.setMgrTransactions(this);
        app.setSession(session);

        Log.d(Common.LOGNAME,"MGRTransactions onJSONArrayResponse");
        setChanged();
        notifyObservers();
    }

    private void categorizeAssets() {
        try {
            myAssets = new JSONArray();
            forAcceptance = new JSONArray();
            JSONObject jsonAsset;

            for (int i = 0; i < assets.length(); i++) {
                jsonAsset = assets.getJSONObject(i);
                forAcceptance.put(jsonAsset);
            }
        }
        catch (JSONException ex) {
        }

    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        Log.i(Common.LOGNAME,"MGRTransactions : Error -> "+error.getMessage());
        setChanged();
        notifyObservers(Common.ERROR);
    }


}
