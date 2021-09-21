package com.sti.research.personalsafetyalert.ui.screen.message;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;

import javax.inject.Inject;

public class MessageFragmentViewModel extends ViewModel {

    private final MessageRepository messageRepository;
    private final MainSharedRepository sharedRepository;

    @Inject
    public MessageFragmentViewModel(
            MessageRepository messageRepository,
            MainSharedRepository sharedRepository) {
        this.messageRepository = messageRepository;
        this.sharedRepository = sharedRepository;
    }

    public void insertMessageDatabase(Message message) {
        this.messageRepository.insert(message);
    }

    public void setAddMessage(String message) {
        this.sharedRepository.setAddMessage(message);
    }

}
