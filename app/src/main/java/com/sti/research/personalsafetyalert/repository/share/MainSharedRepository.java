package com.sti.research.personalsafetyalert.repository.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sti.research.personalsafetyalert.model.Contact;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainSharedRepository {

    private final MutableLiveData<String> selectedMessage;
    private final MutableLiveData<String> addMessage;
    private final MutableLiveData<Contact> contactSinglePerson;

    @Inject
    public MainSharedRepository() {
        this.selectedMessage = new MutableLiveData<>();
        this.addMessage = new MutableLiveData<>();
        this.contactSinglePerson = new MutableLiveData<>();
    }

    public void setContactSinglePerson(Contact contact) {
        this.contactSinglePerson.setValue(contact);
    }

    public Contact getContactSinglePerson() {
        return this.contactSinglePerson.getValue();
    }

    public LiveData<Contact> observedContactSinglePerson() {
        return this.contactSinglePerson;
    }

    public void setAddMessage(String message) {
        this.addMessage.setValue(message);
    }

    public String getAddMessage() {
        return this.addMessage.getValue();
    }

    public void setSelectedMessage(String message) {
        this.selectedMessage.setValue(message);
    }

    public String getSelectedMessage() {
        return this.selectedMessage.getValue();
    }

}
