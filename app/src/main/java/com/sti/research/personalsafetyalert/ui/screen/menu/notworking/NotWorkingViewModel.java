package com.sti.research.personalsafetyalert.ui.screen.menu.notworking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.util.Constants;

import javax.inject.Inject;

public class NotWorkingViewModel extends ViewModel {

    private MutableLiveData<Constants.TransitionType> transitionType;

    @Inject
    public NotWorkingViewModel() {
        this.transitionType = new MutableLiveData<>();
    }

    public void setTransitionType(Constants.TransitionType type) {
        this.transitionType.setValue(type);
    }

    public LiveData<Constants.TransitionType> observedTransitionType() {
        return this.transitionType;
    }

}
