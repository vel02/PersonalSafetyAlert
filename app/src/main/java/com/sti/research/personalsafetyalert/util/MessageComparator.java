package com.sti.research.personalsafetyalert.util;

import com.sti.research.personalsafetyalert.model.Message;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
