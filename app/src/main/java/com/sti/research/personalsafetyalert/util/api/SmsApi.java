/*
 *
 *   Created by Austria, Ariel Namias on 2/15/21 8:57 AM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ 2021 https://github.com/vel02
 *   Last modified: 2/15/21 8:02 AM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.sti.research.personalsafetyalert.util.api;

import android.app.PendingIntent;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmsApi {

    private static final String TAG = "Sms";

    public static final int SLOT_SIM_ONE = 0;
    public static final int SLOT_SIM_TWO = 1;

    private static final ArrayList<PendingIntent> sentPendingIntent = new ArrayList<>();
    private static final ArrayList<PendingIntent> deliveredPendingIntent = new ArrayList<>();

    private static final String DEFAULT_MESSAGE = "\n\nGoogle Map Location: https://www.google.com/maps/search/?api=1&query=";

    private static final int MAX_SMS_MESSAGE_LENGTH = 160;

    private final SmsManager manager;
    private List localList;

    private final double longitude;
    private final double latitude;

    private static SmsApi instance;

    private SmsApi(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.manager = SmsManager.getDefault();
    }

    private SmsApi(List localList, double latitude, double longitude) {
        this.localList = localList;
        this.latitude = latitude;
        this.longitude = longitude;
        this.manager = SmsManager.getDefault();
    }

    public static SmsApi getInstance() {
        if (instance == null)
            instance = new SmsApi(0, 0);
        return instance;
    }

    public static SmsApi getInstance(double latitude, double longitude) {
        if (instance == null)
            instance = new SmsApi(latitude, longitude);
        return instance;
    }

    public static SmsApi getInstance(List localList, double latitude, double longitude) {
        if (instance == null)
            instance = new SmsApi(localList, latitude, longitude);
        return instance;
    }

    public void sendTo(String destination, String userMessage, PendingIntent sentPI, PendingIntent deliveredPI) {
        Log.d(TAG, "SMS API: SINGLE SIM USED.");
        final String separator = ",";
        final String message = userMessage + DEFAULT_MESSAGE + latitude + separator + longitude;

        List<String> numbers;
        if (destination.contains(",")) {
            numbers = Arrays.asList(destination.split(","));
            for (String number : numbers) {
                sendTextMessage(message, number, sentPI, deliveredPI);
            }
        } else {
            sendTextMessage(message, destination, sentPI, deliveredPI);
        }
    }

    public void sendTo(String destination, String userMessage, int slot, PendingIntent sentPI, PendingIntent deliveredPI) {
        Log.d(TAG, "SMS API: DUAL SIM USED.");
        final String separator = ",";
        final String message = userMessage + DEFAULT_MESSAGE + latitude + separator + longitude;

        List<String> numbers;
        if (destination.contains(",")) {
            numbers = Arrays.asList(destination.split(","));
            for (String number : numbers) {
                sendTextMessage(message, number, slot, sentPI, deliveredPI);
            }
        } else {
            sendTextMessage(message, destination, slot, sentPI, deliveredPI);
        }
    }


    public void sendToMany(String destination, String userMessage, PendingIntent sentPI, PendingIntent deliveredPI) {
        Log.d(TAG, "SMS API (SEND TO MANY): SINGLE SIM USED.");

        final String separator = ",";
        final String message = userMessage + DEFAULT_MESSAGE + latitude + separator + longitude;

        List<String> numbers;
        if (destination.contains(",")) {
            numbers = Arrays.asList(destination.split(","));
            for (String number : numbers) {
                sendTextMessage(message, number, sentPI, deliveredPI);
            }
        } else {
            sendTextMessage(message, destination, sentPI, deliveredPI);
        }
    }

    public void sendToMany(String destination, String userMessage, int slot, PendingIntent sentPI, PendingIntent deliveredPI) {
        Log.d(TAG, "SMS API (SEND TO MANY): DUAL SIM USED.");

        final String separator = ",";
        final String message = userMessage + DEFAULT_MESSAGE + latitude + separator + longitude;

        List<String> numbers;
        if (destination.contains(",")) {
            numbers = Arrays.asList(destination.split(","));
            for (String number : numbers) {
                sendTextMessage(message, number, slot, sentPI, deliveredPI);
            }
        } else {
            sendTextMessage(message, destination, slot, sentPI, deliveredPI);
        }
    }


    //single sim
    private void sendTextMessage(String message, String destination, PendingIntent sentPI, PendingIntent deliveredPI) {
        Log.d(TAG, "SMS API: SINGLE SIM USED.");
        int length = message.length();
        if (length > MAX_SMS_MESSAGE_LENGTH) {
            Log.d(TAG, "SMS API: MULTIPART MESSAGING USED CURRENT LENGTH (" + length + ").");

            try {
                ArrayList<String> longMessage = manager.divideMessage(message);
                for (int i = 0; i < longMessage.size(); i++) {
                    sentPendingIntent.add(i, sentPI);
                    deliveredPendingIntent.add(i, deliveredPI);
                }
                manager.sendMultipartTextMessage(destination, null, longMessage, sentPendingIntent, deliveredPendingIntent);

            } catch (Exception exception) {
                Log.d(TAG, "SMS API: MULTIPART MESSAGING SENDING FAILED.");
            }

        } else {
            Log.d(TAG, "SMS API: DEFAULT MESSAGING USED CURRENT LENGTH (" + length + ").");

            try {
                manager.sendTextMessage(destination, null, message, sentPI, deliveredPI);
            } catch (Exception exception) {
                Log.d(TAG, "SMS API: DEFAULT MESSAGING SENDING FAILED.");
            }

        }
    }

    //dual sim
    private void sendTextMessage(String message, String destination, int slot, PendingIntent sentPI, PendingIntent deliveredPI) {
        SubscriptionInfo simInfo = (SubscriptionInfo) localList.get(slot);
        Log.d(TAG, "SMS API: SIM CARD INFO " + simInfo.getNumber());

        int length = message.length();
        if (length > MAX_SMS_MESSAGE_LENGTH) {
            Log.d(TAG, "SMS API: MULTIPART MESSAGING USED CURRENT LENGTH (" + length + ").");

            try {
                ArrayList<String> longMessage = manager.divideMessage(message);
                for (int i = 0; i < longMessage.size(); i++) {
                    sentPendingIntent.add(i, sentPI);
                    deliveredPendingIntent.add(i, deliveredPI);
                }
                SmsManager.getSmsManagerForSubscriptionId(simInfo.getSubscriptionId())
                        .sendMultipartTextMessage(destination, null, longMessage, sentPendingIntent, deliveredPendingIntent);
            } catch (Exception exception) {
                Log.d(TAG, "SMS API: MULTIPART MESSAGING SENDING FAILED.");
            }

        } else {
            Log.d(TAG, "SMS API: DEFAULT MESSAGING USED CURRENT LENGTH (" + length + ").");

            try {
                SmsManager.getSmsManagerForSubscriptionId(simInfo.getSubscriptionId())
                        .sendTextMessage(destination, null, message, sentPI, deliveredPI);
            } catch (Exception exception) {
                Log.d(TAG, "SMS API: DEFAULT MESSAGING SENDING FAILED.");
            }

        }
    }

    public void requestLoad(String destination) {
        //if destination is globe
        //number is 3733, loan keyword is LOAN LOAD5
        String number = "3733";
        manager.sendTextMessage(number, null, "LOAN LOAD10", null, null);
        Log.d(TAG, "SMS API: REQUESTING A LOAD.");
    }

}
