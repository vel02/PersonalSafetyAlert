package com.sti.research.personalsafetyalert.util.screen.contact;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class SelectPreferredContactPreference {

    private static final String SELECT_PREFERRED_CONTACT_REQUEST = "com.sti.research.personalsafetyalert.SELECT_PREFERRED_CONTACT_REQUEST";
    public static final String SELECT_PREFERRED_CONTACT_SINGLE = "SINGLE_CONTACT";
    public static final String SELECT_PREFERRED_CONTACT_MULTIPLE = "MULTIPLE_CONTACT";

    private SelectPreferredContactPreference() {
    }

    private static SelectPreferredContactPreference instance;

    public static SelectPreferredContactPreference getInstance() {
        if (instance == null) instance = new SelectPreferredContactPreference();
        return instance;
    }

    public void setSelectPreferredContact(Context context, String selected) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(SELECT_PREFERRED_CONTACT_REQUEST, selected)
                .apply();
    }

    public String getSelectPreferredContact(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SELECT_PREFERRED_CONTACT_REQUEST, SELECT_PREFERRED_CONTACT_SINGLE);
    }

}
