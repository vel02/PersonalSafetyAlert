package com.sti.research.personalsafetyalert.persistence.contact;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sti.research.personalsafetyalert.model.list.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);

    @Query("UPDATE Contacts SET name = :name, mobileNumber = :mobileNumber," +
            " mobileNetwork = :mobileNetwork, email = :email" +
            " WHERE id = :id")
    void updateContact(int id, String name, String mobileNumber,
                       String mobileNetwork, String email);

    @Query("DELETE FROM Contacts")
    void deleteContacts();

    @Query("SELECT * FROM Contacts ORDER BY id")
    LiveData<List<Contact>> selectContacts();

}
