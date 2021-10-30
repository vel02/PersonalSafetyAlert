/*
 *
 *   Created by Austria, Ariel Namias on 2/20/21 7:49 AM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ 2021 https://github.com/vel02
 *   Last modified: 2/20/21 7:08 AM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.sti.research.personalsafetyalert.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class DetectPowerClickedReceiver extends BroadcastReceiver {

    private static final String TAG = "DetectScreenOnReceiver";
    private static final int THRESHOLD = 3;

    private Vibrator vibrator;
    private PowerClickedReceiverCallback callback;

    private int clickCount = 0;
    private long startTimeSecond = 0;

    public void onScreenReceiverCallback(PowerClickedReceiverCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (vibrator == null)
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (startTimeSecond == 0) startTimeSecond = (System.currentTimeMillis() / 1000);
        int timelapse = 0;
        if (startTimeSecond != 0) {
            long time = (System.currentTimeMillis() / 1000);
            timelapse = (int) (time - startTimeSecond);

            if (timelapse > 2) {
                reset();
            }

        }


        Log.d(TAG, "onReceive: TIMELAPSE: " + timelapse);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "onReceive: SCREEN ON CALLED");
            clickCount++;

            Log.d(TAG, "onReceive: COUNT: " + clickCount);
            triggerPowerClickedReceiver(timelapse);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "onReceive: SCREEN OFF CALLED");
            clickCount++;

            Log.d(TAG, "onReceive: COUNT: " + clickCount);
            triggerPowerClickedReceiver(timelapse);

        }

    }

    private void triggerPowerClickedReceiver(int timelapse) {
        if (clickCount == THRESHOLD && timelapse < 3) {
            Log.d(TAG, "onReceive: ### TRIGGERED ###");
            callback.onTriggered();
            vibrate();
            reset();
        }
    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else vibrator.vibrate(500);
    }

    private void reset() {
        clickCount = 0;
        startTimeSecond = 0;
    }

    public interface PowerClickedReceiverCallback {
        void onTriggered();
    }
}
