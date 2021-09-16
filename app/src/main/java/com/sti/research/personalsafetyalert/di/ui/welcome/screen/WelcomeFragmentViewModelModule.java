package com.sti.research.personalsafetyalert.di.ui.welcome.screen;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.welcome.screen.WelcomeFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WelcomeFragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeFragmentViewModel.class)
    abstract ViewModel bindWelcomeFragmentViewModel(WelcomeFragmentViewModel viewModel);
}
