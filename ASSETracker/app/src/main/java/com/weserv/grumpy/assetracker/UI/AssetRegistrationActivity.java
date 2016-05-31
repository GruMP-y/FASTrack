package com.weserv.grumpy.assetracker.UI;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.weserv.grumpy.assetracker.Core.AssetType;
import com.weserv.grumpy.assetracker.Core.AssetTypes;
import com.weserv.grumpy.assetracker.Core.Connection.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.AccountMgmtUtils;
import com.weserv.grumpy.assetracker.Utils.Common;

import java.util.Date;
import java.util.HashMap;

public class AssetRegistrationActivity extends AppCompatActivity
        implements RequestResponseCallback, BarcodeScannerListener {

    Button assetRegister;
    Button buttonScan;
    Spinner spinAssetType;
    EditText textModel;
    EditText textSerial;
    EditText textAssetTag;
    EditText textBrand;

    ProgressDialog myProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final MyApp app = (MyApp) getApplication();

        myProgress = new ProgressDialog(this);
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(false);

        assetRegister = (Button) findViewById(R.id.button_register_asset);
        buttonScan = (Button) findViewById(R.id.button_reg_scan);
        spinAssetType = (Spinner) findViewById(R.id.spinner_reg_assettype);

        textModel = (EditText) findViewById(R.id.text_asset_reg_model);
        textSerial = (EditText) findViewById(R.id.text_asset_reg_serial);
        textAssetTag = (EditText) findViewById(R.id.text_asset_reg_asset_tag);
        textBrand = (EditText) findViewById(R.id.text_asset_reg_brand);

        assetRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndRegister();
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call scanner here....
                loadScanner();
            }
        });

        /*spinAssetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int iselected = spinAssetType.getSelectedItemPosition();
                String st = Integer.toString(app.getSession().getAssetTypes().getAssetTypeID(iselected));

                Toast.makeText(AssetRegistrationActivity.this,st,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        buttonScan.setTransformationMethod(null);

        //set the spinner data
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,app.getSession().getAssetTypes().getAssetTypeDescriptions());
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAssetType.setAdapter(typesAdapter);

    }

    void loadScanner() {
        attachZxingScaannerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment barcodeScannerFragment = fragmentManager.findFragmentByTag("scanner");
        IntentIntegrator intentIntegrator = new IntentIntegrator(barcodeScannerFragment);
        intentIntegrator.initiateScan();
    }

    void attachZxingScaannerFragment() {
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = ZxingScannerFragment.class;

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(fragment, "scanner").commit();
        fragmentManager.executePendingTransactions();

    }

    void detachZxingScaannerFragment() {
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = ZxingScannerFragment.class;

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        fragmentManager.executePendingTransactions();
    }

    //callback from the ZxingScannerFragment
    public void setAssetTag(String assetBarcode) {
        detachZxingScaannerFragment();
        EditText textAssetTag = (EditText) findViewById(R.id.text_asset_reg_asset_tag);
        textAssetTag.setText(assetBarcode);
        textSerial.requestFocus();
    }

    public void validateAndRegister() {

        MyApp app = (MyApp) getApplication();
        String sModel = textModel.getText().toString();
        String sSerial = textSerial.getText().toString();
        String sAssetTag = textAssetTag.getText().toString();
        String sBrand = textBrand.getText().toString();
        //For now lets make it simple
        //int spinnerSelected =
        String sAssetTypeID = Integer.toString(app.getSession().getAssetTypes().getAssetTypeID(spinAssetType.getSelectedItemPosition()));
        String sAssetClass= "2";
        String sAssetStatus = "2";

        if (validate(sModel, sSerial, sAssetTag, sBrand, sAssetTypeID)) {
            //create hashmap for param

            String empID = String.valueOf(app.getSession().getUser().getEmployeeID());

            HashMap<String, String> params = new HashMap<String, String>();
            params.put(Common.FLD_MODEL, sModel);
            params.put(Common.FLD_SERIAL_NO, sSerial);
            params.put(Common.FLD_ASSET_TAG, sAssetTag);
            params.put(Common.FLD_BRAND, sBrand);
            params.put(Common.FLD_EMP_ID, empID);
            params.put(Common.FLD_ASSET_TYP_ID, sAssetTypeID);
            params.put(Common.FLD_CLS_ID, sAssetClass);

            Log.d(Common.LOGNAME,"Asset Registration : sendrequest");

            sendAssetRegistrationRequest(params);
        } else {
            Log.d(Common.LOGNAME,"Asset Registration : Validation failed!");
            Toast.makeText(getApplicationContext(), "Please fill all the required fields!", Toast.LENGTH_LONG).show();
        }

    }

    public boolean validate(String model, String serial, String assetTag, String brand,  String assetTypeID) {
        boolean value = false;

        if ((AccountMgmtUtils.isNotNull(model)) &&
                (AccountMgmtUtils.isNotNull(serial)) &&
                (AccountMgmtUtils.isNotNull(assetTag)) &&
                (AccountMgmtUtils.isNotNull(brand)) &&
                (AccountMgmtUtils.isNotNull(assetTypeID)))
        {
            value = true;
        }

        return value;
    }

    public void sendAssetRegistrationRequest(HashMap<String, String> params) {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSETS_API;
        String message = MessageConstants.ASSETS_MESSAGE_CAT_ADD_A_NEW_ASSET;
        int callType = Request.Method.POST;


        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(params);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);
    }

    @Override
    public void onResponse(String response) {
        Log.d(Common.LOGNAME,"Asset Registration RESPONSE ->" + response);
        if (response.equals("\"SUCCESSFUL\"")) {
            Log.d(Common.LOGNAME,"Asset Registration Successful!");
            Toast.makeText(getApplicationContext(), "Asset Registration Successful!!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Log.d(Common.LOGNAME,"Asset Registration Failed!");
            Toast.makeText(getApplicationContext(), "Asset Registration Failed!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"Asset Registration  Error!");
        Toast.makeText(getApplicationContext(), "Asset Registration Failed!!", Toast.LENGTH_LONG).show();
    }
}
