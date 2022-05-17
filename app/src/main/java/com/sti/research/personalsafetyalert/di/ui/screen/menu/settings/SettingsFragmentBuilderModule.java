package com.sti.research.personalsafetyalert.di.ui.screen.menu.settings;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.screen.DashboardLogScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.screen.LogScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.screen.MobileUserScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.screen.SettingsScreenScope;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.DashboardLogFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.LogFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.MobileUserFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsFragmentBuilderModule {

    @SettingsScreenScope
    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();

    @DashboardLogScreenScope
    @ContributesAndroidInjector
    abstract DashboardLogFragment contributeDashboardLogFragment();

    @MobileUserScreenScope
    @ContributesAndroidInjector
    abstract MobileUserFragment contributeMobileUserFragment();

    @LogScreenScope
    @ContributesAndroidInjector
    abstract LogFragment contributeLogFragment();

}
