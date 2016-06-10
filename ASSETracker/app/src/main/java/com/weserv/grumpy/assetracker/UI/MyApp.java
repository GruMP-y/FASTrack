package com.weserv.grumpy.assetracker.UI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.weserv.grumpy.assetracker.Core.Session;

/**
 * Created by Vans on 9/27/2015.
 */
public class MyApp extends Application {

    private Session mSession;
    private String mHostAddress;
    public static final String PREFS_NAME = "HostAddrPreference";
    private final String DEFLT_HOST_ADDRRESS ="192.168.1.15";

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    public Session getSession() {
        return mSession;
    }

    public void setSession(Session mSession) {
        this.mSession = mSession;
    }

    public void initialize() {
        mSession = new Session();
        mHostAddress = getHostAddress();
    }



    public String getHostAddress() {

        String hostIP;

        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String newHostAddress = preferences.getString("IPAddress","");

        if (newHostAddress.length() == 0) {
            Log.d("DEFLT_HOST", "set default host address");
            hostIP = DEFLT_HOST_ADDRRESS;
        }else {
            hostIP = newHostAddress;
        }
        return hostIP;
    }
}
