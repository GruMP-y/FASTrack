package com.weserv.grumpy.assetracker.View;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.weserv.grumpy.assetracker.Utils.Common;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Trashvin on 6/27/16.
 */
public class BaseFragment extends Fragment
                            implements Observer{

    protected ProgressDialog progressDialog;
    protected Context currentContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(currentContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onAttach(Context aContext){
        super.onAttach(aContext);
        currentContext = aContext;
    }

    protected void showProgressDialog(){
        if ( !progressDialog.isShowing()){
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
            Toast.makeText(currentContext,aMessage,Toast.LENGTH_LONG).show();
            Log.d(Common.LOGNAME, aMessage);
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME, ex.toString());
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        if ( progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }





}
