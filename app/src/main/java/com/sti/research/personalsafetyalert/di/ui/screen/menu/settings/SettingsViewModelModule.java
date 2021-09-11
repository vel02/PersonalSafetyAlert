package com.sti.research.personalsafetyalert.di.ui.screen.menu.settings;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel viewModel);

}
