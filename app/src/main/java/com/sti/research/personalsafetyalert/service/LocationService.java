package com.sti.research.personalsafetyalert.service;

import static com.sti.research.personalsafetyalert.BaseApplication.NOTIFICATION_GPS_CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sti.research.personalsafetyalert.BaseApplication;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.ui.splash.SplashActivity;
import com.sti.research.personalsafetyalert.util.NetworkUtil;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

public class LocationService extends BaseService {

    private static final String TAG = "Service";

    private NotificationManager notificationManager;

    private boolean isCheckGPSConnectionReceiverRegistered;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        this.stopForeground(true);
        this.status.onConfigUnchanged();
        return new LocationUpdateBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        this.stopForeground(true);
        unregisterCheckGPSConnectionReceiver();
        this.status.onConfigUnchanged();
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (this.status.isConfigRemainUnchanged()) {
            this.startForeground(NOTIFICATION_LOCATION_UPDATE_ID, notificationLocation());
            registerCheckGPSConnectionReceiver();
        }
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean startedFromNotification =
                intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,
                        false);

        if (startedFromNotification) this.removeNotificationLocation();

        return START_NOT_STICKY;
    }

    public class LocationUpdateBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    public void requestNotificationLocation() {
        try {
            startService(new Intent(this, LocationService.class));
            HomeSwitchPreference.getInstance().setSwitchStateState(this, true);
        } catch (SecurityException unlikely) {
            HomeSwitchPreference.getInstance().setSwitchStateState(this, false);
            Log.e(TAG, "requestNotificationLocation: Lost location permission. " +
                    "Could not request updates." + unlikely);
        }
    }

    public void removeNotificationLocation() {
        try {
            this.stopSelf();
            HomeSwitchPreference.getInstance().setSwitchStateState(this, false);
        } catch (SecurityException unlikely) {
            HomeSwitchPreference.getInstance().setSwitchStateState(this, true);
            Log.e(TAG, "requestLocationUpdates: Lost location permission. " +
                    "Could not request updates." + unlikely);
        }
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private Notification notificationLocation() {

        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        PendingIntent servicePI = PendingIntent
                .getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent activityPI = PendingIntent
                .getActivity(this, 0,
                        new Intent(this, SplashActivity.class), 0);

        return new NotificationCompat.Builder(this, BaseApplication.NOTIFICATION_LOCATION_CHANNEL_ID)
                .addAction(R.drawable.ic_launch, "Launch",
                        activityPI)
                .addAction(R.drawable.ic_cancel, "Stop",
                        servicePI)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Present Location")
                .setContentText("location")
                .setPriority(Notification.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .build();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private Notification notificationConnection() {

        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);

        PendingIntent activityPendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NOTIFICATION_GPS_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Connection Interrupted")
                .setContentText("Please check your connection, turn it on to work properly.")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(activityPendingIntent)
                .setOngoing(true)
                .build();
    }


    private final BroadcastReceiver checkGPSConnectionReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())
                    || intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isInternetEnabled = NetworkUtil.getConnectivityStatusString(context);

                if (!isGpsEnabled || !isInternetEnabled) {
                    Log.d(TAG, "GPS AND INTERNET CONNECTION RECEIVER STATE: DISABLED.");
                    /*
                        When GPS location is disabled suddenly while app is running in the foreground,
                        it will stop the current notification update location in foreground, and
                        replace it with location settings notification to alert user about the
                        importance of activating GPS location to run the app properly.

                        The current location updates will automatically stop with
                        removeLocationUpdates() method.
                     */
                    stopForeground(true);
                    notificationManager.notify(NOTIFICATION_GPS_ID, notificationConnection());

                } else {
                    Log.d(TAG, "GPS AND INTERNET CONNECTION RECEIVER STATE: ENABLED.");
                    /*
                        While location notification settings is in a foreground to alert user
                        about reactivating GPS location, if the user chooses to enable the
                        location GPS in phone settings this will stop the notification, and service
                        to restart the process.
                     */
                    notificationManager.cancel(NOTIFICATION_GPS_ID);
                    startForeground(NOTIFICATION_LOCATION_UPDATE_ID, notificationLocation());

                }
            }
        }
    };

    private void registerCheckGPSConnectionReceiver() {
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(checkGPSConnectionReceiver, filter);
        isCheckGPSConnectionReceiverRegistered = true;
    }

    private void unregisterCheckGPSConnectionReceiver() {
        if (isCheckGPSConnectionReceiverRegistered)
            this.unregisterReceiver(checkGPSConnectionReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterCheckGPSConnectionReceiver();
    }
}
