package com.sti.research.personalsafetyalert.util.screen.permission;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class MobileUserIDPreference {
    public static final String INPUT_MOBILE_USER_ID_REQUEST = "com.sti.research.personalsafetyalert.INPUT_MOBILE_USER_ID_REQUEST";

    public MobileUserIDPreference() {
    }

    private static MobileUserIDPreference instance;

    public static MobileUserIDPreference getInstance() {
        if (instance == null) instance = new MobileUserIDPreference();
        return instance;
    }

    public void setMobileUserIDPreference(Context context, String username) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(INPUT_MOBILE_USER_ID_REQUEST, username)
                .apply();
    }

    public String getMobileUserIDPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(INPUT_MOBILE_USER_ID_REQUEST, "");
    }
}
