package com.sti.research.personalsafetyalert.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;
import com.sti.research.personalsafetyalert.util.Constants;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Constants.TransitionType> transitionType;

    private final PermissionRepository permissionRepository;
    private final MainSharedRepository sharedRepository;

    @Inject
    public MainViewModel(PermissionRepository permissionRepository, MainSharedRepository sharedRepository) {
        this.permissionRepository = permissionRepository;
        this.sharedRepository = sharedRepository;
        this.transitionType = new MutableLiveData<>();
    }

    public String getSelectedMessage() {
        return this.sharedRepository.getSelectedMessage();
    }

    public String getAddMessage() {
        return this.sharedRepository.getAddMessage();
    }

    public void setPermissionLocationState(int permission) {
        this.permissionRepository.setPermissionLocationState(permission);
    }

    public void setPermissionSendSMSState(int permission) {
        this.permissionRepository.setPermissionSendSMSState(permission);
    }

    public void setPermissionRecordAudioState(int permission) {
        this.permissionRepository.setPermissionRecordAudioState(permission);
    }

    public void setPermissionStorageState(int permission) {
        this.permissionRepository.setPermissionStorageState(permission);
    }

    public void setTransitionType(Constants.TransitionType type) {
        this.transitionType.setValue(type);
    }

    public LiveData<Constants.TransitionType> observedTransitionType() {
        return this.transitionType;
    }

}
