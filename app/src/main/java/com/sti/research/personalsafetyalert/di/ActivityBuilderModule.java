package com.sti.research.personalsafetyalert.di;

import com.sti.research.personalsafetyalert.di.ui.MainFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.MainScope;
import com.sti.research.personalsafetyalert.di.ui.MainViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.HelpFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.HelpScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.HelpViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.NotWorkingFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.NotWorkingScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.notworking.NotWorkingViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.SettingsFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.SettingsScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.SettingsViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.splash.SplashScope;
import com.sti.research.personalsafetyalert.di.ui.welcome.WelcomeFragmentBuilderModule;
import com.sti.research.personalsafetyalert.di.ui.welcome.WelcomeScope;
import com.sti.research.personalsafetyalert.di.ui.welcome.WelcomeViewModelModule;
import com.sti.research.personalsafetyalert.ui.MainActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.ui.splash.SplashActivity;
import com.sti.research.personalsafetyalert.ui.welcome.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @SplashScope
    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @WelcomeScope
    @ContributesAndroidInjector(modules = {
            WelcomeFragmentBuilderModule.class,
            WelcomeViewModelModule.class
    })
    abstract WelcomeActivity contributeWelcomeActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentBuilderModule.class,
            MainViewModelModule.class
    })
    abstract MainActivity contributeMainActivity();

    @SettingsScope
    @ContributesAndroidInjector(modules = {
            SettingsFragmentBuilderModule.class,
            SettingsViewModelModule.class
    })
    abstract SettingsActivity contributeSettingsActivity();

    @NotWorkingScope
    @ContributesAndroidInjector(modules = {
            NotWorkingFragmentBuilderModule.class,
            NotWorkingViewModelModule.class
    })
    abstract NotWorkingActivity contributeNotWorkingActivity();

    @HelpScope
    @ContributesAndroidInjector(modules = {
            HelpFragmentBuilderModule.class,
            HelpViewModelModule.class
    })
    abstract HelpActivity contributeHelpActivity();
}
