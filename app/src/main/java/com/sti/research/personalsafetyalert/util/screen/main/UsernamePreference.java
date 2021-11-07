package com.sti.research.personalsafetyalert.util.screen.main;

import android.content.Context;

import androidx.preference.PreferenceManager;


public class UsernamePreference {

    public static final String INPUT_USERNAME_REQUEST = "com.sti.research.personalsafetyalert.INPUT_USERNAME_REQUEST";


    private UsernamePreference() {
    }

    private static UsernamePreference instance;

    public static UsernamePreference getInstance() {
        if (instance == null) instance = new UsernamePreference();
        return instance;
    }

    public void setUsernameInput(Context context, String username) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(INPUT_USERNAME_REQUEST, username)
                .apply();
    }

    public String getUsernameInput(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(INPUT_USERNAME_REQUEST, "");
    }


}
