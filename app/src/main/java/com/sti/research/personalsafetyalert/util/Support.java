package com.sti.research.personalsafetyalert.util;

import android.view.MotionEvent;
import android.view.View;

public class Support {

    private Support() {
    }

    public static boolean rippleEffect(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                return true;
            default:
                return false;
        }
    }

}
