package com.sti.research.personalsafetyalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.sti.research.personalsafetyalert.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    public static final String NOTIFICATION_LOCATION_CHANNEL_ID = "com.sti.research.personalsafetyalert.CHANNEL_LOCATION";
    public static final String NOTIFICATION_LOCATION_CHANNEL_NAME = "Location Update";

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
            channel_location.setDescription("User present location");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel_location);
        }
    }
}
