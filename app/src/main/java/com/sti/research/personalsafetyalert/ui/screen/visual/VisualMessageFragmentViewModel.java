package com.sti.research.personalsafetyalert.ui.screen.visual;

import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.database.MessageRepository;
import com.sti.research.personalsafetyalert.repository.share.MainSharedRepository;

import javax.inject.Inject;

public class VisualMessageFragmentViewModel extends ViewModel {

    private final MessageRepository messageRepository;
    private final MainSharedRepository sharedRepository;

    @Inject
    public VisualMessageFragmentViewModel(
            MessageRepository messageRepository,
            MainSharedRepository sharedRepository) {
        this.messageRepository = messageRepository;
        this.sharedRepository = sharedRepository;
    }

    public void insertMessage(Message message) {
        this.messageRepository.insert(message);
    }

    public String getCurrentSelectedMessage() {
        return this.sharedRepository.getSelectedMessage();
    }

}
