package com.weserv.grumpy.assetracker.Core.Connection;

/**
 * Created by baylefra on 1/29/2016.
 */
public class MessageConstants {
    //Users API
    public final static String USERS_API = "User";
    public  final static String USERS_MESSAGE_CAT_REGISTRATION = "Registration";
    public  final static String USERS_MESSAGE_CAT_LOGIN = "Login";
    public   final static String USERS_MESSAGE_CAT_CHANGE_PASSWORD = "ChangePassword";
    public final static String USERS_MESSAGE_CAT_RESET_PASSWORD = "ResetPassword";
    public final static String USERS_MESSAGE_CAT_GET_ACCESS_RIGHTS = "Access";

    //Employees API
    public final static String EMPLOYEES_API = "Employee";
    public final static String EMPLOYEES_MESSAGE_CAT_GET_EMPLOYEE_BY_EMPLOYEE_ID = "EmployeeID";

    //Assets API
    public final static String ASSETS_API = "Asset";
    public final static String ASSETS_MESSAGE_CAT_GET_ASSET_BY_FIX_ASSET_ID = "FixAssetID";
    public final static String ASSETS_MESSAGE_CAT_GET_ASSET_BY_FIX_ASSET_TAG = "AssetTag";
    public final static String ASSETS_MESSAGE_CAT_GET_ASSET_BY_SERIAL_NO = "SerialNumber";
    public final static String ASSETS_MESSAGE_CAT_GET_ASSET_BY_ISSUER_ID = "IssuerID";
    public final static String ASSETS_MESSAGE_CAT_ADD_A_NEW_ASSET = "AddAsset";
    public final static String ASSETS_MESSAGE_CAT_GET_ASSET_TYPES="GetAssetTypes";

    //Assignments API
    public final static String ASSIGNMENTS_API = "Assignment";
    public final static String ASSIGNMENTS_MESSAGE_ASSIGN_BY_ASSETTAG = "New/AssetTag";
    public final static String ASSIGNMENTS_MESSAGE_ACCEPTREJECT="AcceptReject";
    public final static String ASSIGNMENTS_MESSAGE_TRANSFER="TransferAsset";

    //TODO
    public final static String ASSIGNMENT_MESSAGE_CAT_GET_ASSIGNMENT_BY_EMPLOYEE_ID = "EmployeeID";
    public final static String ASSIGNMENT_MESSAGE_CAT_POST_TRANSFER_ASSET = "TransferAsset";
    public final static String ASSIGNMENT_MESSAGE_CAT_POST_RELEASE_ASSET = "ReleaseAsset";

    //Transactions API
    public final static String TRANSACTIONS_API = "Transaction";
    public final static String TRANSACTIONS_MESSAGE_CAT_GET_MIS_TRANS_BY_EMPLOYEE_ID = "MIS";
    public final static String TRANSACTIONS_MESSAGE_CAT_GET_MGR_TRANS_BY_EMPLOYEE_ID="ManagerByID";
    public final static String TRANSACTIONS_MESSAGE_CAT_GET_ADMIN_TRANS_BY_EMPLOYEE_ID="Admin";

    //TODO

    //Call Types
    public final static String USERS_CALL_TYPE_POST = "POST";
    public final static String USERS_CALL_TYPE_GET = "GET";
    public final static String USERS_CALL_TYPE_PUT = "PUT";

    //Connection Builder
    public final static String DOMAIN = ":8090";
    public final static String HTTP= "http://";
    public final static String ADDRESS = "";
    public final static String API ="/api";



    public final static String URL_TEMPLATE = "http://%s:8090/api/%s/%s";
    public final static String URL_TEMPLATE_WITH_PARAM = "http://%s:8090/api/%s/%s/%s";


}
