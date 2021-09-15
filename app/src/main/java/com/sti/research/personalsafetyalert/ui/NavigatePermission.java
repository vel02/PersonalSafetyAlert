package com.sti.research.personalsafetyalert.ui;

public interface NavigatePermission {

    boolean checkLocationPermission();

    boolean checkSendSMSPermission();

    void requestLocationPermission();

    void requestSendSMSPermission();

}
