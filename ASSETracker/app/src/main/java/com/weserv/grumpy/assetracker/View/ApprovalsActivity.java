package com.weserv.grumpy.assetracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weserv.grumpy.assetracker.Core.Asset;
import com.weserv.grumpy.assetracker.Core.Employee;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class ApprovalsActivity extends BaseActivity {

    private Bundle dataFromParent;

    private Button approveButton;
    private Button denyButton;
    private TextView transactionType;
    private TextView assetTag;
    private TextView serialNumber;
    private TextView assetType;
    private TextView brandModel;
    private TextView requestorName;
    private TextView requestorDept;
    private TextView requestorEmail;
    private TextView receipientName;
    private TextView receipientDept;
    private TextView receipientEmail;
    private TextView reasonCode;
    private CardView receipientCard;

    private Employee requestor;
    private Employee recipient;
    private JSONObject asset;

    private boolean isTransactionTransfer;

    private int loadedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadedData = 0;
        isTransactionTransfer = true;

        Intent intent = getIntent();
        dataFromParent = intent.getBundleExtra(MainScreenActivity.EXTRA_MESSAGE);

        approveButton = (Button) findViewById(R.id.button_approve);
        denyButton = ( Button) findViewById(R.id.button_deny);
        assetTag = (TextView) findViewById(R.id.label_approval_asset_tag);
        serialNumber = (TextView) findViewById(R.id.label_approval_asset_serial_no);
        assetType = (TextView) findViewById(R.id.label_approval_asset_asset_type);
        brandModel = (TextView) findViewById(R.id.label_approval_asset_brandmodel);

        requestorName = (TextView) findViewById(R.id.label_approval_requestor_name);
        requestorDept = (TextView) findViewById(R.id.label_approval_requestor_dept);
        requestorEmail = (TextView) findViewById(R.id.label_approval_requestor_email);

        receipientName = (TextView) findViewById(R.id.label_approval_receipient_name);
        receipientDept = (TextView) findViewById(R.id.label_approval_receipient_dept);
        receipientEmail = (TextView) findViewById(R.id.label_approval_receipient_email);

        reasonCode = (TextView) findViewById(R.id.label_approval_reason);
        receipientCard= (CardView) findViewById(R.id.cv_approval_receipient);
        transactionType = (TextView) findViewById(R.id.label_approval_request_type);

        isTransactionTransfer = determineTransactionType();

        if ( !isTransactionTransfer ){
            receipientCard.removeAllViews();
            receipientCard.setVisibility(View.INVISIBLE);
        }

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveTransaction();
            }
        });

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyTransaction();
            }
        });


        loadEmployeeData();

    }

    private boolean determineTransactionType(){
        String status = dataFromParent.getString(Common.PARAM_BNDL_DATA_02);

        if ( status.equalsIgnoreCase(Common.ASSET_STATUS_FORTRANSFER)){
            Log.d(Common.LOGNAME,"Transfer Transaction");
            return true;
        }
        else{
            Log.d(Common.LOGNAME,"Release Transaction");
            return false;
        }
    }

    private void loadEmployeeData(){
        String anAssetTag = dataFromParent.getString(Common.PARAM_BNDL_DATA_01);
        Log.d(Common.LOGNAME,"Approvals : AssetTag -> " + anAssetTag);

        MyApp app = (MyApp) getApplication();

        asset = app.getSession().getMgrTransactions().getMGRTransAssetTag(anAssetTag);

        Log.d(Common.LOGNAME, asset.toString());
        try {

            //get requestor
            int requestorID = Integer.parseInt(asset.getString(Common.FLD_FROM_ID));
            requestor = new Employee();
            requestor.addObserver(this);
            requestor.getEmployeeDetailFromWS(this,(MyApp)getApplication(),requestorID);
            loadedData ++;

            //get recipient if needed
            if (isTransactionTransfer) {
                int recipientID = Integer.parseInt(asset.getString(Common.FLD_TO_ID));
                recipient = new Employee();
                recipient.addObserver(this);
                recipient.getEmployeeDetailFromWS(this, (MyApp) getApplication(), recipientID);
                loadedData++;
            }

            Log.d(Common.LOGNAME,"loadedData = " + Integer.toString(loadedData));
        }
        catch(Exception ex){
            Log.d( Common.LOGNAME, ex.toString());
        }
    }

    private void loadDataToUI(){

        try {
            transactionType.setText(asset.getString(Common.FLD_ASSET_STAT_DESC));
            reasonCode.setText(asset.getString(Common.FLD_ASSIGN_REMARKS));
            assetTag.setText(asset.getString(Common.FLD_ASSET_TAG));
            serialNumber.setText(asset.getString(Common.FLD_SERIAL_NO));
            assetType.setText(asset.getString(Common.FLD_ASSET_TYP_DESC));
            brandModel.setText(asset.getString(Common.FLD_BRAND) + " " + asset.getString(Common.FLD_MODEL));

            requestorName.setText(requestor.getLastName() + ", " + requestor.getFirstName());
            requestorDept.setText(requestor.getDepartmentDescription());
            requestorEmail.setText(requestor.getEmailAddress());

            //load recipient data if needed
            if ( isTransactionTransfer) {
                receipientName.setText(recipient.getLastName() + ", " + recipient.getFirstName());
                receipientDept.setText(recipient.getDepartmentDescription());
                receipientEmail.setText(recipient.getEmailAddress());
            }
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,ex.toString());
        }

    }

    private void approveTransaction(){

        Asset toApprove = new Asset();
        toApprove.addObserver(this);

        toApprove.setAssetDetail(asset);

        if ( isTransactionTransfer ){
             //Verify if asset, recipient and requestor are available
            if ( assetTag.getText().length() > 0 &&
                    receipientName.getText().length()> 0 &&
                        requestorName.getText().length()> 0){
                //continue with approval
                toApprove.approveAsset(this,(MyApp) getApplication(), true);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Missing Information. Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else{
            //Verify if asset, recipient and requestor are available
            if ( assetTag.getText().length() > 0 &&
                    requestorName.getText().length()> 0){
                //continue with approval
                //toApprove.approveAsset(this,(MyApp) getApplication(), true);
                Toast.makeText(getApplicationContext(),"Function Not Supported.", Toast.LENGTH_SHORT).show();
                //finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Missing Information. Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }




    }

    private void denyTransaction(){

    }

    @Override
    public void update(Observable observable, Object o) {
        super.update(observable,o);

        if ( observable instanceof Employee) {
            if ( o == null ) {
                Log.d(Common.LOGNAME, "Loading of employee data successful");
            }
            else{
                Log.d(Common.LOGNAME,"Error loading data employee.");
            }
            loadedData --;

            if ( loadedData == 0){
                loadDataToUI();
            }
        }

        if ( observable instanceof  Asset){
            if ( o == null ) {
                Log.d(Common.LOGNAME, "Approve/deny successful");
                Toast.makeText(getApplicationContext(),"Operation Successful!", Toast.LENGTH_SHORT).show();

            }
            else{
                Log.d(Common.LOGNAME,"Error in approve/deny.");
                Toast.makeText(getApplicationContext(),"Operation Not Successful!", Toast.LENGTH_SHORT).show();

            }

        }


    }

}
