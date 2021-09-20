package com.sti.research.personalsafetyalert.util.screen.home;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class HomeInitialMessage {

    public static final String INITIAL_MESSAGE_REQUEST = "com.sti.research.personalsafetyalert.INITIAL_MESSAGE_REQUEST";

    private HomeInitialMessage() {
    }

    private static HomeInitialMessage instance;

    public static HomeInitialMessage getInstance() {
        if (instance == null) instance = new HomeInitialMessage();
        return instance;
    }

    public void setInitializeMessagesState(Context context, boolean checked) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(INITIAL_MESSAGE_REQUEST, checked)
                .apply();
    }

    public boolean getInitializeMessagesState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(INITIAL_MESSAGE_REQUEST, false);
    }


}
