package com.weserv.grumpy.assetracker.Model;
/**
 * Created by Trashvin on 6/27/16.
 */
public class KeyValuePair {

    private String _key;
    private String _value;

    public KeyValuePair(){

    }

    public KeyValuePair(String aKey, String aValue){
        _key = aKey;
        _value = aValue;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }


}
