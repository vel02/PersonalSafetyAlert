package com.sti.research.personalsafetyalert.di.ui.screen.menu.help;

import com.sti.research.personalsafetyalert.di.ui.screen.menu.help.screen.HelpScreenScope;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.HelpFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HelpFragmentBuilderModule {

    @HelpScreenScope
    @ContributesAndroidInjector
    abstract HelpFragment contributeHelpFragment();

}
