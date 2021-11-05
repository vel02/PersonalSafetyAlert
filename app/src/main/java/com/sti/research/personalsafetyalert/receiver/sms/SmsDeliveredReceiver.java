package com.sti.research.personalsafetyalert.receiver.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SmsDeliveredReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsDeliveredReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Log.d(TAG, "SMS DELIVERED: SMS delivered");
                break;
            case Activity.RESULT_CANCELED:
                Log.d(TAG, "SMS DELIVERED: SMS not delivered");
                break;
        }
    }
}
