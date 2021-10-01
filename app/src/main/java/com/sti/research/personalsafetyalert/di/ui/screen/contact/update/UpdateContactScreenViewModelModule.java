package com.sti.research.personalsafetyalert.di.ui.screen.contact.update;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.contact.update.UpdateContactFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UpdateContactScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UpdateContactFragmentViewModel.class)
    abstract ViewModel bindUpdateContactFragmentViewModel(UpdateContactFragmentViewModel viewModel);

}
