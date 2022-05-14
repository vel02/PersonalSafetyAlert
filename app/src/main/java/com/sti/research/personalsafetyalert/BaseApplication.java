package com.sti.research.personalsafetyalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.sti.research.personalsafetyalert.di.DaggerAppComponent;

import java.util.Arrays;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    public static final String NOTIFICATION_LOCATION_CHANNEL_ID = "com.sti.research.personalsafetyalert.CHANNEL_LOCATION";
    public static final String NOTIFICATION_LOCATION_CHANNEL_NAME = "Location Update";

    public static final String NOTIFICATION_GPS_CHANNEL_ID = "com.sti.research.personalsafetyalert.CHANNEL_GPS";
    public static final String NOTIFICATION_GPS_CHANNEL_NAME = "GPS Connection";

    public static final String NOTIFICATION_NOTIFY_USER_SEND_ACTIVATION_CHANNEL_ID = "com.sti.research.personalsafetyalert.CHANNEL_USER_SEND";
    public static final String NOTIFICATION_NOTIFY_USER_SEND_ACTIVATION_CHANNEL_NAME = "User send activation state";

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel_location = new NotificationChannel(NOTIFICATION_LOCATION_CHANNEL_ID,
                    NOTIFICATION_LOCATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel_location.setDescription("User present location.");

            NotificationChannel channel_gps = new NotificationChannel(NOTIFICATION_GPS_CHANNEL_ID,
                    NOTIFICATION_GPS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel_gps.setDescription("Remind GPS connection status.");

            NotificationChannel channel_notify_user = new NotificationChannel(NOTIFICATION_NOTIFY_USER_SEND_ACTIVATION_CHANNEL_ID,
                    NOTIFICATION_NOTIFY_USER_SEND_ACTIVATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel_gps.setDescription("Remind user sending activation succeeded.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannels(Arrays.asList(channel_location, channel_gps, channel_notify_user));
        }
    }
}
