package com.weserv.grumpy.assetracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.weserv.grumpy.assetracker.Core.AccessRight;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonObjectRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Employee;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.AccountMgmtUtils;
import com.weserv.grumpy.assetracker.Utils.Common;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends BaseActivity {

    EditText textEmpID;
    EditText textPassword;
    Button buttonLogin;

    private short loadItem;
    private final short LOADING_NONE = 0;
    private final short LOADING_LOGIN = 1;
    private final short LOADING_EMPLOYEE_DETAIL = 2;
    private final short LOADING_ACCESS_RIGHT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.i(Common.LOGNAME,"Login : Starting login.");
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textEmpID = (EditText) findViewById(R.id.text_employee_id_login);
        textPassword = (EditText) findViewById(R.id.text_passsword_login);

        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


        loadItem = LOADING_NONE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_host_address) {
            navigatetoPreferenceActivity();
        }

        return true;
    }

    public void loginUser() {
        String empID = textEmpID.getText().toString();
        String password = textPassword.getText().toString();
        if (AccountMgmtUtils.isNotNull(empID) && AccountMgmtUtils.isNotNull(password)) {

            this.showProgressDialog();

            sendLoginRequest(empID, AccountMgmtUtils.generateHash(password));
        } else {
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
            loadItem = LOADING_NONE;
        }
    }

    public void sendLoginRequest(String empID, String hashedPassword) {
        Log.i(Common.LOGNAME,"Login : Sending login request.");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("EmployeeID", empID);
        params.put("HashedPassword", hashedPassword);

        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.USERS_API;
        String message = MessageConstants.USERS_MESSAGE_CAT_LOGIN;
        int callType = Request.Method.POST;


        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(params);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);
        loadItem = LOADING_LOGIN;
    }

    public void loadEmployeeDetail() {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String empID = app.getSession().getEmployeeID();
        String api = MessageConstants.EMPLOYEES_API;
        String message = MessageConstants.EMPLOYEES_MESSAGE_CAT_GET_EMPLOYEE_BY_EMPLOYEE_ID;
        int callType = Request.Method.GET;


        APIJsonObjectRequestFactory request = new APIJsonObjectRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

        loadItem = LOADING_EMPLOYEE_DETAIL;
    }

    public void loadAccessRights() {
        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String empID = app.getSession().getEmployeeID();
        String api = MessageConstants.USERS_API;
        String message = MessageConstants.USERS_MESSAGE_CAT_GET_ACCESS_RIGHTS;
        int callType = Request.Method.GET;

        APIJsonArrayRequestFactory request = new APIJsonArrayRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);

        Log.d(Common.LOGNAME, "Login : Response -> " +response.toString());
        switch (loadItem) {
            case LOADING_LOGIN:
                if (response.equals("\"SUCCESSFUL\"")) {
                    Log.d(Common.LOGNAME, "Login : Successful!");
//                    hideProgressDialog();
//                    navigatetoHomeActivity();
                    MyApp app = (MyApp) getApplication();
                    app.getSession().startSession(textEmpID.getText().toString());
                    Log.d(Common.LOGNAME, "Login : IsLoggedIN: " + app.getSession().isLoggedIn());
                    loadEmployeeDetail();
                } else {
                    this.displayToast("Incorrect username and/or password.");
                    loadItem = LOADING_NONE;
                }
                break;
            case LOADING_EMPLOYEE_DETAIL:
                break;
            case LOADING_ACCESS_RIGHT:
                break;

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);

        try {
            switch (loadItem) {
                case LOADING_LOGIN:
                    if (error == null) {
                        this.displayToast("Network error.");
                    } else if (error.getMessage().contains("ConnectException")) {
                        this.displayToast("Network error.");
                    } else {
                        this.displayToast("Invalid username and/or password.");
                    }
                    break;
            }
            loadItem = LOADING_NONE;
        }
        catch(Exception ex){
            this.displayToast("Network error.");
        }
    }

    @Override
    public void onJSONResponse(JSONObject response) {
        super.onJSONResponse(response);

        MyApp app = (MyApp) getApplication();
        switch (loadItem) {
            case LOADING_EMPLOYEE_DETAIL:

//                try {
                    Employee empDetail = new Employee();
//
                    empDetail.setEmployeeDetail(response);
                    app.getSession().setUser(empDetail);
                Log.d(Common.LOGNAME,"Login : Data -> " + app.getSession().getUser().toString());
//                } catch (JSONException ex) {
//                    //TODO: what to do?? should retry?
//                    Log.d("LOAD_DATA", "Loading Employee Detail... Error!");
//                    Log.d("LOAD_DATA", ex.getMessage());
//                    app.getSession().endSession();
//                    hideProgressDialog();
//                    loadItem = LOADING_NONE;
//                }
                loadAccessRights();
                break;
        }
    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {
        super.onJSONErrorResponse(error);

        this.displayToast("Login error.");
        loadItem = LOADING_NONE;
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
        super.onJSONArrayResponse(response);

        MyApp app = (MyApp) getApplication();
        AccessRight accessRight = new AccessRight();
        try {
            accessRight.setAccessRight(response);
            app.getSession().setAccessRight(accessRight);
            Log.d(Common.LOGNAME,"Login : AccessRights -> "+ app.getSession().getAccessRight().toString());
        } catch (Exception ex) {
            this.displayToast("Login error.");
        }
        navigatetoHomeActivity();
        loadItem = LOADING_ACCESS_RIGHT;
    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        super.onJSONArrayErrorResponse(error);

        this.displayToast("Login error. " + error.getMessage());
        loadItem = LOADING_NONE;
    }

    public void navigatetoHomeActivity() {
        Log.i(Common.LOGNAME,"Login : Navigate Home");
        Intent homeIntent = new Intent(getApplicationContext(), MainScreenActivity.class);
        startActivity(homeIntent);
        clearFields();
    }

    public void navigatetoRegisterActivity(View view) {
        Log.i(Common.LOGNAME,"Login : Navigate RegisterActivity");
        Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(register);
        clearFields();
    }

    public void navigatetoForgotPasswordActivity(View view) {
        Log.i(Common.LOGNAME,"Login : Navigate Forgot Password");
        Intent forgotpassword = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(forgotpassword);
        clearFields();
    }

    public void navigatetoPreferenceActivity() {
        Log.i(Common.LOGNAME,"Login : Navigate Preferences");
        Intent hostAddress = new Intent(getApplicationContext(), PreferenceActivity.class);
        startActivity(hostAddress);
        clearFields();
    }

    private void clearFields() {
        textPassword.getText().clear();
        textEmpID.getText().clear();
    }

}