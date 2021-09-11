package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.model.Instruction;

public class Instructions {

    public static Instruction[] getInstructions() {
        return INSTRUCTIONS;
    }

    public static final Instruction INSTRUCTION_ONE = new Instruction("Notification Access", "1. Click Notification Access button." +
            "\n2. Turn OFF and ON Personal Safety App in the Notification Access screen." +
            "\n3. Update location will work now." +
            "\nIf it doesn't work please swipe left to try next method.", "Notification Access");
    public static final Instruction INSTRUCTION_TWO = new Instruction("App Notification", "Please make sure you are getting a notification. Click Settings button and go to Notifications and turn on notification of Personal Safety Alert app.", "Settings");
    public static final Instruction INSTRUCTION_THREE = new Instruction("Restart Phone", "Please restart your phone and check it again. If none of these methods works, please contact us.", "Contact");

    public static final Instruction[] INSTRUCTIONS = {INSTRUCTION_ONE, INSTRUCTION_TWO, INSTRUCTION_THREE};

}
