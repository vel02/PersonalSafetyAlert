package com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingScreenScope;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.NotWorkingFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotWorkingFragmentBuilderModule {

    @NotWorkingScreenScope
    @ContributesAndroidInjector
    abstract NotWorkingFragment contributeNotWorkingFragment();

}
