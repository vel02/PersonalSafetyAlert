package com.sti.research.personalsafetyalert.ui.screen.permission;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository.RequiredPermissionsState;

import javax.inject.Inject;

public class PermissionFragmentViewModel extends ViewModel {

    private final PermissionRepository repository;

    @Inject
    public PermissionFragmentViewModel(PermissionRepository repository) {
        this.repository = repository;
    }

    //############# Permission Required State #############

    public void setPermissionRequiredState(RequiredPermissionsState state) {
        this.repository.setPermissionsRequiredStatus(state);
    }

    public LiveData<RequiredPermissionsState> observedPermissionRequiredState() {
        return this.repository.observedPermissionRequiredState();
    }

    //############# Permission Send SMS State #############

    public void setPermissionSendSMSState(int permission) {
        this.repository.setPermissionSendSMS(permission);
    }

    public LiveData<Integer> observedPermissionSendSMSState() {
        return this.repository.observedPermissionSendSMSState();
    }

    //############# Permission Location State #############

    public void setPermissionLocationState(int permission) {
        this.repository.setPermissionLocationState(permission);
    }

    public LiveData<Integer> observedPermissionLocationState() {
        return this.repository.observedPermissionLocationState();
    }

}
