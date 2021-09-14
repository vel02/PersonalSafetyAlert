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

}
