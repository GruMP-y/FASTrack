package com.weserv.grumpy.assetracker.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.Common;

/**
 * Created by baylefra on 3/18/2016.
 */
public class Preference  extends AppCompatActivity {
    Button buttonClose, buttonEditFASTWS;
    TextView textFASTWS;
    public static final String PREFS_NAME = "HostAddrPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        buttonClose = ( Button) findViewById(R.id.button_preferences_close);
        buttonEditFASTWS = (Button) findViewById(R.id.button_preferences_edit_fastws);
        textFASTWS =(TextView) findViewById(R.id.text_preferences_fastws);

        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String ipAddress = preferences.getString("IPAddress","");

        textFASTWS.setText(ipAddress);

        buttonEditFASTWS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFASTWSHostAddr(textFASTWS.getText().toString());
                Toast.makeText(getApplicationContext(),"Setting Saved", Toast.LENGTH_SHORT).show();
                Log.i(Common.LOGNAME,"Saving FASTWS");
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void saveFASTWSHostAddr(String anIpAdd) {

                SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("IPAddress", anIpAdd);
                editor.commit();
    }



}
