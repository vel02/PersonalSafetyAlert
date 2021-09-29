package com.sti.research.personalsafetyalert.persistence.contact;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sti.research.personalsafetyalert.model.list.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "contact_db";

    public abstract ContactDao getContactDao();

}
