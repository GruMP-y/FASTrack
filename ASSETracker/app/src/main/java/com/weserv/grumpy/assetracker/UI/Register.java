package com.weserv.grumpy.assetracker.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.Core.Connection.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.AccountMgmtUtils;

import java.util.HashMap;

/**
 * Register Activity Class
 */
public class Register extends AppCompatActivity
        implements RequestResponseCallback {
    ProgressDialog prgDialog;
    EditText textEmpID;
    Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textEmpID = (EditText) findViewById(R.id.text_employee_id_register);
        buttonRegister = (Button) findViewById(R.id.button_register);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empID = textEmpID.getText().toString();
                registerUser(empID);
            }
        });

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
    }

    private void showProgressDialog() {
        if (!prgDialog.isShowing())
            prgDialog.show();
    }

    private void hideProgressDialog() {
        if (prgDialog.isShowing())
            prgDialog.hide();
    }

    public void registerUser(String EmpID) {
        String name = EmpID;
        if (AccountMgmtUtils.isNotNull(name)) {
            showProgressDialog();
            sendResetRequest(name);
        } else {
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }


    public void sendResetRequest(String empID) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("EmployeeID", empID);

        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.USERS_API;
        String message = MessageConstants.USERS_MESSAGE_CAT_REGISTRATION;
        int callType = Request.Method.POST;


        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(params);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

    }

    @Override
    public void onResponse(String response) {
        Log.d("MMT", "String resp");
        hideProgressDialog();
        if (response.equals("\"SUCCESSFUL\"")) {
            hideProgressDialog();
            Toast.makeText(getApplicationContext(), "Account was registered!", Toast.LENGTH_LONG).show();
            navigateToLoginActivity();
        } else {
            hideProgressDialog();
            Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideProgressDialog();
        Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
    }

    /**
     * Method which navigates from Register Activity to Login Activity
     */
    public void navigateToLoginActivity() {
        this.finish();
    }


}