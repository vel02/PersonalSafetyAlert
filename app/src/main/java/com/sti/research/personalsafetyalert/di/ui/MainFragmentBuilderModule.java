package com.sti.research.personalsafetyalert.di.ui;

import com.sti.research.personalsafetyalert.di.ui.screen.home.HomeScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.home.HomeScreenViewModelModule;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @HomeScreenScope
    @ContributesAndroidInjector(modules = {
            HomeScreenViewModelModule.class
    })
    abstract HomeFragment contributeHomeFragment();

}
