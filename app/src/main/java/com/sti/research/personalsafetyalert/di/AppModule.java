package com.sti.research.personalsafetyalert.di;

import com.sti.research.personalsafetyalert.repository.MessagingRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static PermissionRepository providePermissionRepository() {
        return new PermissionRepository();
    }

    @Singleton
    @Provides
    static MessagingRepository provideMessagingRepository() {
        return new MessagingRepository();
    }

}
