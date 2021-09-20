package com.sti.research.personalsafetyalert.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sti.research.personalsafetyalert.model.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "message_db";

    public abstract MessageDao getMessageDao();

}
