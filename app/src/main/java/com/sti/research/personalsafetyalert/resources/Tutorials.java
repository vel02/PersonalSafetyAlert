package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.model.Tutorial;

public class Tutorials {

    public static Tutorial[] getTutorials() {
        return TUTORIALS;
    }

    public static final Tutorial TUTORIAL_ONE = new Tutorial("Lorem Ipsum", "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
            R.drawable.ic_location_holder);
    public static final Tutorial TUTORIAL_TWO = new Tutorial("Lorem Ipsum",
            "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
            R.drawable.ic_sms_holder);
    public static final Tutorial TUTORIAL_THREE = new Tutorial("Lorem Ipsum",
            "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
            R.drawable.ic_storage_holder);

    public static final Tutorial[] TUTORIALS = {TUTORIAL_ONE, TUTORIAL_TWO, TUTORIAL_THREE};

}
