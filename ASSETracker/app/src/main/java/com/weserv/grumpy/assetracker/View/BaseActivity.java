package com.weserv.grumpy.assetracker.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.RESTHelper.JSONArrayResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.RequestResponseCallback;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Trashvin on 6/27/16.
 */
public class BaseActivity extends AppCompatActivity
    implements Observer,IBarcodeScannerListener, RequestResponseCallback, JSONObjectResponseCallback, JSONArrayResponseCallback {

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

    }

    protected void showProgressDialog(){
        if ( !progressDialog.isShowing()){
            Log.d(Common.LOGNAME,"Progress triggered");
            progressDialog.show();
        }
    }

    protected void hideProgressDialog(){
        if ( progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    protected void displayToast(String aMessage){
        try{
            Toast.makeText(this.getApplicationContext(),aMessage,Toast.LENGTH_LONG).show();
            Log.d(Common.LOGNAME, aMessage);
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME, ex.toString());
        }
    }

    @Override
    public void onResponse(String response) {
        Log.d(Common.LOGNAME,"OnResponse received.");
        hideProgressDialog();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"OnErrorResponse received with the following error ->" + error.getMessage());
        hideProgressDialog();
    }

    @Override
    public void setAssetTag(String assetBarcode) {
        Log.d(Common.LOGNAME,"AssetTag received from Barcode ->" + assetBarcode);
        hideProgressDialog();
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
        Log.d(Common.LOGNAME,"OnJSONArrayResponse received.");
        hideProgressDialog();
    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"OnJSONArrayErrorResponse received with the following error ->"+ error.getMessage());
        hideProgressDialog();
    }

    @Override
    public void onJSONResponse(JSONObject response) {
        Log.d(Common.LOGNAME,"OnJSONResponse received.");
        hideProgressDialog();
    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"OnJSONErrorResponse received with the following error ->"+ error.getMessage());
        hideProgressDialog();
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d(Common.LOGNAME,"Update");
        hideProgressDialog();
    }
}
