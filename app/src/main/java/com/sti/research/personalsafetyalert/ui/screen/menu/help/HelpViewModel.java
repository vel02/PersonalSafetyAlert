package com.sti.research.personalsafetyalert.ui.screen.menu.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.util.Constants;

import javax.inject.Inject;

public class HelpViewModel extends ViewModel {

    private MutableLiveData<Constants.TransitionType> transitionType;

    @Inject
    public HelpViewModel() {
        this.transitionType = new MutableLiveData<>();
    }

    public void setTransitionType(Constants.TransitionType type) {
        this.transitionType.setValue(type);
    }

    public LiveData<Constants.TransitionType> observedTransitionType() {
        return this.transitionType;
    }

}
