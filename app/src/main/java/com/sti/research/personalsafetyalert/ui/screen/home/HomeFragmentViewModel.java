package com.sti.research.personalsafetyalert.ui.screen.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

import javax.inject.Inject;

public class HomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> alertChecked;

    private final Application application;

    @Inject
    public HomeFragmentViewModel(Application application) {
        this.application = application;
        this.alertChecked = new MutableLiveData<>();
    }

    public void setAlertChecked(boolean checked) {
        this.alertChecked.setValue(checked);
        HomeSwitchPreference.getInstance().setSwitchStateState(application, checked);
    }

    public LiveData<Boolean> observedAlertChecked() {
        return this.alertChecked;
    }
}
