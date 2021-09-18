package com.sti.research.personalsafetyalert.di.ui.screen.menu.help;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.HelpScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.contactus.ContactUsScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.contactus.ContactUsFragmentViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.howto.HowToFragmentViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.howto.HowToScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.notworking.NotWorkingScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingFragmentViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.screen.NotWorkingViewPagerScreenScope;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.HelpFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus.ContactUsFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.howto.HowToFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.NotWorkingFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HelpFragmentBuilderModule {

    @HelpScreenScope
    @ContributesAndroidInjector
    abstract HelpFragment contributeHelpFragment();

    @HowToScreenScope
    @ContributesAndroidInjector(modules = {
            HowToFragmentViewModelModule.class})
    abstract HowToFragment contributeHowToFragment();

    @ContactUsScreenScope
    @ContributesAndroidInjector(modules = {
            ContactUsFragmentViewModelModule.class})
    abstract ContactUsFragment contributeContactUsFragment();

    @NotWorkingScreenScope
    @ContributesAndroidInjector(modules = {
            NotWorkingFragmentViewModelModule.class})
    abstract NotWorkingFragment contributeNotWorkingFragment();

    @NotWorkingViewPagerScreenScope
    @ContributesAndroidInjector
    abstract NotWorkingFragmentViewPager contributeNotWorkingFragmentViewPager();

}
