package com.sti.research.personalsafetyalert.di.ui.screen.menu.help;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HelpViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HelpViewModel.class)
    abstract ViewModel bindHelpViewModel(HelpViewModel viewModel);

}
