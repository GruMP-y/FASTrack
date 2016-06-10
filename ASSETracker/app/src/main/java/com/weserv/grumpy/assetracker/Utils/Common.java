package com.weserv.grumpy.assetracker.Utils;

/**
 * Created by Developer on 4/11/2016.
 */
public class Common {
    public final static String LOGNAME="FAST";
    public final static String ERROR="ERROR";
    public final static String DATA="DATA";

    public final static int PROCESS_LOADING_NONE =0;
    public final static int PROCESS_LOADING_LOGIN =1;
    public final static int PROCESS_LOADING_EMPDETAIL =2;
    public final static int PROCESS_LOADING_ACCESSRIGHTS =3;
    public final static int PROCESS_LOADING_ASSETDETAIL = 4;
    public final static int PROCESS_LOADING_ASSETLIST =5;
    public final static int PROCESS_TRANSFER = 6;
    public final static int PROCESS_ASSIGN =7;
    public final static int PROCESS_LOADING_ASSETTYPES=8;
    public final static int PROCESS_ACCEPTREJECT = 9;
    public final static int PROCESS_BARCODE_SCANNING=10;
    public final static int PROCESS_EMPTY = 11;
    public final static int PROCESS_TRANSFER_ACTIVE=12;
    public final static int PROCESS_NO_CNXN =13;
    public final static int PROCESS_RELEASE=14;

    public final static String FLD_ASSIGN_ID="AssetAssignmentID";
    public final static String FLD_EMP_ID="EmployeeID";
    public final static String FLD_ASSET_ID="FixAssetID";
    public final static String FLD_MODEL ="Model";
    public final static String FLD_SERIAL_NO="SerialNumber";
    public final static String FLD_ASSET_TAG="AssetTag";
    public final static String FLD_BRAND="Brand";
    public final static String FLD_CLS_ID ="AssetClassID";
    public final static String FLD_CLS_DESC="ClassDescription";
    public final static String FLD_ASSET_STAT_ID="AssetStatusID";
    public final static String FLD_ASSET_STAT_DESC="StatusDescription";
    public final static String FLD_ASSET_TYP_ID="AssetTypeID";
    public final static String FLD_ASSET_TYP_DESC = "TypeDescription";
    public final static String FLD_LOC_ID="LocationID";
    public final static String FLD_LOC_NAME="LocationName";
    public final static String FLD_LOC_COUNTRY="Country";
    public final static String FLD_ASSIGN_STAT_ID="AssignmentStatusID";
    public final static String FLD_ASSIGN_STAT_DESC = "AssignmentStatus";
    public final static String FLD_MIS_ID = "MISEmployeeID";
    public final static String FLD_ADMIN_ID="AdminID";
    public final static String FLD_DEPT_ID="DepartmentID";
    public final static String FLD_FROM_ID="FromID";
    public final static String FLD_TO_ID="ToID";
    public final static String FLD_DEPT_NAME="DepartmentName";
    public final static String FLD_GRP_NAME="GroupName";
    public final static String FLD_ACCESS_LVL="AccessLevel";
    public final static String FLD_RCPT_EMP_ID="ReceipientEmpID";
    public final static String FLD_ASSIGNING_EMP_ID="AssigningEmpID";

    public final static String PARAM_ACCEPTING_EMP_ID="acceptingEmployeeID";
    public final static String PARAM_ACCEPTED="accepted";
    public final static String PARAM_ASSIGN_ID="assignmentID";
    public final static String PARAM_CUR_ASSIGN_ID="currentAssignmentID";
    public final static String PARAM_TOMIS ="ToMIS";
    public final static String PARAM_RQSTOR_ID="RequestorID";
    public final static String PARAM_RCPT_ID="ReceipientID";
    public final static String PARAM_ASSET_ID="FixAssetID";
    public final static String PARAM_RQR_APPROVAL="RequireApproval";
    public final static String PARAM_RMRKS="OptionalRemarks";
    public final static String PARAM_APPROVING_ID="ApprovingID";
    public final static String PARAM_REASON_CD="ReasonCode";
    public final static String PARAM_ACCEPTING_ID="AcceptingID";
    public final static String PARAM_REMARKS="OptionalRemarks";

    public final static String PARAM_BNDL_SRC="SOURCE";
    public final static String PARAM_BNDL_DATA_01="DATA01";
    public final static String PARAM_BNDL_DATA_02="DATA02";

    public final static int SRC_MYASSSETS = 1;
    public final static int SRC_MYACCEPTANCES = 2;
    public final static int SRC_MISASSETS = 3;
    public final static int SRC_MISACCEPTANCES = 4;
    public final static int SRC_ADMINASSETS = 5;
    public final static int SRC_ADMINACCEPTANCES = 6;
    public final static int SRC_ADMINAPPROVALS = 7;
    public final static int SRC_MGRAPPROVALS = 8;

    public final static int RSPNS_CD_GET_ASSET_TAG = 2;

}
