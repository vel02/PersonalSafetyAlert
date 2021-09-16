package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.model.Tutorial;

public class Tutorials {

    public static Tutorial[] getTutorials() {
        return TUTORIALS;
    }

    public static final Tutorial TUTORIAL_ONE = new Tutorial("Notification Access", "1. Click Notification Access button." +
            "\n2. Turn OFF and ON Personal Safety App in the Notification Access screen." +
            "\n3. Update location will work now." +
            "\nIf it doesn't work please swipe left to try next method.",
            R.drawable.ic_location_holder);
    public static final Tutorial TUTORIAL_TWO = new Tutorial("App Notification",
            "Please make sure you are getting a notification. Click Settings button and go to Notifications and turn on notification of Personal Safety Alert app.",
            R.drawable.ic_sms_holder);
    public static final Tutorial TUTORIAL_THREE = new Tutorial("Restart Phone",
            "Please restart your phone and check it again. If none of these methods works, please contact us.",
            R.drawable.ic_storage_holder);

    public static final Tutorial[] TUTORIALS = {TUTORIAL_ONE, TUTORIAL_TWO, TUTORIAL_THREE};

}
