package com.sti.research.personalsafetyalert.di.ui.welcome;

import com.sti.research.personalsafetyalert.di.ui.welcome.screen.WelcomeFragmentScope;
import com.sti.research.personalsafetyalert.di.ui.welcome.screen.WelcomeFragmentViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.welcome.screen.WelcomeViewPagerScreenScope;
import com.sti.research.personalsafetyalert.ui.welcome.screen.WelcomeFragment;
import com.sti.research.personalsafetyalert.ui.welcome.screen.pager.WelcomeFragmentViewPager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WelcomeFragmentBuilderModule {

    @WelcomeFragmentScope
    @ContributesAndroidInjector(modules = {WelcomeFragmentViewModelModule.class})
    abstract WelcomeFragment contributeWelcomeFragmentFragment();

    @WelcomeViewPagerScreenScope
    @ContributesAndroidInjector
    abstract WelcomeFragmentViewPager contributeWelcomeFragmentViewPager();

}
