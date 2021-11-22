package com.sti.research.personalsafetyalert.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.repository.MessagingRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.database.ContactRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;
import com.sti.research.personalsafetyalert.util.Constants;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Constants.TransitionType> transitionType;

    private final MessagingRepository messagingRepository;
    private final PermissionRepository permissionRepository;
    private final MainSharedRepository sharedRepository;
    private final ContactRepository contactRepository;

    @Inject
    public MainViewModel(MessagingRepository messagingRepository, PermissionRepository permissionRepository,
                         MainSharedRepository sharedRepository,
                         ContactRepository contactRepository) {
        this.messagingRepository = messagingRepository;
        this.permissionRepository = permissionRepository;
        this.sharedRepository = sharedRepository;
        this.contactRepository = contactRepository;
        this.transitionType = new MutableLiveData<>();
    }

    public void sendEmail(String subject, String body, String recipient, String path, String filename) {
        this.messagingRepository.sendEmailWithAttachments(subject, body, recipient, path, filename);
    }

    public void sendEmailWithMaxDuration(String subject, String body, String recipient, String path, String filename) {
        this.messagingRepository.sendEmailWithAttachments(subject, body, recipient, path, filename);
    }

    public void loadContactList() {
        this.contactRepository.select();
    }

    public LiveData<List<Contact>> observedContacts() {
        return this.contactRepository.observedContacts();
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
