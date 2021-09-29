package com.sti.research.personalsafetyalert.ui.screen.contact.add;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.repository.database.ContactRepository;

import javax.inject.Inject;

public class AddContactFragmentViewModel extends ViewModel {

    private final ContactRepository contactRepository;

    @Inject
    public AddContactFragmentViewModel(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void insertContact(Contact contact) {
        this.contactRepository.insert(contact);
    }

}
