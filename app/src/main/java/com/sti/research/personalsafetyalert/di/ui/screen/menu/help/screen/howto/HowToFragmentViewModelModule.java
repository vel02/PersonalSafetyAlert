package com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.howto;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.howto.HowToViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HowToFragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HowToViewModel.class)
    abstract ViewModel bindHowToViewModel(HowToViewModel viewModel);

}
