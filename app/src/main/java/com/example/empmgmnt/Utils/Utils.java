package com.example.empmgmnt.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {

    public static void showMessage(Context context,String Message){

        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
    }


    public static void printData(String key,String value){
        Log.i(key,value);
    }


}
