package com.sti.research.personalsafetyalert.repository;

import android.content.pm.PackageManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PermissionRepository {

    private final MutableLiveData<Integer> permissionLocation;

    @Inject
    public PermissionRepository() {
        this.permissionLocation = new MutableLiveData<>();
        setPermissionLocationState(PackageManager.PERMISSION_DENIED);
    }

    public void setPermissionLocationState(int permission) {
        this.permissionLocation.setValue(permission);
    }

    public LiveData<Integer> observedPermissionLocationState() {
        return this.permissionLocation;
    }

}
