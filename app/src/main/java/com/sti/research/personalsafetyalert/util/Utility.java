package com.sti.research.personalsafetyalert.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    private Utility() {
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

}
