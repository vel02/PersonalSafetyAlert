package com.sti.research.personalsafetyalert.ui;

import android.view.View;

public interface HostScreen {

    void onInflate(View view, String screen);

    void onInflate(View view, String screen, Object object);

}
