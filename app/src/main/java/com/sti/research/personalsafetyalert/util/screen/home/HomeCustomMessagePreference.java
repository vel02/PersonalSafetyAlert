package com.sti.research.personalsafetyalert.util.screen.home;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.sti.research.personalsafetyalert.R;

public class HomeCustomMessagePreference {
    public static final String CUSTOM_MESSAGE_STATE_REQUEST = "com.sti.research.personalsafetyalert.CUSTOM_MESSAGE_STATE_REQUEST";

    private HomeCustomMessagePreference() {
    }

    private static HomeCustomMessagePreference instance;

    public static HomeCustomMessagePreference getInstance() {
        if (instance == null) instance = new HomeCustomMessagePreference();
        return instance;
    }

    public void setCustomMessageStorage(Context context, String message) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(CUSTOM_MESSAGE_STATE_REQUEST, message)
                .apply();
    }

    public String getCustomMessageStorage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(CUSTOM_MESSAGE_STATE_REQUEST, context.getString(R.string.txt_sample_text_00));
    }

}
