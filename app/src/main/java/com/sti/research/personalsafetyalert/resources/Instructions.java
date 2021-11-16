package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.model.Instruction;

public class Instructions {

    public static Instruction[] getInstructions() {
        return INSTRUCTIONS;
    }


    public static final Instruction INSTRUCTION_ONE = new Instruction("App Notification", "Please make sure you are getting a notification. Click Settings button and go to Notifications and turn on notification of Personal Safety Alert app.", "Settings");
    public static final Instruction INSTRUCTION_TWO = new Instruction("Restart Phone", "Please restart your phone and check it again.\n\n If none of these methods works, please contact us.", "Go to Help");

    public static final Instruction[] INSTRUCTIONS = {INSTRUCTION_ONE, INSTRUCTION_TWO};

}
