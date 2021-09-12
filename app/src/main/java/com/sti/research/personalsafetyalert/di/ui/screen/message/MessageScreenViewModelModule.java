package com.sti.research.personalsafetyalert.di.ui.screen.message;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MessageScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessageFragmentViewModel.class)
    abstract ViewModel bindMessageFragmentViewModel(MessageFragmentViewModel viewModel);

}
