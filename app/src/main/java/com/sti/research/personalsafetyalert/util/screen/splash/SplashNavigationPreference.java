package com.sti.research.personalsafetyalert.util.screen.splash;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class SplashNavigationPreference {

    public static final String SPLASH_NAVIGATE_STATE_REQUEST = "com.sti.research.personalsafetyalert.SPLASH_NAVIGATE_STATE_REQUEST";

    private SplashNavigationPreference() {
    }

    private static SplashNavigationPreference instance;

    public static SplashNavigationPreference getInstance() {
        if (instance == null) instance = new SplashNavigationPreference();
        return instance;
    }

    public void setSplashNavigationStateState(Context context, boolean checked) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(SPLASH_NAVIGATE_STATE_REQUEST, checked)
                .apply();
    }

    public boolean getSplashNavigationStateState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SPLASH_NAVIGATE_STATE_REQUEST, false);
    }


}
