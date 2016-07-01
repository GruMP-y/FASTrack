package com.weserv.grumpy.assetracker.View;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

public class GetAssetTagActivity extends BaseActivity {

    Button acceptButton;
    android.support.design.widget.FloatingActionButton scanButton;
    EditText textAssetTag;

    private int current_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_asset_tag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        acceptButton = (Button) findViewById(R.id.get_asset_accept);
        scanButton = (android.support.design.widget.FloatingActionButton) findViewById(R.id.get_asset_scan);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textAssetTag = (EditText) findViewById(R.id.get_asset_asset_tag);

                if ( textAssetTag.getText().toString().length() > 0) {
                    String message = textAssetTag.getText().toString().toUpperCase();
                    Intent intent = new Intent();
                    intent.putExtra(Common.DATA, message);
                    setResult(Common.RSPNS_CD_GET_ASSET_TAG, intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Missing Field",Toast.LENGTH_LONG).show();
                }
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadScanner();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_asset_tag, menu);
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
    public void setAssetTag(String assetBarcode) {
        super.setAssetTag(assetBarcode);

        String message = assetBarcode.toUpperCase();
        Intent intent = new Intent();
        intent.putExtra(Common.DATA, message);
        setResult(Common.RSPNS_CD_GET_ASSET_TAG, intent);
        finish();
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

}
