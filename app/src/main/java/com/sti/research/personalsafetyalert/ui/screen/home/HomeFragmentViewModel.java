package com.sti.research.personalsafetyalert.ui.screen.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository.RequiredPermissionsState;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;

import javax.inject.Inject;

public class HomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> alertChecked;

    private final Application application;
    private final PermissionRepository repository;

    @Inject
    public HomeFragmentViewModel(Application application, PermissionRepository repository) {
        this.application = application;
        this.repository = repository;
        this.alertChecked = new MutableLiveData<>();
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
