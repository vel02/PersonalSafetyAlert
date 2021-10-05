package com.sti.research.personalsafetyalert.di.ui.screen.home.dialog;

import com.sti.research.personalsafetyalert.ui.screen.home.dialog.DialogInvalidContactFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeDialogBuilderModule {

    @DialogInvalidContactScope
    @ContributesAndroidInjector()
    abstract DialogInvalidContactFragment contributeDialogInvalidContactFragment();

}
