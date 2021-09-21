package com.sti.research.personalsafetyalert.repository.share;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainSharedRepository {

    private final MutableLiveData<String> selectedMessage;

    private final MutableLiveData<String> addMessage;

    @Inject
    public MainSharedRepository() {
        this.selectedMessage = new MutableLiveData<>();
        this.addMessage = new MutableLiveData<>();
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
