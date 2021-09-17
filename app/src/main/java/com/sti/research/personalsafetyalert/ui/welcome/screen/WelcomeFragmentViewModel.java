package com.sti.research.personalsafetyalert.ui.welcome.screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class WelcomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<Integer> selectedTabPosition;


    @Inject
    public WelcomeFragmentViewModel() {
        this.selectedTabPosition = new MutableLiveData<>();
        setSelectedTabPosition(0);
    }

    public void setSelectedTabPosition(Integer position) {
        this.selectedTabPosition.setValue(position);
    }

    public LiveData<Integer> observedSelectedTabPosition() {
        return this.selectedTabPosition;
    }

}
