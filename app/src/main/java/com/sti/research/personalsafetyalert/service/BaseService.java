package com.sti.research.personalsafetyalert.service;

import android.app.Service;
import android.content.res.Configuration;

public abstract class BaseService extends Service {

    protected static final String APPLICATION_PACKAGE = "com.sti.research.personalsafetyalert.service";
    protected static final String EXTRA_STARTED_FROM_NOTIFICATION = APPLICATION_PACKAGE + ".started_from_notification";

    protected static final int LOCATION_REQUEST_UPDATE_INTERVAL = 10000;
    protected static final int LOCATION_REQUEST_UPDATE_FAST_INTERVAL = LOCATION_REQUEST_UPDATE_INTERVAL / 2;
    protected static final int LOCATION_REQUEST_MAX_WAIT_TIME = LOCATION_REQUEST_UPDATE_INTERVAL * 5;

    protected static final int NOTIFICATION_LOCATION_UPDATE_ID = 1;
    protected static final int NOTIFICATION_GPS_ID = 2;
    protected static final int NOTIFICATION_USER_SEND_ACTIVATION_ID = 3;

    protected ConfigurationChangeStatus status;

    public BaseService() {
        this.status = new ConfigurationChangeStatus();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.status.onConfigChange();
    }
}
