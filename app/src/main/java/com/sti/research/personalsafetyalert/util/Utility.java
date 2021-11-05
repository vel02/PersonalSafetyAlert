package com.sti.research.personalsafetyalert.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility {

    private Utility() {
    }


    public static boolean serviceIsRunningInForeground(Context context, Object object) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (object.getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getLocationTitle(Context context) {
        return context.getString(R.string.txt_location_updated,
                DateFormat.getDateTimeInstance().format(new Date()));
    }

    public static String getLocationText(Context context, Location location) {

        if (location == null) return "Please wait to retrieve your current location.";

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.isEmpty())
            return "Please wait, the app is retrieving your location.";
        Address address = addresses.get(0);
        List<String> addressFragments = new ArrayList<>();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressFragments.add(address.getAddressLine(i));
        }

        return TextUtils.join(System.getProperty("line.separator"), addressFragments);
    }

    public static boolean isNotEmpty(String value) {
        return !value.isEmpty();
    }

    public static long getCurrentTimeAndDateInMillis() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String currentDate = formatter.format(date);

        try {
            date = formatter.parse(currentDate);
            assert date != null;
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void hideSoftKeyboard(Fragment fragment) {
        final InputMethodManager imm = (InputMethodManager) fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fragment.requireView().getWindowToken(), 0);
    }

    public static class Popup {

        private Popup() {
        }

        public static void messageWithAction(View view, CharSequence message) {
            Resources resources = view.getResources();
            Snackbar.make(view, message,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(resources.getColor(R.color.primarySecondary))
                    .setTextColor(resources.getColor(R.color.text_color_white))
                    .setActionTextColor(resources.getColor(R.color.text_color_white))
                    .setAction(R.string.txt_hide, v -> {
                    }).show();
        }

        public static void message(View view, CharSequence message) {
            Resources resources = view.getResources();
            Snackbar.make(view, message,
                    Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.primarySecondary))
                    .setTextColor(resources.getColor(R.color.text_color_white))
                    .show();
        }
    }

    public static class Bubble {

        private Bubble() {
        }

        public static void message(Context context, CharSequence message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static class Connection {
        private Connection() {
        }

        public static boolean isWifiConnected(Context context) {
            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return ((netInfo != null) && netInfo.isConnected());
        }

        public static boolean isMobileConnected(Context context) {
            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return ((netInfo != null) && netInfo.isConnected());
        }

        //REFERENCE:https://stackoverflow.com/questions/44024009/how-to-check-if-gps-i-turned-on-or-not-programmatically-in-android-version-4-4-a
        public static boolean isGPSEnabled(Context context) {
            int locationMode;

            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }

    }

    public static class Dialog {

        private Dialog() {
        }

        public static void initAlertDialogBuilder(FragmentActivity context, int layout) {
            androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(context, R.style.PersonalSafetyAlert_AlertDialogTheme);
            View view = context.getLayoutInflater().inflate(layout, null);
            TextView positiveButton = view.findViewById(R.id.dialog_button_positive);
            builder.setCancelable(false);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
            positiveButton.setOnClickListener(v -> dialog.dismiss());
        }
    }
}
