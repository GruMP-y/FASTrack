package com.weserv.grumpy.assetracker.Model;

/**
 * Created by Trashvin on 6/27/16.
 */
public class AccessRightModel {

    private int _accessID;
    private int _employeeID;
    private int _departmentID;
    private int _accessLevel;
    private int _status;

    public AccessRightModel(){

    }

    public AccessRightModel(int anAccessID, int anEmployeeID, int aDepartmentID, int anAccessLevel, int aStatus){
        _accessID = anAccessID;
        _employeeID = anEmployeeID;
        _departmentID = aDepartmentID;
        _accessLevel = anAccessLevel;
        _status = aStatus;
    }

    public int get_accessID() {
        return _accessID;
    }

    public void set_accessID(int _accessID) {
        this._accessID = _accessID;
    }

    public int get_employeeID() {
        return _employeeID;
    }

    public void set_employeeID(int _employeeID) {
        this._employeeID = _employeeID;
    }

    public int get_departmentID() {
        return _departmentID;
    }

    public void set_departmentID(int _departmentID) {
        this._departmentID = _departmentID;
    }

    public int get_accessLevel() {
        return _accessLevel;
    }

    public void set_accessLevel(int _accessLevel) {
        this._accessLevel = _accessLevel;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }


}
