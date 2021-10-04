package com.sti.research.personalsafetyalert.ui.screen.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository.RequiredPermissionsState;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

import java.util.List;

import javax.inject.Inject;

public class HomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> alertChecked;
    private final MutableLiveData<Message> message;
    private final MutableLiveData<LocationServiceState> locationServiceState;


    private final Application application;
    private final PermissionRepository permissionRepository;
    private final MessageRepository messageRepository;
    private final MainSharedRepository sharedRepository;


    @Inject
    public HomeFragmentViewModel(Application application,
                                 PermissionRepository permissionRepository,
                                 MessageRepository messageRepository,
                                 MainSharedRepository sharedRepository) {
        this.application = application;
        this.permissionRepository = permissionRepository;
        this.messageRepository = messageRepository;
        this.sharedRepository = sharedRepository;
        this.alertChecked = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
        this.locationServiceState = new MutableLiveData<>();
    }

    //#############  Switch State #############

    public void setLocationServiceState(LocationServiceState state) {
        this.locationServiceState.setValue(state);
    }

    public LiveData<LocationServiceState> observedLocationServiceState() {
        return this.locationServiceState;
    }


    //############# Main Shared Repository #############

    public void setSelectedMessage(String message) {
        this.sharedRepository.setSelectedMessage(message);
    }

    //############# Message Database Repository #############

    public void setMessage(Message message) {
        this.message.setValue(message);
    }

    public LiveData<Message> observedMessage() {
        return this.message;
    }

    //############# Message Database Repository #############

    public void loadMessagesDatabase() {
        this.messageRepository.select();
    }

    public void insert(Message message) {
        this.messageRepository.insert(message);
    }

    public void insert(List<Message> messages) {
        this.messageRepository.insert(messages);
    }

    public void update(Message message) {
        this.messageRepository.update(message);
    }

    public void deleteAll() {
        this.messageRepository.deleteAll();
    }

    public LiveData<List<Message>> observedMessages() {
        return this.messageRepository.observedMessages();
    }

    //############# Permission Required State #############

    public void setPermissionRequiredState(RequiredPermissionsState state) {
        this.permissionRepository.setPermissionsRequiredStatus(state);
    }

    public LiveData<RequiredPermissionsState> observedPermissionRequiredState() {
        return this.permissionRepository.observedPermissionRequiredState();
    }

    public void setAlertChecked(boolean checked) {
        this.alertChecked.setValue(checked);
        HomeSwitchPreference.getInstance().setSwitchStateState(application, checked);
    }

    public LiveData<Boolean> observedAlertChecked() {
        return this.alertChecked;
    }

    public enum LocationServiceState {ACTIVATE_ON, ACTIVATE_OFF}

}
