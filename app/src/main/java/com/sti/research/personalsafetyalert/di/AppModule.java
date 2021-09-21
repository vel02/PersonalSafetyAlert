package com.sti.research.personalsafetyalert.di;

import android.app.Application;

import androidx.room.Room;

import com.sti.research.personalsafetyalert.persistence.MessageDatabase;
import com.sti.research.personalsafetyalert.repository.MessagingRepository;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;

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

    @Singleton
    @Provides
    static MainSharedRepository provideMainSharedRepository() {
        return new MainSharedRepository();
    }

    @Singleton
    @Provides
    static MessageDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(
                application, MessageDatabase.class,
                MessageDatabase.DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static MessageRepository provideMessageRepository(MessageDatabase database) {
        return new MessageRepository(database);
    }

}
