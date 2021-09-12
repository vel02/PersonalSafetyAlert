package com.sti.research.personalsafetyalert.di.ui.screen.visual;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.visual.VisualMessageFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VisualMessageScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VisualMessageFragmentViewModel.class)
    abstract ViewModel bindVisualMessageFragmentViewModel(VisualMessageFragmentViewModel viewModel);

}
