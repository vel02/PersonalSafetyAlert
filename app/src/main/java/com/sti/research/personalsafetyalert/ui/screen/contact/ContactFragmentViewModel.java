package com.sti.research.personalsafetyalert.ui.screen.contact;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactMessageToPreference;

import javax.inject.Inject;

public class ContactFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> radioSelected;
    private final MainSharedRepository sharedRepository;

    private final Application application;

    @Inject
    public ContactFragmentViewModel(Application application, MainSharedRepository sharedRepository) {
        this.application = application;
        this.sharedRepository = sharedRepository;
        this.radioSelected = new MutableLiveData<>();
    }

    public void setRadioSelected(String selected) {
        this.radioSelected.setValue(selected);
        ContactMessageToPreference.getInstance().setMessageToRadioSelectState(application, selected);
    }

    public LiveData<String> observedRadioSelected() {
        return this.radioSelected;
    }
}
