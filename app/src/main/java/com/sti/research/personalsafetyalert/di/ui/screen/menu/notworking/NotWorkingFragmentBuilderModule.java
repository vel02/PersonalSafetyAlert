package com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingViewPagerScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingFragmentViewModelModule;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.NotWorkingFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotWorkingFragmentBuilderModule {

    @NotWorkingScreenScope
    @ContributesAndroidInjector(modules = {NotWorkingFragmentViewModelModule.class})
    abstract NotWorkingFragment contributeNotWorkingFragment();

    @NotWorkingViewPagerScreenScope
    @ContributesAndroidInjector
    abstract NotWorkingFragmentViewPager contributeNotWorkingFragmentViewPager();

}
