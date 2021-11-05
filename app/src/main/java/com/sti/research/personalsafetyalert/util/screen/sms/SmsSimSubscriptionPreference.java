package com.sti.research.personalsafetyalert.util.screen.sms;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class SmsSimSubscriptionPreference {

    private static final String KEY_SMS_SIM_SUBSCRIPTION_STATUS = "com.sti.research.personalsafetyalert.KEY_SMS_SIM_SUBSCRIPTION_STATUS";

    public static final String FROM_SIM_ONE_FAILED_STATUS = "SIM_ONE_FAILED_STATUS";
    public static final String FROM_SIM_TWO_FAILED_STATUS = "SIM_TWO_FAILED_STATUS";

    public static final String FROM_SINGLE_SIM_FAILED_STATUS = "SINGLE_SIM_FAILED_STATUS";

    public static final String FROM_SIM_REQUESTED_LOAD_STATUS = "SIM_REQUESTING_LOAD_STATUS";

    public static final String FROM_SIM_ONE_RESEND_FAILED_STATUS = "SIM_ONE_RESEND_FAILED_STATUS";


    private SmsSimSubscriptionPreference() {
    }

    private static SmsSimSubscriptionPreference instance;

    public static SmsSimSubscriptionPreference settings() {
        if (instance == null)
            instance = new SmsSimSubscriptionPreference();
        return instance;
    }

    public void setSmsSimSubscriptionStatus(Context context, String status) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(KEY_SMS_SIM_SUBSCRIPTION_STATUS, status)
                .apply();
    }

    public String getSmsSimSubscriptionStatus(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_SMS_SIM_SUBSCRIPTION_STATUS, "");
    }

}
