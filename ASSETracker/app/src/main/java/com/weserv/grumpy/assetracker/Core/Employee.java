package com.weserv.grumpy.assetracker.Core;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vans on 9/27/2015.
 */
public class Employee {
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
}
