package com.sti.research.personalsafetyalert.ui.screen.permission;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.PermissionRepository;

import javax.inject.Inject;

public class PermissionFragmentViewModel extends ViewModel {

    private final PermissionRepository repository;

    @Inject
    public PermissionFragmentViewModel(PermissionRepository repository) {
        this.repository = repository;
    }

    public void setPermissionState(int permission) {
        this.repository.setPermissionLocationState(permission);
    }

    public LiveData<Integer> observedPermissionState() {
        return this.repository.observedPermissionLocationState();
    }

}
