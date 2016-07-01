package com.weserv.grumpy.assetracker.View;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.RESTHelper.APIStringRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.R;
import com.weserv.grumpy.assetracker.Utils.AccountMgmtUtils;

import java.util.HashMap;

/**
 * Forgot Password Activity Class
 */
public class ForgotPasswordActivity extends BaseActivity {
    Button buttonReset;
    EditText textEmpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpassword);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textEmpID = (EditText) findViewById(R.id.text_employee_id_reset);
        buttonReset = (Button) findViewById(R.id.button_reset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empID = textEmpID.getText().toString();

                resetPassword(empID);
            }
        });
    }

    public void resetPassword(String empID) {
        String name = textEmpID.getText().toString();
        if (AccountMgmtUtils.isNotNull(name)) {

            this.showProgressDialog();
            sendResetRequest(name);
            //makeStringReq();
        } else {
            this.displayToast("Please fill the form, don't leave any field blank");
        }
    }

    public void sendResetRequest(String empID) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("EmployeeID", empID);

        MyApp app = (MyApp) getApplication();
        String ip = app.getHostAddress();
        String api = MessageConstants.USERS_API;
        String message = MessageConstants.USERS_MESSAGE_CAT_RESET_PASSWORD;
        int callType = Request.Method.PUT;


        APIStringRequestFactory request = new APIStringRequestFactory(ip, api, message, callType);

        request.setParams(params);
        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(this);
        sender.sendRequest(request);

    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);

        if (response.equals("\"SUCCESSFUL\"")) {
            this.displayToast("Password reset successful.");
            navigateToLoginActivity();
        } else {
            this.displayToast("Password reset failed.");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);

        this.displayToast("Password reset failed.");
    }

    /**
     * Method which navigates from RegisterActivity Activity to Login Activity
     */
    public void navigateToLoginActivity() {

        this.finish();
    }

}