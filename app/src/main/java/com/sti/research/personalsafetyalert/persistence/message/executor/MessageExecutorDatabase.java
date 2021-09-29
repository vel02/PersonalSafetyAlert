package com.sti.research.personalsafetyalert.persistence.message.executor;

import android.util.Log;

import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.persistence.message.MessageDatabase;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MessageExecutorDatabase {

    private static final String TAG = "database";

    private static final CompositeDisposable disposable = new CompositeDisposable();

    public static void insert(MessageDatabase database, Message message) {
        Observable.just(message).subscribeOn(Schedulers.io()).subscribe(new Observer<Message>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Message message) {
                Log.d(TAG, "SUCCESSFULLY INSERTED");
                database.getMessageDao().insertMessage(message);
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

    public static void insert(MessageDatabase database, List<Message> messages) {
        Observable.just(messages).subscribeOn(Schedulers.io()).subscribe(new Observer<List<Message>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<Message> message) {
                Log.d(TAG, "SUCCESSFULLY INSERTED");
                database.getMessageDao().insertMessages(message);
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

    public static void update(MessageDatabase database, Message message) {
        Observable.just(message).subscribeOn(Schedulers.io()).subscribe(new Observer<Message>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Message message) {
                Log.d(TAG, "SUCCESSFULLY UPDATED");
                database.getMessageDao().updateMessage(
                        message.getId(),
                        message.getTimestamp());
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

    public static void deleteAll(MessageDatabase database) {
        Observable.just(new Object()).subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.d(TAG, "SUCCESSFULLY DELETED ALL");
                database.getMessageDao().deleteMessages();
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
