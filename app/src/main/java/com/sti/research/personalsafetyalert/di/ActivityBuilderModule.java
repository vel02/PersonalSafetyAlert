package com.sti.research.personalsafetyalert.di;

import com.sti.research.personalsafetyalert.di.ui.MainFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.MainScope;
import com.sti.research.personalsafetyalert.di.ui.MainViewModelModule;
import com.sti.research.personalsafetyalert.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentBuilderModule.class,
            MainViewModelModule.class
    })
    abstract MainActivity contributeMainActivity();


}
