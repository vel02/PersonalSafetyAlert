package com.sti.research.personalsafetyalert.di;

import androidx.lifecycle.ViewModelProvider;

import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory providerFactory);


}
