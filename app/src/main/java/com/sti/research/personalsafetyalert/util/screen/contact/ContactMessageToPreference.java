package com.sti.research.personalsafetyalert.util.screen.contact;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.sti.research.personalsafetyalert.R;

public class ContactMessageToPreference {

    public static final String SELECT_STATE_REQUEST = "com.sti.research.personalsafetyalert.SELECT_STATE_REQUEST";

    private ContactMessageToPreference() {
    }

    private static ContactMessageToPreference instance;

    public static ContactMessageToPreference getInstance() {
        if (instance == null) instance = new ContactMessageToPreference();
        return instance;
    }

    public void setMessageToRadioSelectState(Context context, String selected) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(SELECT_STATE_REQUEST, selected)
                .apply();
    }

    public String getMessageToRadioSelectState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SELECT_STATE_REQUEST, context.getString(R.string.txt_single_person));
    }

}
