package com.example.empmgmnt.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public Session(Context ctx){
        prefs = ctx.getSharedPreferences("empmanage", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){ return prefs.getBoolean("loggedInmode", false); }

    public void setUserDet(String id,String uname,String urole){
        editor.putString("userid",id);
        editor.putString("username",uname);
        editor.putString("userrole",urole);

        editor.commit();
    }

    public String getUId(){return prefs.getString("userid","");}

    public String getUName(){return prefs.getString("username","");}

    public String getUrole(){return prefs.getString("userrole","");}

    public void logout(){
        editor.clear();
        editor.commit();
    }




}
