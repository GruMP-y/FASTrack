package com.weserv.grumpy.assetracker.Core;

import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Developer on 4/13/2016.
 */
public class AssetType
{
    int typeID;
    String description;
    JSONObject assetTypeJSON;


    public AssetType(int typeID, String description) {
        this.typeID = typeID;
        this.description = description;
    }

    public AssetType() {
    }

    public int getTypeID() {
        String value;
        try {

            if ((assetTypeJSON.has(Common.FLD_ASSET_TYP_ID))) {
                value = assetTypeJSON.getString(Common.FLD_ASSET_TYP_ID);
            } else {
                value = Integer.toString(typeID);
            }
            return Integer.parseInt(value);

        } catch (Exception ex) {
            return 0;
        }
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getDescription() {

        String value;
        try {

            if ((assetTypeJSON.has(Common.FLD_ASSET_TYP_DESC))) {
                value = assetTypeJSON.getString(Common.FLD_ASSET_TYP_DESC);
            } else {
                value = description;
            }
            return value;

        } catch (Exception ex) {
            return "";
        }
    }

    public void setAssetTypeFromJSON(JSONObject assetTypeJSON)
    {
        this.assetTypeJSON = assetTypeJSON;
        this.typeID = getTypeID();
        this.description = getDescription();
    }


}
