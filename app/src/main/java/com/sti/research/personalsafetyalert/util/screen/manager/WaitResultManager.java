package com.sti.research.personalsafetyalert.util.screen.manager;

import android.os.CountDownTimer;
import android.util.Log;

public class WaitResultManager extends CountDownTimer {

    private static final String TAG = "WaitResultReceiver";

    public static final int WAIT_INTERVAL = 1000;
    public static final int WAIT_LONG = 1000;
    public static final int WAIT_MEDIUM = 300;
    public static final int WAIT_VERY_LONG = 500;

    private final WaitResultReceiverListener listener;

    public WaitResultManager(long millisInFuture, long countDownInterval, WaitResultReceiverListener listener) {
        super(millisInFuture, countDownInterval);
        this.listener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
    }

    @Override
    public void onFinish() {
        listener.onFinished();
    }

    public interface WaitResultReceiverListener {
        void onFinished();
    }
}
