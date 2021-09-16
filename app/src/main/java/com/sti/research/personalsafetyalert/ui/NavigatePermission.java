package com.sti.research.personalsafetyalert.ui;

public interface NavigatePermission {

    boolean checkLocationPermission();

    boolean checkSendSMSPermission();

    boolean checkRecordAudioPermission();

    boolean checkStoragePermission();

    void requestLocationPermission();

    void requestSendSMSPermission();

    void requestRecordAudioPermission();

    void requestStoragePermission();

}
