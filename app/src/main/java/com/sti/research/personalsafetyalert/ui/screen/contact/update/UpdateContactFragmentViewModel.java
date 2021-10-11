package com.sti.research.personalsafetyalert.ui.screen.contact.update;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.repository.database.ContactRepository;

import javax.inject.Inject;

public class UpdateContactFragmentViewModel extends ViewModel {

    private final ContactRepository contactRepository;

    @Inject
    public UpdateContactFragmentViewModel(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void updateContact(Contact contact) {
        this.contactRepository.update(contact);
    }

    public void deleteContact(Contact contact) {
        this.contactRepository.delete(contact);
    }

}
