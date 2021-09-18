package com.sti.research.personalsafetyalert.repository;

import com.sti.research.personalsafetyalert.util.api.EmailManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.MessagingException;

@Singleton
public class MessagingRepository {

    @Inject
    public MessagingRepository() {
    }

    public void sendEmailWithAttachments(String subject, String body, String recipients, List<String> paths) {
        new Thread(() -> {
            try {
                EmailManager manager = new EmailManager();
                manager.from();
                manager.recipients(recipients);
                manager.subject(subject);
                manager.body(body);
                manager.attachments(paths, "screen_shot");
                manager.send();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
