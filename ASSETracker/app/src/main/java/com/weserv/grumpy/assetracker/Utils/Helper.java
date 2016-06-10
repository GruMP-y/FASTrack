package com.weserv.grumpy.assetracker.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Trashvin on 5/25/16.
 */
public class Helper {

    public static int GetRandomColor(String aKey){
        String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String[] colors = {"#CE93D8","#B39DDB","#EC407A","#EF5350","#64B5F6","#81C784","#D4E157","#FF9800"};

        int position = 0;
        position = letters.indexOf(aKey)%8;

        return Color.parseColor(colors[position]);
    }

    public static void ShowAlertDialog(Context aContext, String aMessage){
        AlertDialog dialog = new AlertDialog.Builder(aContext).create();
        dialog.setCancelable(false);
        dialog.setTitle("Fastrack");
        dialog.setMessage(aMessage);

        dialog.setButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

}
