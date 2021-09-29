package com.sti.research.personalsafetyalert.repository.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.persistence.contact.ContactDatabase;
import com.sti.research.personalsafetyalert.persistence.contact.executor.ContactExecutorDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactRepository {

    private final MediatorLiveData<List<Contact>> contacts;

    private final ContactDatabase database;

    @Inject
    public ContactRepository(ContactDatabase database) {
        this.database = database;
        this.contacts = new MediatorLiveData<>();
    }

    public void insert(Contact contact) {
        ContactExecutorDatabase.insert(database, contact);
    }

    public void update(Contact contact) {
        ContactExecutorDatabase.update(database, contact);
    }

    public void deleteAll() {
        ContactExecutorDatabase.deleteAll(database);
    }

    public void select() {
        final LiveData<List<Contact>> source = database.getContactDao().selectContacts();

        this.contacts.addSource(source, contactList -> {
            if (contactList != null) {
                ContactRepository.this.contacts.setValue(contactList);
            }
            ContactRepository.this.contacts.removeSource(source);
        });
    }

    public LiveData<List<Contact>> observedContacts() {
        return this.contacts;
    }

}
