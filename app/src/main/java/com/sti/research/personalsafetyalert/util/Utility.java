package com.sti.research.personalsafetyalert.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.R;

public class Utility {

    private Utility() {
    }

    public static class Popup {

        private Popup() {
        }

        public static void message(View view, CharSequence message) {
            Resources resources = view.getResources();
            Snackbar.make(view, message,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(resources.getColor(R.color.primaryLight))
                    .setActionTextColor(resources.getColor(R.color.primaryDark))
                    .setAction(R.string.txt_hide, v -> {
                    }).show();
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
