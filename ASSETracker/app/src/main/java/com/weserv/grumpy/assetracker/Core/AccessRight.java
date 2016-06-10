package com.weserv.grumpy.assetracker.Core;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.Core.Connection.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.JSONArrayResponseCallback;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Vans on 9/27/2015.
 */
public class AccessRight {

    private JSONArray accessRight;
    private ArrayList<String> accessItems;
    private boolean isManager;
    private boolean isAdmin;
    private boolean isMIS;
    private boolean isAppAdmin;

    public AccessRight() {
        isManager = false;
        isAdmin = false;
        isMIS = false;
        isAppAdmin = false;
        accessItems = new ArrayList<String>();
    }

    public JSONArray getAccessRight() {
        return accessRight;
    }

    public ArrayList<String> getAccessItems() {
        return accessItems;
    }

    public void setAccessRight(JSONArray accessRight) {
        try {
            this.accessRight = new JSONArray(accessRight.toString());
            setAccessItems(accessRight);
        } catch (JSONException ex){
            Log.d(Common.LOGNAME,"SetAccessRight Error: " + ex.getMessage());
        }

    }

    public void setAccessRight2(JSONArray jsonArrAccessRight) {
        accessRight = jsonArrAccessRight;
//        try {
//            setAccessItems(jsonArrAccessRight);
//        } catch (JSONException ex){
//            //TODO: what to do? assume no access right?
//            Log.d("SET_ACCESS_ITEMS", ex.getMessage());
//        }
    }

    private void setAccessItems(JSONArray jsonArrAccessRight) throws JSONException {
        int accessLevel = 0;
        JSONObject jsonAccessRightItem;

        for (int i = 0; i < jsonArrAccessRight.length(); i++) {
            jsonAccessRightItem = jsonArrAccessRight.getJSONObject(i);
            accessLevel = jsonAccessRightItem.getInt("AccessLevel");
            switch (accessLevel) {
                case 1:
                    isAdmin = true;
                    accessItems.add("Asset Registration");
                    accessItems.add("Asset Assignment");
                    break;
                case 2:
                    isManager = true;
                    accessItems.add("Approvals");
                    break;
                case 3:
                    isMIS = true;
                    accessItems.add("MIS For Acceptance");
                    accessItems.add("MIS Assignments");
                    break;
                case 4:
                    isAppAdmin = true;
                    break;
            }
        }
    }

    public boolean hasAdminRights() {
        return this.isAdmin;
    }

    public boolean hasMISRights(){
        return this.isMIS;
    }

    public boolean hasAppAdminRights(){
        return this.isAppAdmin;
    }

    public boolean hasManagerRights(){
        return this.isManager;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (String item : accessItems) {
            text.append(item + " ");
        }
        return text.toString();
    }
}
