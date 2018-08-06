package com.izzaweb.bengkel.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.izzaweb.bengkel.MainActivity;
import com.izzaweb.bengkel.Ui.LoginActivity;


public class UserSession {
    SharedPreferences preferences;
    Editor editor;
    Context mContext;
    public static final String PREFER_NAME = "PrefBengkel";
    public static final String IS_USER_LOGIN = "LoginStatus";
    public static final String KEY_USER_ID = "id_user";

    public UserSession(Context mContext) {
        this.mContext = mContext;
        preferences = mContext.getSharedPreferences(PREFER_NAME,0);
        editor = preferences.edit();
    }
    public void setLoginSession(int userid){
        editor.putBoolean(IS_USER_LOGIN,true);
        editor.putInt(KEY_USER_ID,userid);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.isUserLoggedIn()){
            Intent i = new Intent(mContext, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }else {
            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(mContext, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }
    public int getUserID(){
        return preferences.getInt(KEY_USER_ID,0);
    }
    // Check for login
    public boolean isUserLoggedIn(){
        return preferences.getBoolean(IS_USER_LOGIN, false);
    }
}

