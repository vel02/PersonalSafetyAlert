package com.sti.research.personalsafetyalert.di.ui;

import com.sti.research.personalsafetyalert.di.ui.screen.contact.ContactScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.contact.ContactScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.contact.add.AddContactScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.contact.add.AddContactScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.contact.update.UpdateContactScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.contact.update.UpdateContactScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.home.HomeScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.home.HomeScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.message.MessageScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.message.MessageScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.permission.PermissionScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.permission.PermissionScreenViewModelModule;
import com.sti.research.personalsafetyalert.di.ui.screen.visual.VisualMessageScreenScope;
import com.sti.research.personalsafetyalert.di.ui.screen.visual.VisualMessageScreenViewModelModule;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragment;
import com.sti.research.personalsafetyalert.ui.screen.contact.add.AddContactFragment;
import com.sti.research.personalsafetyalert.ui.screen.contact.add.AddContactFragmentViewModel;
import com.sti.research.personalsafetyalert.ui.screen.contact.update.UpdateContactFragment;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragment;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragment;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragment;
import com.sti.research.personalsafetyalert.ui.screen.visual.VisualMessageFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @HomeScreenScope
    @ContributesAndroidInjector(modules = {HomeScreenViewModelModule.class})
    abstract HomeFragment contributeHomeFragment();

    @ContactScreenScope
    @ContributesAndroidInjector(modules = {ContactScreenViewModelModule.class})
    abstract ContactFragment contributeContactFragment();

    @AddContactScreenScope
    @ContributesAndroidInjector(modules = {AddContactScreenViewModelModule.class})
    abstract AddContactFragment contributeAddContactFragment();

    @UpdateContactScreenScope
    @ContributesAndroidInjector(modules = {UpdateContactScreenViewModelModule.class})
    abstract UpdateContactFragment contributeUpdateContactFragment();

    @MessageScreenScope
    @ContributesAndroidInjector(modules = {MessageScreenViewModelModule.class})
    abstract MessageFragment contributeMessageFragment();

    @VisualMessageScreenScope
    @ContributesAndroidInjector(modules = {VisualMessageScreenViewModelModule.class})
    abstract VisualMessageFragment contributeVisualMessageFragment();

    @PermissionScreenScope
    @ContributesAndroidInjector(modules = {PermissionScreenViewModelModule.class})
    abstract PermissionFragment contributePermissionFragment();

}
