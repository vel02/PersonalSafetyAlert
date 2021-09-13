package com.sti.research.personalsafetyalert.util.screen.home;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class HomeSwitchPreference {

    public static final String SWITCH_STATE_REQUEST = "com.sti.research.personalsafetyalert.SWITCH_STATE_REQUEST";

    private HomeSwitchPreference() {
    }

    private static HomeSwitchPreference instance;

    public static HomeSwitchPreference getInstance() {
        if (instance == null) instance = new HomeSwitchPreference();
        return instance;
    }

    public void setSwitchStateState(Context context, boolean checked) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(SWITCH_STATE_REQUEST, checked)
                .apply();
    }

    public boolean getSwitchStateState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SWITCH_STATE_REQUEST, false);
    }


}
