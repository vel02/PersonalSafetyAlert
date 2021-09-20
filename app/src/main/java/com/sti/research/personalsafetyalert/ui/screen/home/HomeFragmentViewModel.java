package com.sti.research.personalsafetyalert.ui.screen.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository.RequiredPermissionsState;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

import java.util.List;

import javax.inject.Inject;

public class HomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> alertChecked;
    private final MutableLiveData<Message> message;

    private final Application application;
    private final PermissionRepository repository;
    private final MessageRepository databaseRepo;

    @Inject
    public HomeFragmentViewModel(Application application, PermissionRepository repository, MessageRepository databaseRepo) {
        this.application = application;
        this.repository = repository;
        this.databaseRepo = databaseRepo;
        this.alertChecked = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
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
        this.databaseRepo.select();
    }

    public void insert(Message message) {
        this.databaseRepo.insert(message);
    }

    public void insert(List<Message> messages) {
        this.databaseRepo.insert(messages);
    }

    public void update(Message message) {
        this.databaseRepo.update(message);
    }

    public void deleteAll() {
        this.databaseRepo.deleteAll();
    }

    public LiveData<List<Message>> observedMessages() {
        return this.databaseRepo.observedMessages();
    }

    //############# Permission Required State #############

    public void setPermissionRequiredState(RequiredPermissionsState state) {
        this.repository.setPermissionsRequiredStatus(state);
    }

    public LiveData<RequiredPermissionsState> observedPermissionRequiredState() {
        return this.repository.observedPermissionRequiredState();
    }

    public void setAlertChecked(boolean checked) {
        this.alertChecked.setValue(checked);
        HomeSwitchPreference.getInstance().setSwitchStateState(application, checked);
    }

    public LiveData<Boolean> observedAlertChecked() {
        return this.alertChecked;
    }
}
