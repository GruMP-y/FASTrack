package com.weserv.grumpy.assetracker.View;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonObjectRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.RESTHelper.RequestResponseCallback;
import com.weserv.grumpy.assetracker.Core.Employee;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TransferAssetActivity extends AppCompatActivity
        implements IBarcodeScannerListener, RequestResponseCallback, JSONObjectResponseCallback {

    private Boolean hasAssetDataLoaded;
    private Boolean hasEmployeeDataLoaded;
    private Bundle dataFromParent;

    Button buttonSearchEmployee;
    Button buttonSearchAsset;
    Button buttonTransfer;
    EditText textEmployeeID;
    EditText textAssetID;
    TextView labelAssetType;
    TextView labelAssetSerial;
    TextView labelAssetDesc;
    TextView labelEmpName;
    TextView labelDeptName;
    Spinner spinReasonCode;
    MenuItem clearMenuItem;



    private int current_action;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transfer_asset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        switch(menu.getItemId()){
            case R.id.clear_transfer:
                textEmployeeID.setText("");
                textAssetID.setText("");
                labelAssetDesc.setText("");
                labelAssetSerial.setText("");
                labelAssetType.setText("");
                labelDeptName.setText("");
                labelEmpName.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_asset);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttonSearchEmployee = (Button) findViewById(R.id.button_transfer_frm_search_emp);
        buttonSearchAsset = (Button) findViewById(R.id.button_transfer_frm_search_asset);
        buttonTransfer = ( Button) findViewById(R.id.button_transfer);

        textEmployeeID = (EditText)findViewById(R.id.text_recipient_id);
        textAssetID= ( EditText) findViewById(R.id.text_asset_tag_search);

        labelAssetSerial = ( TextView) findViewById(R.id.label_asset_info_serial);
        labelAssetType = (TextView) findViewById(R.id.label_asset_info_type);
        labelAssetDesc = (TextView) findViewById(R.id.label_asset_info_model);

        labelEmpName = (TextView) findViewById(R.id.label_transfer_frm_emp_name);
        labelDeptName = (TextView) findViewById(R.id.label_trasnfer_frm_emp_dept);

        clearMenuItem = (MenuItem) findViewById(R.id.clear_transfer);

        spinReasonCode = (Spinner) findViewById(R.id.spinner_transfer_reasons);

        setOnClickLister();

        hasAssetDataLoaded = false;
        hasEmployeeDataLoaded = false;

        Intent intent = getIntent();
        dataFromParent = intent.getBundleExtra(MainScreenActivity.EXTRA_MESSAGE);

        if ( dataFromParent != null) {

            int src =dataFromParent.getInt(Common.PARAM_BNDL_SRC);
            if ( src == Common.SRC_MYASSSETS) {
                current_action = Common.PROCESS_TRANSFER_ACTIVE;
            }
        }
        else{
            current_action = Common.PROCESS_LOADING_NONE;
        }


    }

    @Override
    public void onStart(){
        super.onStart();

        if(current_action == Common.PROCESS_EMPTY){
            Toast.makeText(getApplicationContext(),"Scanning aborted. Key-in asset tag.", Toast.LENGTH_SHORT).show();
            textAssetID.requestFocus();
            current_action= Common.PROCESS_LOADING_NONE;
        }

        if(current_action == Common.PROCESS_TRANSFER_ACTIVE){
            loadAssetToTransfer(dataFromParent.getString(Common.PARAM_BNDL_DATA_01));
        }

    }

    private void loadAssetToTransfer(String anAssetTag){
        textAssetID.setText(anAssetTag);
        textAssetID.setEnabled(false);
        manualSearch(textAssetID.getText().toString().toUpperCase().trim());
    }


    void setOnClickLister() {

        buttonSearchEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Common.LOGNAME, "Transfer : Search Employee");

                if (textEmployeeID.getText().toString().trim().length() > 0) {
                    Log.i(Common.LOGNAME, "Transfer : Do search for employee");
                    searchEmployeeDetail(textEmployeeID.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_empty_search_key), Toast.LENGTH_LONG).show();
                    textEmployeeID.requestFocus();
                }

            }
        });

        buttonSearchAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(Common.LOGNAME, "Transfer : Search Asset");

                //hide the keyboard
                try
                {
                    InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    input.hideSoftInputFromWindow(buttonSearchAsset.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
                catch(Exception ex)
                {
                }

                if (textAssetID.getText().toString().trim().length() > 0) {
                    Log.i(Common.LOGNAME, "Transfer : Do manual search");
                    //do manual search
                    manualSearch(textAssetID.getText().toString().trim());
                } else {
                    Log.i(Common.LOGNAME, "Transfer : Launch scanner");
                    current_action = Common.PROCESS_EMPTY;
                    loadScanner();

                }
            }
        });

        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Common.LOGNAME,"Transfer : Inititate transfer");
                validateAndTransfer();
            }
        });


    }

    public void searchEmployeeDetail(String employeeID) {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String empID = employeeID;
        String api = MessageConstants.EMPLOYEES_API;
        String message = MessageConstants.EMPLOYEES_MESSAGE_CAT_GET_EMPLOYEE_BY_EMPLOYEE_ID;
        int callType = Request.Method.GET;

        APIJsonObjectRequestFactory request = new APIJsonObjectRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

        current_action = Common.PROCESS_LOADING_EMPDETAIL;
    }

    void manualSearch(String searchTag) {
        Log.i(Common.LOGNAME, "Transfer : Search asset result.");

        JSONObject asset;
        MyApp app = (MyApp) getApplication();

        asset = app.getSession().getAssets().getMyAssetByAssetTag(searchTag);

        if (asset != null) {
            Log.i(Common.LOGNAME,"Transfer : Data found.");

            loadAssetDetailToViews(asset);
        } else {
            Log.d(Common.LOGNAME, "Transfer : Data not found.");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_not_found), Toast.LENGTH_LONG).show();
        }
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
        textAssetID.setText(assetBarcode);
        manualSearch(assetBarcode);
    }

    void loadAssetDetailToViews(JSONObject asset) {
        if (asset != null) {
            Log.d(Common.LOGNAME,"Transfer : Loaded data -> " + asset.toString());
            populateAssetInfoViews(asset);
            buttonTransfer.requestFocus();
        }
    }

    void populateAssetInfoViews(JSONObject info) {
        try
        {
            labelAssetType.setText(info.getString("TypeDescription"));
            labelAssetSerial.setText(info.getString("SerialNumber"));
            labelAssetDesc.setText(info.getString("Brand") + " " + info.getString("Model"));
            hasAssetDataLoaded = true;
            buttonTransfer.requestFocus();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    void validateAndTransfer() {
        MyApp app = (MyApp) getApplication();
        JSONObject asset;

        if (hasEmployeeDataLoaded && hasAssetDataLoaded)
        {
            Log.i(Common.LOGNAME,"Transfer : Send transfer request to FASTWS.");
            asset = app.getSession().getAssets().getMyAssetByAssetTag(textAssetID.getText().toString());
            sendTransferRequest(createRequestParams(asset));
        }
        else
        {
            Log.d(Common.LOGNAME, "Transfer : Missing data, transfer request aborted");
            Toast.makeText(getApplication(),getResources().getString(R.string.error_request_aborted),Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void sendTransferRequest(HashMap<String, String> params) {

        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENT_MESSAGE_CAT_POST_TRANSFER_ASSET;
        int callType = Request.Method.POST;

        Log.d(Common.LOGNAME,"Send Transfer Request.");
        Log.d(Common.LOGNAME,params.toString());
        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(params);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

        current_action = Common.PROCESS_TRANSFER;

    }

    public HashMap<String, String> createRequestParams(JSONObject asset) {
        HashMap<String, String> params = new HashMap<String, String>();
        MyApp app = (MyApp) getApplication();
        EditText recipient = (EditText) findViewById(R.id.text_recipient_id);
        int newAssignmentID = 0;
        try {
            params.put("ToMIS", "false");
            params.put("RequestorID", String.valueOf(app.getSession().getUser().getEmployeeID()));
            params.put("ReceipientID", textEmployeeID.getText().toString());
            params.put("CurrentAssignmentID", asset.getString("AssetAssignmentID"));
            params.put("FixAssetID", asset.getString("FixAssetID"));
            params.put("RequireApproval", "true");
            params.put("OptionalRemarks", spinReasonCode.getSelectedItem().toString());
            if (asset.isNull("ManagerID")) {
                params.put("ApprovingID", String.valueOf(0));
            } else {
                params.put("ApprovingID", asset.getString("ManagerID"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (params.size() == 0) {
            params = null;
        }
        return params;
    }

    @Override
    public void onResponse(String response) {
        Log.i(Common.LOGNAME, "Transfer : Response -> " + response.toString());

        if (response.equals("\"SUCCESSFUL\"")) {
            Toast.makeText(getApplicationContext(), "Transfer Successful!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Transfer Failed!", Toast.LENGTH_LONG).show();
        }

        Log.d(Common.LOGNAME,"Finishing...");
        finish();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"Transfer : Error -> "+error.getMessage());
        Toast.makeText(getApplicationContext(), "Transfer Error!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onJSONResponse(JSONObject response) {
        Log.i(Common.LOGNAME,"Transfer : JSON Response received.");
        Log.d(Common.LOGNAME,"Transfer : Response -> " + response.toString());

        MyApp app = (MyApp) getApplication();
        switch (current_action) {
            case Common.PROCESS_LOADING_EMPDETAIL:
                Employee receipientDetail = new Employee();
                receipientDetail.setEmployeeDetail(response);

                labelEmpName.setText(receipientDetail.getLastName() + ", " + receipientDetail.getFirstName());
                labelDeptName.setText(receipientDetail.getGroupName());

                hasEmployeeDataLoaded = true;

                textAssetID.requestFocus();
                break;
        }
    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME, "Transfer : JSON error received -> " + error.getMessage());
        Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_LONG).show();
        current_action = Common.PROCESS_LOADING_NONE;
    }

}
