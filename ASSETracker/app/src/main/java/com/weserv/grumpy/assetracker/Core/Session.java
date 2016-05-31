package com.weserv.grumpy.assetracker.Core;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.weserv.grumpy.assetracker.Core.Asset;
import com.weserv.grumpy.assetracker.Core.Employee;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Vans on 9/27/2015.
 */
public class Session {
    private String employeeID;

    private AccessRight accessRight;
    private Employee user;
    private Assets assetList;
    private AssetTypes assetTypes;
    private MISTransactions misTransactions;
    private MgrTransactions mgrTransactions;

    private boolean loginStatus;

    public Session() {
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public Employee getUser() {
        return user;
    }

    public Assets getAssets() {
        return assetList;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public void setAssetList(Assets assetList) {
        this.assetList = assetList;
    }

    public void setAssetTypes(AssetTypes anAssetTypes){
        this.assetTypes = anAssetTypes;
    }

    public AssetTypes getAssetTypes(){
        return assetTypes;
    }

    public void startSession(String empID) {
        loginStatus = true;
        employeeID = empID;
    }

    public void endSession() {
        //clear session values
        loginStatus = false;
        employeeID = "";
        user = null;
        assetList = null;
        misTransactions = null;
    }

    public boolean isLoggedIn() {
        return loginStatus;
    }

    public void logout() {
        loginStatus = false;
        endSession();
    }

    public void setMisTransactions(MISTransactions anMISTransactions){
        this.misTransactions = anMISTransactions;
    }

    public void setMgrTransactions(MgrTransactions anMgrTransactions){
        this.mgrTransactions = anMgrTransactions;
    }

    public MISTransactions getMisTransactions(){
        return misTransactions;
    }

    public MgrTransactions getMgrTransactions() {
        return mgrTransactions;
    }



}
