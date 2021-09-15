package com.sti.research.personalsafetyalert.repository;

import android.content.pm.PackageManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PermissionRepository {

    private final MutableLiveData<RequiredPermissionsState> permissionsRequiredStatus;
    private final MutableLiveData<Integer> permissionLocation;
    private final MutableLiveData<Integer> permissionSendSMS;

    @Inject
    public PermissionRepository() {
        this.permissionsRequiredStatus = new MutableLiveData<>();
        this.permissionLocation = new MutableLiveData<>();
        this.permissionSendSMS = new MutableLiveData<>();
        setPermissionsRequiredStatus(RequiredPermissionsState.NONE);
        setPermissionLocationState(PackageManager.PERMISSION_DENIED);
        setPermissionSendSMS(PackageManager.PERMISSION_DENIED);
    }

    //############# Permission Required State #############

    public void setPermissionsRequiredStatus(RequiredPermissionsState status) {
        this.permissionsRequiredStatus.setValue(status);
    }

    public LiveData<RequiredPermissionsState> observedPermissionRequiredState() {
        return this.permissionsRequiredStatus;
    }

    //############# Permission Send SMS State #############

    public void setPermissionSendSMS(int permission) {
        this.permissionSendSMS.setValue(permission);
    }

    public LiveData<Integer> observedPermissionSendSMSState() {
        return this.permissionSendSMS;
    }

    //############# Permission Location State #############

    public void setPermissionLocationState(int permission) {
        this.permissionLocation.setValue(permission);
    }

    public LiveData<Integer> observedPermissionLocationState() {
        return this.permissionLocation;
    }

    public enum RequiredPermissionsState {PARTIAL, COMPLETED, NONE}

}
