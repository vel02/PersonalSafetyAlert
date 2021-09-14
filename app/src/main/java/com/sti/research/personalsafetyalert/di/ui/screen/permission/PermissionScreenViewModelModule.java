package com.sti.research.personalsafetyalert.di.ui.screen.permission;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PermissionScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PermissionFragmentViewModel.class)
    abstract ViewModel bindPermissionFragmentViewModel(PermissionFragmentViewModel viewModel);

}
