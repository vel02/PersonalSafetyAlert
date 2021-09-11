package com.sti.research.personalsafetyalert.di.ui.screen.menu.settings;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.settings.screen.SettingsScreenScope;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsFragmentBuilderModule {

    @SettingsScreenScope
    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();

}
