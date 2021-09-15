package com.sti.research.personalsafetyalert.resources;

import com.sti.research.personalsafetyalert.model.Permission;

public class Permissions {

    public static Permission[] getPermissions() {
        return PERMISSIONS;
    }

    public static final Permission LOCATION = new Permission("Location",
            "Personal Safety Alert needs to access your location, which is required to show your current location in real time.",
            "Enable Location");

    public static final Permission INTERNET = new Permission("Location",
            "Personal Safety Alert needs to access your location, which is required to show your current location in real time.",
            "Enable Location");

    public static final Permission[] PERMISSIONS = {LOCATION, INTERNET};

}
