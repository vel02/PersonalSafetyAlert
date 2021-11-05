package com.sti.research.personalsafetyalert.receiver.sms;

import java.util.Observable;

public class SentReceiverObserver extends Observable {

    private static SentReceiverObserver instance;

    private SentReceiverObserver() {

    }

    public static SentReceiverObserver getInstance() {
        if (instance == null)
            instance = new SentReceiverObserver();
        return instance;
    }

    public void sentReceiverResult(String value) {
        synchronized (this) {
            setChanged();
            notifyObservers(value);
        }

    }

}
