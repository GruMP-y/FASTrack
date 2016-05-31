package com.weserv.grumpy.assetracker.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.weserv.grumpy.assetracker.Core.Connection.APIJsonArrayRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.APIJsonObjectRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.Core.Connection.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.Core.Connection.JSONArrayResponseCallback;
import com.weserv.grumpy.assetracker.Core.Connection.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.Core.Connection.MessageConstants;
import com.weserv.grumpy.assetracker.Core.Connection.RequestResponseCallback;
import com.weserv.grumpy.assetracker.Core.Employee;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.AccountMgmtUtils;
import com.weserv.grumpy.assetracker.Utils.Common;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity
        implements RequestResponseCallback, JSONObjectResponseCallback, JSONArrayResponseCallback {
    ProgressDialog prgDialog;
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
        Log.i(Common.LOGNAME,"Login : Starting login.");

        super.onCreate(savedInstanceState);
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

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        loadItem = LOADING_NONE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            //makeStringReq();
            showProgressDialog();
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
                    Log.d(Common.LOGNAME,"Login : Failed!");
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Incorrect username and/or password!", Toast.LENGTH_LONG).show();
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
        try {
            Log.d(Common.LOGNAME, "Login : request error -> " + error.getMessage());
            hideProgressDialog();
            switch (loadItem) {
                case LOADING_LOGIN:
                    if (error == null) {
                        Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_LONG).show();
                    } else if (error.getMessage().contains("ConnectException")) {
                        Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username or Password!", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            loadItem = LOADING_NONE;
        }
        catch(Exception ex){
            Log.d(Common.LOGNAME,ex.toString());
        }
    }

    @Override
    public void onJSONResponse(JSONObject response) {
        Log.d(Common.LOGNAME,"Login : JSONResponse -> "+ response.toString());

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

//    private Employee setUserDetail(JSONObject jsonUser) throws JSONException {
//        Employee user = new Employee();
//        if (jsonUser.has("FirstName")) {
//            if (!jsonUser.isNull("FirstName")) {
//                user.setFirstName(jsonUser.getString("FirstName"));
//            }
//        }
//        if (jsonUser.has("MiddleName")) {
//            if (!jsonUser.isNull("MiddleName")) {
//                user.setMiddleName(jsonUser.getString("MiddleName"));
//            }
//        }
//        if (jsonUser.has("LastName")) {
//            if (!jsonUser.isNull("LastName")) {
//                user.setLastName(jsonUser.getString("LastName"));
//            }
//        }
//        if (jsonUser.has("EmailAddress")) {
//            if (!jsonUser.isNull("EmailAddress")) {
//                user.setEmailAddress(jsonUser.getString("EmailAddress"));
//            }
//        }
//        if (jsonUser.has("EmployeeID")) {
//            if (!jsonUser.isNull("EmployeeID")) {
//                user.setEmployeeID(jsonUser.getInt("EmployeeID"));
//            }
//        }
//        if (jsonUser.has("Gender")) {
//            if (!jsonUser.isNull("Gender")) {
//                user.setGender(jsonUser.getString("Gender"));
//            }
//        }

//        Log.d("LOAD_DATA", user.toString());
//        return user;
//    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"Login : JSON Error -> "+error.getMessage());
        loadItem = LOADING_NONE;
    }

    @Override
    public void onJSONArrayResponse(JSONArray response) {
        Log.d(Common.LOGNAME,"Login : JSONArray response -> "+ response.toString());
        MyApp app = (MyApp) getApplication();
        AccessRight accessRight = new AccessRight();
        try {
//            accessRight = app.getSession().getAccessRight();
            accessRight.setAccessRight(response);
            app.getSession().setAccessRight(accessRight);
            Log.d(Common.LOGNAME,"Login : AccessRights -> "+ app.getSession().getAccessRight().toString());
        } catch (Exception ex) {
            Log.d(Common.LOGNAME, "Login : Error -> " + ex.getMessage());
        }


        hideProgressDialog();
        navigatetoHomeActivity();
        loadItem = LOADING_ACCESS_RIGHT;
    }

    @Override
    public void onJSONArrayErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"Login : Error -> "+ error.getMessage());
        hideProgressDialog();
        loadItem = LOADING_NONE;
    }

    private void showProgressDialog() {
       // if (loadItem == LOADING_NONE) {
            if (!prgDialog.isShowing()) {
                prgDialog.show();
            }
       // }
    }

    private void hideProgressDialog() {
        if (loadItem != LOADING_NONE) {
            if (prgDialog.isShowing())
                prgDialog.hide();
        }
    }

    public void navigatetoHomeActivity() {
        Log.i(Common.LOGNAME,"Login : Navigate Home");
        Intent homeIntent = new Intent(getApplicationContext(), MainScreenActivity.class);
        startActivity(homeIntent);
        clearFields();
    }

    public void navigatetoRegisterActivity(View view) {
        Log.i(Common.LOGNAME,"Login : Navigate Register");
        Intent register = new Intent(getApplicationContext(), Register.class);
        startActivity(register);
        clearFields();
    }

    public void navigatetoForgotPasswordActivity(View view) {
        Log.i(Common.LOGNAME,"Login : Navigate Forgot Password");
        Intent forgotpassword = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(forgotpassword);
        clearFields();
    }

    public void navigatetoPreferenceActivity() {
        Log.i(Common.LOGNAME,"Login : Navigate Preferences");
        Intent hostAddress = new Intent(getApplicationContext(), Preference.class);
        startActivity(hostAddress);
        clearFields();
    }

    private void clearFields() {
        textPassword.getText().clear();
        textEmpID.getText().clear();
    }

}