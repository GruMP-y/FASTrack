package com.weserv.grumpy.assetracker.Core;

import android.util.Log;

import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vans on 3/28/2016.
 */
public class AssetItem {
    String assetType;
    String serialNumber;
    String assetTag;
    String assignmentStatusDesc;
    String assignmentStatus;
    String assetBrandModel;
    String fromID;
    String toID;
    int assignmentID;
    String assetStatus;
    String assetStatusDesc;

    public AssetItem(String assetType, String serialNumber, String anAssetTag, String assignmentStatusDesc, String anAssignmentStatus) {
        this.assetType = assetType;
        this.serialNumber = serialNumber;
        this.assetTag = anAssetTag;
        this.assignmentStatusDesc = assignmentStatusDesc;
        this.assignmentStatus =anAssignmentStatus;
    }

    public AssetItem(JSONObject aJsonAsset){
        try {
            this.assetType = aJsonAsset.getString(Common.FLD_ASSET_TYP_DESC);
            this.assetTag = aJsonAsset.getString(Common.FLD_ASSET_TAG);
            this.assignmentStatus = aJsonAsset.getString(Common.FLD_ASSIGN_STAT_ID);
            this.serialNumber = aJsonAsset.getString(Common.FLD_SERIAL_NO);
            this.assignmentID = aJsonAsset.getInt(Common.FLD_ASSIGN_ID);
            this.assetStatus = aJsonAsset.getString(Common.FLD_ASSET_STAT_ID);
            this.assetStatusDesc = aJsonAsset.getString(Common.FLD_ASSET_STAT_DESC);

            String modelBrand = aJsonAsset.getString(Common.FLD_BRAND) + " " + aJsonAsset.getString(Common.FLD_MODEL);

            this.assetBrandModel = modelBrand;
        }
        catch(JSONException ex){
            Log.d(Common.LOGNAME, ex.toString());
        }

    }

    public String getAssetType() {

        return assetType;
    }

    public void setAssetType(String assetType) {

        this.assetType = assetType;
    }

    public String getSerialNumber() {

        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {

        this.serialNumber = serialNumber;
    }

    public void setAssetTag(String anAssetTag) {
        this.assetTag = anAssetTag; }

    public String getAssetTag() {
        return assetTag;}

    public String getAssignmentStatusDesc() {

        return assignmentStatusDesc;
    }

    public void setAssignmentStatusDesc(String assignmentStatusDesc) {
        this.assignmentStatusDesc = assignmentStatusDesc;
    }

    public String getAssignmentStatus() {

        return assignmentStatus;
    }

    public void setAssignmentStatus(String anAssignmentStatus){
        this.assignmentStatus = anAssignmentStatus;
    }

    public String getAssetBrandModel() {

        return assetBrandModel;
    }

    public void setAssetBrandModel(String aModelBrand){

        this.assetBrandModel = aModelBrand;
    }

    public int getAssignmentID() {
        return assignmentID;}

    public String getAssetStatus(){
        return assetStatus;
    }

    public String getAssetStatusDescription(){
        return assetStatusDesc;
    }

}
