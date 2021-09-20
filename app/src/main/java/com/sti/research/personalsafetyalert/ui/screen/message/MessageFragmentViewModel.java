package com.sti.research.personalsafetyalert.ui.screen.message;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;

import javax.inject.Inject;

public class MessageFragmentViewModel extends ViewModel {

    private final MessageRepository repository;

    @Inject
    public MessageFragmentViewModel(MessageRepository repository) {
        this.repository = repository;
    }

    public void insertMessageDatabase(Message message) {
        this.repository.insert(message);
    }

}
