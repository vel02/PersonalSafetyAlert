package com.sti.research.personalsafetyalert.persistence.contact.executor;

import android.util.Log;

import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.persistence.contact.ContactDatabase;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactExecutorDatabase {

    private static final String TAG = "database";

    private static final CompositeDisposable disposable = new CompositeDisposable();

    public static void insert(ContactDatabase database, Contact contact) {
        Observable.just(contact).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Contact>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Contact contact) {
                        Log.d(TAG, "SUCCESSFULLY INSERTED");
                        database.getContactDao().insertContact(contact);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "ERROR INSERTING ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DONE INSERTING");
                        disposable.clear();
                    }
                });
    }

    public static void update(ContactDatabase database, Contact contact) {
        Observable.just(contact).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Contact>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Contact contact) {
                        Log.d(TAG, "SUCCESSFULLY UPDATED");
                        database.getContactDao().updateContact(
                                contact.getId(),
                                contact.getName(),
                                contact.getMobileNumber(),
                                contact.getMobileNetwork(),
                                contact.getEmail()
                        );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "ERROR UPDATE ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DONE UPDATE");
                        disposable.clear();
                    }
                });
    }

    public static void delete(ContactDatabase database, Contact contact) {
        Observable.just(contact).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Contact>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Contact contact) {
                        Log.d(TAG, "SUCCESSFULLY UPDATED");
                        database.getContactDao().deleteContact(contact.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "ERROR DELETING ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DONE DELETING");
                        disposable.clear();
                    }
                });
    }

    public static void deleteAll(ContactDatabase database) {
        Observable.just(new Object()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Log.d(TAG, "SUCCESSFULLY DELETED ALL");
                        database.getContactDao().deleteContacts();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "ERROR DELETING ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DONE DELETING");
                        disposable.clear();
                    }
                });
    }


}
