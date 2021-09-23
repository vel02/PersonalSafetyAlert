package com.sti.research.personalsafetyalert.di.ui.screen.contact.add;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.contact.add.AddContactFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AddContactScreenViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddContactFragmentViewModel.class)
    abstract ViewModel bindAddContactFragmentViewModel(AddContactFragmentViewModel viewModel);

}
