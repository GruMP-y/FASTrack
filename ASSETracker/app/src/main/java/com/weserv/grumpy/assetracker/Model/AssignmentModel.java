package com.weserv.grumpy.assetracker.Model;

import java.util.Date;

/**
 * Created by Trashvin on 6/27/16.
 */
public class AssignmentModel {
    private int _assetAssignmentID;
    private int _employeeID;
    private int _fixAssetID;
    private String _model;
    private String _serialNumber;
    private String _assetTag;
    private String _brand;
    private String _assetRemarks;
    private int _assetClassID;
    private String _assetCalssDescription;
    private int _assetTypeID;
    private String _assetTypeDescription;
    private int _locationID;
    private String _locationName;
    private String _country;
    private int _assignmentStatusID;
    private String _assignmentStatusDescription;
    private Date _dateAssigned;
    private Date _dateReleased;
    private String  _assignmentRemarks;
    private int _fromID;
    private int ToID;

    public AssignmentModel(){

    }

    public int get_assetAssignmentID() {
        return _assetAssignmentID;
    }

    public void set_assetAssignmentID(int _assetAssignmentID) {
        this._assetAssignmentID = _assetAssignmentID;
    }

    public int get_employeeID() {
        return _employeeID;
    }

    public void set_employeeID(int _employeeID) {
        this._employeeID = _employeeID;
    }

    public int get_fixAssetID() {
        return _fixAssetID;
    }

    public void set_fixAssetID(int _fixAssetID) {
        this._fixAssetID = _fixAssetID;
    }

    public String get_model() {
        return _model;
    }

    public void set_model(String _model) {
        this._model = _model;
    }

    public String get_serialNumber() {
        return _serialNumber;
    }

    public void set_serialNumber(String _serialNumber) {
        this._serialNumber = _serialNumber;
    }

    public String get_assetTag() {
        return _assetTag;
    }

    public void set_assetTag(String _assetTag) {
        this._assetTag = _assetTag;
    }

    public String get_brand() {
        return _brand;
    }

    public void set_brand(String _brand) {
        this._brand = _brand;
    }

    public String get_assetRemarks() {
        return _assetRemarks;
    }

    public void set_assetRemarks(String _assetRemarks) {
        this._assetRemarks = _assetRemarks;
    }

    public int get_assetClassID() {
        return _assetClassID;
    }

    public void set_assetClassID(int _assetClassID) {
        this._assetClassID = _assetClassID;
    }

    public String get_assetCalssDescription() {
        return _assetCalssDescription;
    }

    public void set_assetCalssDescription(String _assetCalssDescription) {
        this._assetCalssDescription = _assetCalssDescription;
    }

    public int get_assetTypeID() {
        return _assetTypeID;
    }

    public void set_assetTypeID(int _assetTypeID) {
        this._assetTypeID = _assetTypeID;
    }

    public String get_assetTypeDescription() {
        return _assetTypeDescription;
    }

    public void set_assetTypeDescription(String _assetTypeDescription) {
        this._assetTypeDescription = _assetTypeDescription;
    }

    public int get_locationID() {
        return _locationID;
    }

    public void set_locationID(int _locationID) {
        this._locationID = _locationID;
    }

    public String get_locationName() {
        return _locationName;
    }

    public void set_locationName(String _locationName) {
        this._locationName = _locationName;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public int get_assignmentStatusID() {
        return _assignmentStatusID;
    }

    public void set_assignmentStatusID(int _assignmentStatusID) {
        this._assignmentStatusID = _assignmentStatusID;
    }

    public String get_assignmentStatusDescription() {
        return _assignmentStatusDescription;
    }

    public void set_assignmentStatusDescription(String _assignmentStatusDescription) {
        this._assignmentStatusDescription = _assignmentStatusDescription;
    }

    public Date get_dateAssigned() {
        return _dateAssigned;
    }

    public void set_dateAssigned(Date _dateAssigned) {
        this._dateAssigned = _dateAssigned;
    }

    public Date get_dateReleased() {
        return _dateReleased;
    }

    public void set_dateReleased(Date _dateReleased) {
        this._dateReleased = _dateReleased;
    }

    public String get_assignmentRemarks() {
        return _assignmentRemarks;
    }

    public void set_assignmentRemarks(String _assignmentRemarks) {
        this._assignmentRemarks = _assignmentRemarks;
    }

    public int get_fromID() {
        return _fromID;
    }

    public void set_fromID(int _fromID) {
        this._fromID = _fromID;
    }

    public int getToID() {
        return ToID;
    }

    public void setToID(int toID) {
        ToID = toID;
    }





}
