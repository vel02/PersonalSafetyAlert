package com.sti.research.personalsafetyalert.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sti.research.personalsafetyalert.BaseApplication;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.ui.splash.SplashActivity;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

public class LocationService extends BaseService {

    private static final String TAG = "Service";

    @Override
    public void onCreate() {
        super.onCreate();
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
        this.status.onConfigUnchanged();
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (this.status.isConfigRemainUnchanged())
            this.startForeground(NOTIFICATION_LOCATION_UPDATE_ID, notifyLocation());
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
    private Notification notifyLocation() {

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

}
