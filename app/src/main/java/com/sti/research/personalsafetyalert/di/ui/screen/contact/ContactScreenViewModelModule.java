package com.sti.research.personalsafetyalert.di.ui.screen.contact;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactFragmentViewModel.class)
    abstract ViewModel bindContactFragmentViewModel(ContactFragmentViewModel viewModel);

}
