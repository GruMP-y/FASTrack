package com.weserv.grumpy.assetracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weserv.grumpy.assetracker.Core.Asset;
import com.weserv.grumpy.assetracker.Core.Assets;
import com.weserv.grumpy.assetracker.Core.MISTransactions;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONObject;
import java.util.Observable;
import java.util.Observer;

public class ManageAssetActivity extends BaseActivity{

    TextView textAssignID;
    TextView textEmpID;
    TextView textAssettype;
    TextView textModelBrand;
    TextView textSerialNo;
    TextView textAssetTag;

    Asset theAsset;
    Bundle dataFromParent;

    Button transferButton;
    Button releaseButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_asset);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textAssignID = (TextView) findViewById(R.id.label_manage_assign_assign_id);
        textEmpID = (TextView) findViewById(R.id.label_manage_assign_emp_id);
        textAssettype = (TextView) findViewById(R.id.label_manage_assign_asset_type);
        textModelBrand= (TextView) findViewById(R.id.label_manage_assign_brand_model);
        textSerialNo = ( TextView) findViewById(R.id.label_manage_assign_serialno);
        textAssetTag =(TextView) findViewById(R.id.label_manage_assign_asset_tag);

        transferButton =(Button) findViewById(R.id.button_manage_assign_transfer);
        releaseButton =(Button) findViewById(R.id.button_manage_assign_release);

        Intent intent = getIntent();
        dataFromParent = intent.getBundleExtra(MainScreenActivity.EXTRA_MESSAGE);

        performRequestActions();

        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int source = dataFromParent.getInt(Common.PARAM_BNDL_SRC);
                //Log.d(Common.LOGNAME,Integer.toString(source));
                misReleaseAsset();
                /*switch(source){
                    case Common.SRC_MISASSETS:
                        misReleaseAsset();
                        break;
                    case Common.SRC_MYASSSETS:
                        //employeeReleaseAsset();
                        misReleaseAsset();
                        break;
                }*/



            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int source = dataFromParent.getInt(Common.PARAM_BNDL_SRC);

                switch(source){
                    case Common.SRC_MISASSETS:
                        transferToEmployee();
                        break;
                    case Common.SRC_MYASSSETS:
                        transferAssetToAnotherEmployee();
                        break;
                }

            }
        });

    }

    private void performRequestActions(){
        int source = dataFromParent.getInt(Common.PARAM_BNDL_SRC);

        switch(source){
            case Common.SRC_MISASSETS:
                releaseButton.setText(getString(R.string.button_label_release));
                transferButton.setText(getString(R.string.button_label_release_to_emp));
                loadAssignment(Common.SRC_MISASSETS);
                break;
            case Common.SRC_MYASSSETS:
                releaseButton.setText(getString(R.string.button_label_release));
                transferButton.setText(getString(R.string.button_label_transfer));
                loadAssignment(Common.SRC_MYASSSETS);
                break;
        }
    }

    private void loadAssignment(int source){
        MyApp app = (MyApp) getApplication();

        MISTransactions misAssignments;
        Assets myAssets;
        JSONObject theJSONAsset = new JSONObject();

        String anAssetTag = dataFromParent.getString(Common.PARAM_BNDL_DATA_01);

        switch(source){
            case Common.SRC_MYASSSETS:
                myAssets = app.getSession().getAssets();
                theJSONAsset = myAssets.getMyAssetByAssetTag(anAssetTag);
                break;
            case Common.SRC_MISASSETS:
                misAssignments = app.getSession().getMisTransactions();
                theJSONAsset = misAssignments.getMyAssetByAssetTag(anAssetTag);
                break;
        }



        if ( theJSONAsset != null){
            theAsset = new Asset(theJSONAsset);
            theAsset.addObserver(this);
            textAssetTag.setText(theAsset.getAssetTag());
            textAssettype.setText(theAsset.getAssetHardwareTypeDescription());
            textModelBrand.setText(theAsset.getBrand() + " " + theAsset.getModel());
            textAssignID.setText(Integer.toString(theAsset.getAssignmentID()));
            textEmpID.setText(Integer.toString(theAsset.getEmployeeID()));
            textSerialNo.setText(theAsset.getSerialNumber());
        }
        else{
            Toast.makeText(getApplicationContext(),"Asset Not Found",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void misReleaseAsset(){
        Log.d(Common.LOGNAME,"Releasing..");
        if( theAsset != null){
            theAsset.releaseAsset(this,(MyApp) getApplication());
        }
    }

    private void employeeReleaseAsset(){
        if ( theAsset != null){

        }
    }



    private void transferToEmployee(){
        if( theAsset != null){
            theAsset.transferAssetFromMIS(this,(MyApp) getApplication());
        }
    }

    private void transferAssetToAnotherEmployee(){
        Bundle params = new Bundle();

        params.putInt(Common.PARAM_BNDL_SRC,Common.SRC_MYASSSETS);
        params.putString(Common.PARAM_BNDL_DATA_01, textAssetTag.getText().toString());
        params.putString(Common.PARAM_BNDL_DATA_02,"TRANSFER");

        Intent intent = new Intent(this,TransferAssetActivity.class);
        intent.putExtra(MainScreenActivity.EXTRA_MESSAGE,params);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_asset, menu);
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

    @Override
    public void update(Observable observable, Object o) {
        if ( observable instanceof Asset) {
            if ( o == null ) {
                Log.d(Common.LOGNAME, "Asset Operation Successful");
                Toast.makeText(this,"Asset Operation Successful",Toast.LENGTH_LONG).show();
            }
            else{
                Log.d(Common.LOGNAME,"Asset Operation Failed.");
                Toast.makeText(this,"Asset Operation Failed",Toast.LENGTH_LONG).show();
            }
        }

        finish();
    }
}
