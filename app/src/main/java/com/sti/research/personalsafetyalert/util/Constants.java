package com.sti.research.personalsafetyalert.util;

public class Constants {

    public static final String KEY_ANIM_TYPE = "anim_type";

    public static class PermissionManager {
        public static final int PERMISSION_LOCATION_REQUEST_CODE = 101;
        public static final int PERMISSION_SEND_SMS_REQUEST_CODE = 102;
        public static final int PERMISSION_RECORD_AUDIO_REQUEST_CODE = 103;
        public static final int PERMISSION_STORAGE_REQUEST_CODE = 104;
    }

    public static class GalleryManager {
        public static final int PICK_GALLERY_REQUEST = 201;
    }

    public static class MessagingManager {
        public static final String EMAIL_HOST = "personal.safety.alert.bot@gmail.com";
        public static final String EMAIL_SUBJECT_WITHOUT_SUGGESTION = "CUSTOMER PROBLEM REPORT";
        public static final String EMAIL_SUBJECT_WITH_SUGGESTION = "CUSTOMER PROBLEM/SUGGESTION REPORT";
    }

    public enum TransitionType {
        Fade, Slide
    }

}
