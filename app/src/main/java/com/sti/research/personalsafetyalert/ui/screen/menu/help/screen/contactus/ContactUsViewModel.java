package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus;

import static com.sti.research.personalsafetyalert.util.Constants.*;

import android.net.Uri;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.repository.MessagingRepository;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.screen.permission.MobileUserIDPreference;
import com.sun.mail.imap.protocol.UID;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

public class ContactUsViewModel extends ViewModel {

    private static final String TAG = "IMAGES";


    private final MutableLiveData<ScreenshotSlot> screenshotSlot;
    private final MutableLiveData<String> videoUri = new MutableLiveData<>();
    private final MutableLiveData<String> imageUri = new MutableLiveData<>();

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

    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }


    private String uriImage = "";

    public void writeUserVideo(View view, Uri uri) {
        final String FIREBASE_IMAGE_STORAGE = "videos/user";

        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        StorageReference riversRef = reference.child(FIREBASE_IMAGE_STORAGE + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"
                + MobileUserIDPreference.getInstance().getMobileUserIDPreference(view.getContext()) + "/video_" + System.currentTimeMillis());
        UploadTask uploadTask = riversRef.putFile(uri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                Task<Uri> firebaseURL = taskSnapshot.getStorage().getDownloadUrl();
                firebaseURL.addOnSuccessListener(uri -> {
                    Log.e(TAG, "writeUserVideo: URI " + uri);
                    videoUri.setValue(String.valueOf(uri));

                });

            }
        });
    }

    public LiveData<String> getVideoUri() {
        return videoUri;
    }

    public void writeUserPhoto(View view, Uri file) {
        final String FIREBASE_IMAGE_STORAGE = "images/user";

        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"
                        + MobileUserIDPreference.getInstance().getMobileUserIDPreference(view.getContext()) + "/image_" + System.currentTimeMillis());


        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .setContentLanguage("en")
                .setCustomMetadata("Meta-data", "Images")
                .setCustomMetadata("location", "Philippines")
                .build();
        UploadTask uploadTask = reference.putFile(file, metadata);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> firebaseURL = taskSnapshot.getStorage().getDownloadUrl();
            firebaseURL.addOnSuccessListener(uri -> {
                Log.e(TAG, "writeUserPhoto: URI " + uri);
                this.imageUri.setValue(String.valueOf(uri));
            }).addOnFailureListener(e -> Log.d(TAG, "Could not upload photo"));
        }).addOnFailureListener(e -> Log.d(TAG, "Could not upload photo"))
                .addOnProgressListener(taskSnapshot -> {
                });

    }

    public LiveData<String> getImageUri() {
        return imageUri;
    }

    public enum ScreenshotSlot {
        SLOT_ONE,
        SLOT_TWO,
        SLOT_THREE
    }

}
