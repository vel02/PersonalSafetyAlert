package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.model.Tutorial;

public class Tutorials {

    public static Tutorial[] getTutorials() {
        return TUTORIALS;
    }

    public static final Tutorial TUTORIAL_ONE = new Tutorial("Security", "Personal Safety Alert stands for your awareness and security.",
            R.drawable.ic_security_holder);
    public static final Tutorial TUTORIAL_TWO = new Tutorial("Location",
            "Let your emergency contacts know you are okay.",
            R.drawable.ic_location_holder);
    public static final Tutorial TUTORIAL_THREE = new Tutorial("Safety",
            "Ensure you get home safe at the end of the day.",
            R.drawable.ic_safety_holder);

    public static final Tutorial[] TUTORIALS = {TUTORIAL_ONE, TUTORIAL_TWO, TUTORIAL_THREE};

}
