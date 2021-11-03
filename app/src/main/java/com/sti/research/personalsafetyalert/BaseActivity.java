package com.sti.research.personalsafetyalert;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;

import com.sti.research.personalsafetyalert.service.LocationService;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity implements LocationService.LocationDataTransmitterListener {

    @Override
    public void onDataProcessing(Location location) {
        if (dataProcessingListener != null) {
            dataProcessingListener.onDataProcessing(location);
        }
    }

    protected final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.LocationUpdateBinder binder = (LocationService.LocationUpdateBinder) service;
            BaseActivity.this.locationService = binder.getService();
            BaseActivity.this.locationService.registerLocationDataTransmitter(BaseActivity.this);
            BaseActivity.this.isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            BaseActivity.this.locationService = null;
            BaseActivity.this.isServiceBound = false;
        }
    };

    protected LocationService locationService;
    protected boolean isServiceBound;
    protected OnDataProcessingListener dataProcessingListener;

    //============= Binder =============

    protected void bindLocationService() {
        bindService(new Intent(this, LocationService.class),
                connection, Context.BIND_AUTO_CREATE);
    }

    protected void unbindLocationService() {
        if (isServiceBound) {
            unbindService(connection);
            isServiceBound = false;
        }
    }

    //============= Interfaces =============

    public void registerDataProcessingListener(OnDataProcessingListener dataProcessingListener) {
        this.dataProcessingListener = dataProcessingListener;
    }

    public interface OnDataProcessingListener {
        void onDataProcessing(Location location);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.bindLocationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unbindLocationService();
    }

}
