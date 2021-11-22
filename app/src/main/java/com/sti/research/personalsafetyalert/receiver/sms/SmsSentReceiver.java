package com.sti.research.personalsafetyalert.receiver.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;


public class SmsSentReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsSentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "SMS ONRECEIVER: " + intent.getIntExtra("object", 0));



        switch (getResultCode()) {
            case Activity.RESULT_OK://sent message
                Log.d(TAG, "SMS RECEIVER RESULT CODE: " + intent.getIntExtra("object", 0));

                SentReceiverObserver.getInstance().sentReceiverResult("SMS_SENT");
                break;

            case SmsManager.RESULT_ERROR_GENERIC_FAILURE://no load
                Log.d(TAG, "SMS RECEIVER: GENERIC FAILURE");

                SentReceiverObserver.getInstance().sentReceiverResult("GENERIC_FAILURE");
                break;

            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Log.d(TAG, "SMS RECEIVER: SMS no service available");
                break;

            case SmsManager.RESULT_ERROR_NULL_PDU:
                Log.d(TAG, "SMS RECEIVER: SMS null PDU");
                break;

            case SmsManager.RESULT_ERROR_RADIO_OFF://Airplane mode
                Log.d(TAG, "SMS RECEIVER: SMS radio off");
                break;
        }

    }
}
