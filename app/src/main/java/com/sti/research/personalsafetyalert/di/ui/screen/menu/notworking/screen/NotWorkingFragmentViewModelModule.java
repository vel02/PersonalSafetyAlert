package com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.NotWorkingFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NotWorkingFragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NotWorkingFragmentViewModel.class)
    abstract ViewModel bindNotWorkingViewModel(NotWorkingFragmentViewModel viewModel);

}
