package com.sti.research.personalsafetyalert.ui;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.PermissionRepository;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final PermissionRepository repository;

    @Inject
    public MainViewModel(PermissionRepository repository) {
        this.repository = repository;
    }

    public void setPermissionLocationState(int permission) {
        this.repository.setPermissionLocationState(permission);
    }

    public void setPermissionSendSMSState(int permission) {
        this.repository.setPermissionSendSMSState(permission);
    }

    public void setPermissionRecordAudioState(int permission) {
        this.repository.setPermissionRecordAudioState(permission);
    }

    public void setPermissionStorageState(int permission) {
        this.repository.setPermissionStorageState(permission);
    }

}
