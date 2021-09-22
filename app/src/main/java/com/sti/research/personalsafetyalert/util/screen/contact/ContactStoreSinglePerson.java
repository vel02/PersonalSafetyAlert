package com.sti.research.personalsafetyalert.util.screen.contact;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.model.Contact;

/**
 * REFERENCE:
 * https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android
 */
public class ContactStoreSinglePerson {

    public static final String STORE_CONTACT_STATE_REQUEST = "com.sti.research.personalsafetyalert.STORE_CONTACT_STATE_REQUEST";

    private ContactStoreSinglePerson() {
    }

    private static ContactStoreSinglePerson instance;

    public static ContactStoreSinglePerson getInstance() {
        if (instance == null) instance = new ContactStoreSinglePerson();
        return instance;
    }

    public void storeContactSinglePerson(Context context, Contact contact) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(contact);
        editor.putString(STORE_CONTACT_STATE_REQUEST, json);
        editor.apply();
    }

    public Contact restoreContactSinglePerson(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = preferences.getString(STORE_CONTACT_STATE_REQUEST, "");
        return gson.fromJson(json, Contact.class);
    }

    private String formatPhoneNumber(String number) {
        //09166718943 -> +639166718943
        return number.replaceFirst("0", "+63");
    }

}
