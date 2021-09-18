package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus;

import static com.sti.research.personalsafetyalert.util.Constants.*;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sti.research.personalsafetyalert.repository.MessagingRepository;
import com.sti.research.personalsafetyalert.util.Constants;

import java.util.List;

import javax.inject.Inject;

public class ContactUsViewModel extends ViewModel {

    private final MutableLiveData<ScreenshotSlot> screenshotSlot;

    private final MessagingRepository repository;

    @Inject
    public ContactUsViewModel(MessagingRepository repository) {
        this.repository = repository;
        this.screenshotSlot = new MutableLiveData<>();
    }

    public void sendEmailWithAttachments(String body, String recipients, boolean suggestions, List<String> paths) {
        String subject;
        if (suggestions) subject = MessagingManager.EMAIL_SUBJECT_WITH_SUGGESTION;
        else subject = MessagingManager.EMAIL_SUBJECT_WITHOUT_SUGGESTION;
        this.repository.sendEmailWithAttachments(subject, body, recipients, paths);
    }

    public void setScreenshotSlot(ScreenshotSlot slot) {
        this.screenshotSlot.setValue(slot);
    }

    public ScreenshotSlot getScreenshotSlot() {
        return this.screenshotSlot.getValue();
    }

    public enum ScreenshotSlot {
        SLOT_ONE,
        SLOT_TWO,
        SLOT_THREE
    }

}
