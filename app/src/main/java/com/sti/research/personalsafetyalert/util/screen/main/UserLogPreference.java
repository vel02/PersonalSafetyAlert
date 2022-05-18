package com.sti.research.personalsafetyalert.util.screen.main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sti.research.personalsafetyalert.model.UserLog;

import java.lang.reflect.Type;
import java.util.List;


public class UserLogPreference {

    public static final String INPUT_USER_LOG_REQUEST = "com.sti.research.personalsafetyalert.INPUT_USER_LOG_REQUEST";


    private UserLogPreference() {
    }

    private static UserLogPreference instance;

    public static UserLogPreference getInstance() {
        if (instance == null) instance = new UserLogPreference();
        return instance;
    }

    public void setUserLogInput(Context context, List<UserLog> userLogs) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(userLogs);

        editor.putString(INPUT_USER_LOG_REQUEST, json);
        editor.apply();

    }

    public List<UserLog> getUserLogOutput(Context context) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(INPUT_USER_LOG_REQUEST, "");
        Type type = new TypeToken<List<UserLog>>() {
        }.getType();

        return gson.fromJson(json, type);
    }


}
