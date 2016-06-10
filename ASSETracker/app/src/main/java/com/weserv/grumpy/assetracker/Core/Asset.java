package com.weserv.grumpy.assetracker.Core;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.Core.Connection.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.UI.MyApp;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observer;
import java.util.Observable;
/**
 * Created by Vans on 9/27/2015.
 */
public class Asset extends Observable
    implements RequestResponseCallback{
    private JSONObject assetDetail;

    private int mFixedAssetID;
    private int mAssignID;
    private String mModel;
    private String mBrand;
    private String mSerialNumber;
    private String mAssetTag;
    private int mAssetHardwareTypeID;
    private String mAssetHardwareTypeDescription;
    private int mAssetHardwareStatusID;
    private String mAssetHardwareStatusDescription;
    private String mRemarks;
    private String mAcquisitionDate;
    private String mFromID;
    private String mToID;
    private String mAdminID;
    private String mMISID;
    private String mDepartmentID;
    private int mEmployeeID;

    private int current_action;


    public Asset()
      {
    }

    public Asset(JSONObject anAsset){

        this.assetDetail = anAsset;
    }

    public Asset(String mAssetHardwareTypeDescription, String mAssetTag, int mAssetHardwareStatusID) {
        this.mAssetHardwareTypeDescription = mAssetHardwareTypeDescription;
        this.mAssetTag = mAssetTag;
        this.mAssetHardwareStatusID = mAssetHardwareStatusID;
    }

    public Asset(int mFixedAssetID, String mModel, String mSerialNumber, String mAssetTag, int mAssetHardwareTypeID, String mAssetHardwareTypeDescription, int mAssetHardwareStatusID, String mAssetHardwareStatusDescription, String mRemarks, String mAcquisitionDate, String brand) {
        this.mFixedAssetID = mFixedAssetID;
        this.mModel = mModel;
        this.mSerialNumber = mSerialNumber;
        this.mAssetTag = mAssetTag;
        this.mAssetHardwareTypeID = mAssetHardwareTypeID;
        this.mAssetHardwareTypeDescription = mAssetHardwareTypeDescription;
        this.mAssetHardwareStatusID = mAssetHardwareStatusID;
        this.mAssetHardwareStatusDescription = mAssetHardwareStatusDescription;
        this.mRemarks = mRemarks;
        this.mAcquisitionDate = mAcquisitionDate;
        this.mBrand = brand;
    }


    public int getAssignmentID(){
        String value;
        try {

            if ((assetDetail.has(Common.FLD_ASSIGN_ID))) {
                value = assetDetail.getString(Common.FLD_ASSIGN_ID);
            } else {
                value = Integer.toString(mAssignID);
            }
            return Integer.parseInt(value);

        }
        catch (Exception ex) {
            Log.d(Common.LOGNAME,ex.toString());
            return 0;
        }
    }

    public int getEmployeeID(){
        String value;
        try {

            if ((assetDetail.has(Common.FLD_EMP_ID))) {
                value = assetDetail.getString(Common.FLD_EMP_ID);
            } else {
                value = Integer.toString(mEmployeeID);
            }
            return Integer.parseInt(value);
        } catch (JSONException ex) {
            return 0;
        }
    }

    public int getFixedAssetID()
    {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_ASSET_ID))) {
                value = assetDetail.getString(Common.FLD_ASSET_ID);
            } else {
                value = Integer.toString(mFixedAssetID);
            }

            return Integer.parseInt(value);


        } catch (JSONException ex) {
            return 0;
        }

    }

    public void setFixedAssetID(int mFixedAssetID) {
        this.mFixedAssetID = mFixedAssetID;
    }

    public String getModel() {

        String value;
        try {

            if ((assetDetail.has(Common.FLD_MODEL))) {
                value = assetDetail.getString(Common.FLD_MODEL);
            } else {
                value = mModel;
            }

        } catch (JSONException ex) {
            value="";
        }
        return value;
    }

    public void setModel(String mModel) {
        this.mModel = mModel;
    }

    public String getBrand()
    {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_BRAND))) {
                value = assetDetail.getString(Common.FLD_BRAND);
            } else {
                value = mModel;
            }

        } catch (JSONException ex) {
            value="";
        }
        return value;
    }

    public void setBrand(String brand) {
        this.mBrand = brand;
    }

    public String getSerialNumber() {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_SERIAL_NO))) {
                value = assetDetail.getString(Common.FLD_SERIAL_NO);
            } else {
                value = mSerialNumber;
            }

        } catch (JSONException ex) {
            value="";
        }
        return value;
    }

    public void setSerialNumber(String mSerialNumber) {
        this.mSerialNumber = mSerialNumber;
    }

    public String getAssetTag() {
        String value;
        try {

            if ((assetDetail.has("AssetTag"))) {
                value = assetDetail.getString("AssetTag");
            } else {
                value = mAssetTag;
            }

        } catch (JSONException ex) {
            value="";
        }
        return value;

    }

    public void setAssetTag(String mAssetTag) {
        this.mAssetTag = mAssetTag;
    }

    public int getAssetHardwareTypeID() {
        return mAssetHardwareTypeID;
    }

    public void setAssetHardwareTypeID(int mAssetHardwareTypeID) {
        this.mAssetHardwareTypeID = mAssetHardwareTypeID;
    }

    public String getAssetHardwareTypeDescription() {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_ASSET_TYP_DESC))) {
                value = assetDetail.getString(Common.FLD_ASSET_TYP_DESC);
            } else {
                value = mAssetHardwareTypeDescription;
            }

        } catch (JSONException ex) {
            value="";
        }
        return value;

    }

    public String FromID()
    {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_FROM_ID))) {
                value = assetDetail.getString(Common.FLD_FROM_ID);
            } else {
                value = mFromID;
            }

            return value;


        } catch (JSONException ex) {
            return "";
        }

    }

    public void setFromID(String aFromID) {
        this.mFromID = aFromID;
    }

    public String ToID()
    {
        String value;
        try {

            if ((assetDetail.has(Common.FLD_TO_ID))) {
                value = assetDetail.getString(Common.FLD_TO_ID);
            } else {
                value = mToID;
            }

            return value;


        } catch (JSONException ex) {
            return "";
        }

    }

    public void setToID(String aToID) {
        this.mToID = aToID;
    }

    public void setAssetHardwareTypeDescription(String mAssetHardwareTypeDescription) {
        this.mAssetHardwareTypeDescription = mAssetHardwareTypeDescription;
    }

    public int getAssetHardwareStatusID() {
        return mAssetHardwareStatusID;
    }

    public void setAssetHardwareStatusID(int mAssetHardwareStatusID) {
        this.mAssetHardwareStatusID = mAssetHardwareStatusID;
    }

    public String getAssetHardwareStatusDescription() {
        return mAssetHardwareStatusDescription;
    }

    public void setAssetHardwareStatusDescription(String mAssetHardwareStatusDescription) {
        this.mAssetHardwareStatusDescription = mAssetHardwareStatusDescription;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }

    public String getAcquisitionDate() {
        return mAcquisitionDate;
    }

    public void setAcquisitionDate(String mAcquisitionDate) {
        this.mAcquisitionDate = mAcquisitionDate;
    }

    public void assignAsset() {
        //Create a transaction that will be sent to admin custodian for approval
        //The asset will be assigned to the user when approved
    }

    //TODO
    public void approveAsset(Activity anActivity,MyApp anApp){

    }

    //TODO
    public void releaseAsset(Activity anActivity,MyApp anApp) {
        //Create a transaction that will be sent to admin custodian for approval
        //The asset status will be released once approved
        MyApp app = anApp;
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENT_MESSAGE_CAT_POST_RELEASE_ASSET;
        int callType = Request.Method.POST;

        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Common.PARAM_RQSTOR_ID,String.valueOf(app.getSession().getUser().getEmployeeID()));
            params.put(Common.PARAM_ASSIGN_ID,String.valueOf(this.getAssignmentID()));
            params.put(Common.PARAM_ASSET_ID,String.valueOf(this.getFixedAssetID()));
            params.put(Common.PARAM_RQR_APPROVAL,"false");
            //for now default to 5
            params.put(Common.PARAM_REASON_CD,"5");
            params.put(Common.PARAM_REMARKS,"");
            params.put(Common.PARAM_APPROVING_ID,"0");
            params.put(Common.PARAM_ACCEPTING_ID,"0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (params.size() == 0) {
            params = null;
        }

        Log.d(Common.LOGNAME, "Asset Release Params -> " + params.toString());
        request.setParams(params);
        request.registerCallback(this);

        current_action = Common.PROCESS_RELEASE;

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);
    }

    public void transferAssetFromMIS(Activity anActivity,MyApp anApp) {
        MyApp app = anApp;
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENTS_MESSAGE_TRANSFER;
        int callType = Request.Method.POST;

        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Common.PARAM_TOMIS,"false");
            params.put(Common.PARAM_RCPT_ID,Integer.toString(this.getEmployeeID()));
            params.put(Common.PARAM_RQSTOR_ID, String.valueOf(app.getSession().getUser().getEmployeeID()));
            params.put(Common.PARAM_ASSET_ID,Integer.toString(this.getFixedAssetID()));
            params.put(Common.PARAM_RQR_APPROVAL,"false");
            params.put(Common.PARAM_CUR_ASSIGN_ID,Integer.toString(this.getAssignmentID()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (params.size() == 0) {
            params = null;
        }

        Log.d(Common.LOGNAME, "Asset Transfer Params -> " + params.toString());
        request.setParams(params);
        request.registerCallback(this);

        current_action = Common.PROCESS_TRANSFER;

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);
    }

    public void acceptAsset(Activity anActivity,MyApp anApp) {

        MyApp app = anApp;
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENTS_MESSAGE_ACCEPTREJECT;
        int callType = Request.Method.PUT;

        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Common.PARAM_ASSIGN_ID,Integer.toString(this.getAssignmentID()));
            params.put(Common.PARAM_ACCEPTED,"true");
            params.put(Common.PARAM_ACCEPTING_EMP_ID, String.valueOf(app.getSession().getUser().getEmployeeID()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (params.size() == 0) {
            params = null;
        }

        Log.d(Common.LOGNAME, "Asset AcceptReject Params -> " + params.toString());
        request.setParams(params);
        request.registerCallback(this);

        current_action = Common.PROCESS_ACCEPTREJECT;

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);

    }

    public JSONObject getAssetDetail() {
        return assetDetail;
    }

    public void setAssetDetail(JSONObject assetDetail) {
        this.assetDetail = assetDetail;
    }


    @Override
    public void onResponse(String response) {

        switch(current_action) {
            case Common.PROCESS_ACCEPTREJECT:
                Log.d(Common.LOGNAME, "Asset AcceptReject ->" + response);
                if (response.equals("\"SUCCESSFUL\"")) {
                    setChanged();
                    notifyObservers();
                } else {
                    setChanged();
                    notifyObservers(Common.ERROR);
                }
                break;
            case Common.PROCESS_TRANSFER:
                Log.d(Common.LOGNAME, "Asset Transfer ->" + response);
                if (response.equals("\"SUCCESSFUL\"")) {
                    setChanged();
                    notifyObservers();
                } else {
                    setChanged();
                    notifyObservers(Common.ERROR);
                }
                break;
            default:
                Log.d(Common.LOGNAME,"Response -> "+ response);
                setChanged();
                notifyObservers();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        switch(current_action) {
            case Common.PROCESS_ACCEPTREJECT:
                Log.d(Common.LOGNAME, "Asset AcceptReject  Error -> " + error.getMessage());
                break;
            case Common.PROCESS_TRANSFER:
                Log.d(Common.LOGNAME, "Asset Transfer  Error -> " + error.getMessage());
                break;
            case Common.PROCESS_RELEASE:
                Log.d(Common.LOGNAME, "Asset Release  Error -> " + error.getMessage());
                break;
            default:
                Log.d(Common.LOGNAME, "Asset Error -> " + error.getMessage());
                break;

        }

        setChanged();
        notifyObservers(Common.ERROR);
    }
}
