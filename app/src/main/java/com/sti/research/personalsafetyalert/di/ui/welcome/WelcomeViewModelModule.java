package com.sti.research.personalsafetyalert.di.ui.welcome;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.welcome.WelcomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WelcomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel.class)
    abstract ViewModel bindWelcomeViewModel(WelcomeViewModel viewModel);

}
