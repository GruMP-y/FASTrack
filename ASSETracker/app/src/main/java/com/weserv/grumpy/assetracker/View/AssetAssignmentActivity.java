package com.weserv.grumpy.assetracker.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.weserv.grumpy.assetracker.Core.Asset;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonObjectRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Employee;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import java.util.HashMap;

import org.json.JSONObject;

public class AssetAssignmentActivity extends BaseActivity {

    Button buttonEmpSearch;
    Button buttonAssetSearch;
    Button buttonAssignAsset;
    EditText empID;
    EditText assetID;
    TextView textEmployeeName;
    TextView textPosition;
    TextView textAssetType;
    TextView textAssetDesc;
    TextView textSerial;

    Employee receipientDetail;
    Asset assetDetail;

    private short loadItem;
    private final short LOADING_NONE = 0;
    private final short LOADING_EMPLOYEE_DETAIL = 2;
    private final short LOADING_ASSETS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_assignment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonEmpSearch = ( Button ) findViewById(R.id.button_assignfrm_search_emp);
        buttonAssetSearch = (Button) findViewById(R.id.button_assignfrm_search_asset);
        buttonAssignAsset = (Button) findViewById(R.id.button_assign_asset);
        empID = ( EditText) findViewById(R.id.text_assignfrm_empID);
        assetID = ( EditText) findViewById(R.id.text_assignfrm_assetID);

        textEmployeeName = ( TextView) findViewById(R.id.label_assignfrm_emp_name);
        textPosition = ( TextView)  findViewById(R.id.label_assignfrm_emp_dept);
        textAssetType = ( TextView) findViewById(R.id.label_assignfrm_asset_type);
        textAssetDesc =(TextView) findViewById(R.id.label_assignfrm_asset_desct);
        textSerial =(TextView)findViewById(R.id.label_assignfrm_asset_serial);

        buttonEmpSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEmployee(empID.getText().toString());
            }
        });

        buttonAssetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempAssetID = assetID.getText().toString();

                if (tempAssetID.length() > 0) {
                    searchAsset(assetID.getText().toString());
                } else {
                    loadScanner();
                }
            }
        });

        buttonAssignAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignAsset();
            }
        });

    }

    private void searchEmployee(String employeeID)
    {
        if ( employeeID.trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Empty Employee ID",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadEmployeeDetail(employeeID);
        }
    }

    public void loadEmployeeDetail(String employeeID) {
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

        loadItem = LOADING_EMPLOYEE_DETAIL;
    }

    private void searchAsset(String assetID)
    {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String aID = assetID;
        String api = MessageConstants.ASSETS_API;
        String message = MessageConstants.ASSETS_MESSAGE_CAT_GET_ASSET_BY_FIX_ASSET_TAG;
        int callType = Request.Method.GET;

        APIJsonObjectRequestFactory request = new APIJsonObjectRequestFactory(ip,api,message,assetID, callType);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

        loadItem = LOADING_ASSETS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asset_assignment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setAssetTag(String assetBarcode) {
        super.setAssetTag(assetBarcode);
        //callback from the ZxingScannerFragment
        detachZxingScaannerFragment();
        searchAsset(assetBarcode);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);

        if (response.equals("\"SUCCESSFUL\"")) {
            this.displayToast("Asset assignment successful.");
        } else {
            this.displayToast("Asset assignment failed.");
        }
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
        this.displayToast("Asset assignment failed.");
    }

    @Override
    public void onJSONResponse(JSONObject response) {
        super.onJSONResponse(response);

        MyApp app = (MyApp) getApplication();
        switch (loadItem) {
            case LOADING_EMPLOYEE_DETAIL:
                receipientDetail = new Employee();
                receipientDetail.setEmployeeDetail(response);

                textEmployeeName.setText(receipientDetail.getLastName() + ", " + receipientDetail.getFirstName());
                textPosition.setText(receipientDetail.getPositionDescription());
                assetID.requestFocus();
                break;
            case LOADING_ASSETS:
                assetDetail = new Asset();
                assetDetail.setAssetDetail(response);
                assetID.setText(assetDetail.getAssetTag());
                textAssetType.setText(assetDetail.getAssetHardwareTypeDescription());
                textAssetDesc.setText(assetDetail.getBrand() + " " + assetDetail.getModel());
                textSerial.setText(assetDetail.getSerialNumber());
                break;
        }
    }

   @Override
    public void onJSONErrorResponse(VolleyError error) {
        super.onJSONErrorResponse(error);
       this.displayToast("Data not found.");
        loadItem = LOADING_NONE;
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

    public void assignAsset()
    {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.ASSIGNMENTS_API;
        String message = MessageConstants.ASSIGNMENTS_MESSAGE_ASSIGN_BY_ASSETTAG;
        int callType = Request.Method.POST;

        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(createRequestParams());
        request.registerCallback(this);
        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);
    }

    public HashMap<String, String> createRequestParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        MyApp app = (MyApp) getApplication();
        try {
            params.put(Common.FLD_RCPT_EMP_ID, Integer.toString(receipientDetail.getEmployeeID()));
            params.put(Common.FLD_ASSET_TAG, assetDetail.getAssetTag());
            params.put(Common.FLD_ASSIGNING_EMP_ID, String.valueOf(app.getSession().getUser().getEmployeeID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (params.size() == 0) {
            params = null;
        }
        return params;
    }


}
