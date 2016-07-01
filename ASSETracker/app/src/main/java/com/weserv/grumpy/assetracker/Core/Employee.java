package com.weserv.grumpy.assetracker.Core;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.weserv.grumpy.assetracker.RESTHelper.APIJsonObjectRequestFactory;
import com.weserv.grumpy.assetracker.RESTHelper.HttpVolleyRequestSender;
import com.weserv.grumpy.assetracker.RESTHelper.JSONObjectResponseCallback;
import com.weserv.grumpy.assetracker.RESTHelper.MessageConstants;
import com.weserv.grumpy.assetracker.RESTHelper.RequestResponseCallback;
import com.weserv.grumpy.assetracker.View.MyApp;
import com.weserv.grumpy.assetracker.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by Vans on 9/27/2015.
 */
public class Employee extends Observable
        implements RequestResponseCallback, JSONObjectResponseCallback {
    private JSONObject employeeDetail;

    private int employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private int managerID;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private int departmentID;
    private String departmentDescription;
    private String groupName;
    private int positionID;
    private String positionDescription;
    private short status;

    private int current_action;

    public Employee() {
    }

    public int getEmployeeID() {
        int value;
        try {

            if ((employeeDetail.has("EmployeeID")) && (!employeeDetail.isNull("EmployeeID"))) {
                value = employeeDetail.getInt("EmployeeID");
            } else {
                value = 0;
            }

        } catch (JSONException ex) {
            value = 0;
        }
        return value;
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        String value;
        try {

            if ((employeeDetail.has("FirstName")) && (!employeeDetail.isNull("FirstName"))) {
                value = employeeDetail.getString("FirstName");
            } else {
                value = "";
            }

        } catch (JSONException ex) {
            value = "";
        }
        return value;
    }

    public String getMiddleName() {
        String value;
        try {

            if ((employeeDetail.has("MiddleName")) && (!employeeDetail.isNull("MiddleName"))) {
                value = employeeDetail.getString("MiddleName");
            } else {
                value = "";
            }

        } catch (JSONException ex) {
            value = "";
        }
        return value;
    }

    public String getLastName() {
        String value;
        try {

            if ((employeeDetail.has("LastName")) && (!employeeDetail.isNull("LastName"))) {
                value = employeeDetail.getString("LastName");
            } else {
                value = "";
            }

        } catch (JSONException ex) {
            value = "";
        }
        return value;
    }

    public int getManagerID() {
        int value;
        try {

            if ((employeeDetail.has("ManagerID")) && (!employeeDetail.isNull("ManagerID"))) {
                value = employeeDetail.getInt("ManagerID");
            } else {
                value = 0;
            }

        } catch (JSONException ex) {
            value = 0;
        }
        return value;
    }

    public String getGender() {
        String value;
        try {

            if ((employeeDetail.has("Gender")) && (!employeeDetail.isNull("Gender"))) {
                value = employeeDetail.getString("Gender");
            } else {
                value = "";
            }

        } catch (JSONException ex) {
            value = "";
        }
        return value;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        String value;
        try {

            if ((employeeDetail.has("EmailAddress")) && (!employeeDetail.isNull("EmailAddress"))) {
                value = employeeDetail.getString("EmailAddress");
            } else {
                value = "";
            }

        } catch (JSONException ex) {
            value = "";
        }
        return value;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentDescription() {

        String value;
        try {

            if ((employeeDetail.has("DepartmentName"))) {
                value = employeeDetail.getString("DepartmentName");
            } else {
                value =departmentDescription;
            }

        } catch (JSONException ex) {
            value =  departmentDescription;
        }
        return value;

    }

    public String getGroupName() {

        String value;
        try {

            if ((employeeDetail.has("GroupName"))) {
                value = employeeDetail.getString("GroupName");
            } else {
                value = groupName;
            }

        } catch (JSONException ex) {
            value =  groupName;
        }
        return value;
    }

    public int getPositionID() {
        return positionID;
    }

    public String getPositionDescription() {

        String value;
        try {

            if ((employeeDetail.has("Description"))) {
                value = employeeDetail.getString("Description");
            } else {
                value =departmentDescription;
            }

        } catch (JSONException ex) {
            value =  positionDescription;
        }
        return value;

    }

    public short getStatus() {
        return status;
    }

    public JSONObject getEmployeeDetail() {
        return employeeDetail;
    }

    public void setEmployeeDetail(JSONObject employeeDetail) {
        this.employeeDetail = employeeDetail;
    }


    public void getEmployeeDetailFromWS(Activity anActivity, MyApp anApp,int anEmployeeID){
        MyApp app = anApp;
        String ip = app.getHostAddress();
        String empID = Integer.toString(anEmployeeID);
        String api = MessageConstants.EMPLOYEES_API;
        String message = MessageConstants.EMPLOYEES_MESSAGE_CAT_GET_EMPLOYEE_BY_EMPLOYEE_ID;
        int callType = Request.Method.GET;

        APIJsonObjectRequestFactory request = new APIJsonObjectRequestFactory(ip, api, message, empID, callType);

        request.registerCallback(this);

        HttpVolleyRequestSender sender = new HttpVolleyRequestSender(anActivity);
        sender.sendRequest(request);

        current_action = Common.PROCESS_LOADING_EMPDETAIL;
    }

    //TODO: Add RequestToWS

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        text.append("EmployeeID: " + getEmployeeID());
        text.append(" FirstName: " + getFirstName());
        text.append(" MiddleName: " + getMiddleName());
        text.append(" LastName: " + getLastName());
        text.append(" EmailAddress: " + getEmailAddress());
        text.append(" Gender: " + getGender());
        text.append(" ManagerID: " + getManagerID());

        return text.toString();
    }

    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onJSONResponse(JSONObject response) {
        Log.d(Common.LOGNAME,"Employee -> onJSONResponse" + response.toString());
        switch (current_action) {
            case Common.PROCESS_LOADING_EMPDETAIL:
                this.setEmployeeDetail(response);
                Log.d(Common.LOGNAME,"Loaded...");
                break;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void onJSONErrorResponse(VolleyError error) {
        Log.d(Common.LOGNAME,"Employee -> onJSONResponseError");
        setChanged();
        notifyObservers(Common.ERROR);
    }
}
