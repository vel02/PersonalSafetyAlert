package com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NotWorkingViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NotWorkingViewModel.class)
    abstract ViewModel bindNotWorkingViewModel(NotWorkingViewModel viewModel);

}
