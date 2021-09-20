package com.sti.research.personalsafetyalert.repository.database;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.persistence.MessageDatabase;
import com.sti.research.personalsafetyalert.persistence.executor.MessageExecutorDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageRepository {

    private final MediatorLiveData<List<Message>> messages;

    private final MessageDatabase database;

    @Inject
    public MessageRepository(MessageDatabase database) {
        this.database = database;
        this.messages = new MediatorLiveData<>();
    }

    public void insert(Message message) {
        MessageExecutorDatabase.insert(database, message);
    }

    public void insert(List<Message> messages) {
        MessageExecutorDatabase.insert(database, messages);
    }

    public void update(Message message) {
        MessageExecutorDatabase.update(database, message);
    }

    public void deleteAll() {
        MessageExecutorDatabase.deleteAll(database);
    }

    public void select() {
        final LiveData<List<Message>> source = database.getMessageDao().selectMessages();

        this.messages.addSource(source, messageList -> {
            if (messageList != null) {
                MessageRepository.this.messages.setValue(messageList);
            }
            MessageRepository.this.messages.removeSource(source);
        });
    }

    public LiveData<List<Message>> observedMessages() {
        return this.messages;
    }

}
