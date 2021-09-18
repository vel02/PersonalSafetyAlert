package com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.contactus;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.di.ViewModelKey;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus.ContactUsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactUsFragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactUsViewModel.class)
    abstract ViewModel bindContactUsViewModel(ContactUsViewModel viewModel);

}
