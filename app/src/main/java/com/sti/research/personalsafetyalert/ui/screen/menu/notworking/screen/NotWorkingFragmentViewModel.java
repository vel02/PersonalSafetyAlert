package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class NotWorkingFragmentViewModel extends ViewModel {

    private final MutableLiveData<Integer> selectedTabPosition;

    @Inject
    public NotWorkingFragmentViewModel() {
        this.selectedTabPosition = new MutableLiveData<>();
        this.selectedTabPosition.setValue(0);
    }

    public void setSelectedTabPosition(Integer position) {
        this.selectedTabPosition.setValue(position);
    }

    public LiveData<Integer> observedSelectedTabPosition() {
        return this.selectedTabPosition;
    }

}
