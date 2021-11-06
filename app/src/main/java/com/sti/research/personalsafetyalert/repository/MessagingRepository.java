package com.sti.research.personalsafetyalert.repository;

import android.util.Log;

import com.sti.research.personalsafetyalert.util.api.EmailManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.MessagingException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class MessagingRepository {

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public MessagingRepository() {
    }

    public void sendEmailWithAttachments(String subject, String body, String recipients, List<String> paths) {
        Observable.just(new Object()).subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.d("test", "PROCESSING");
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

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("test", "ERROR", e);
            }

            @Override
            public void onComplete() {
                Log.d("test", "DONE");
                disposable.clear();
            }
        });
    }

    public void sendEmailWithAttachments(String subject, String body, String recipients, String path, String filename) {
        Observable.just(new Object()).subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.d("test", "PROCESSING");
                new Thread(() -> {
                    try {
                        EmailManager manager = new EmailManager();
                        manager.from();
                        manager.recipients(recipients);
                        manager.subject(subject);
                        manager.body(body);
                        manager.attachment(path, filename);
                        manager.send();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("test", "ERROR", e);
            }

            @Override
            public void onComplete() {
                Log.d("test", "DONE");
                disposable.clear();
            }
        });
    }

}
