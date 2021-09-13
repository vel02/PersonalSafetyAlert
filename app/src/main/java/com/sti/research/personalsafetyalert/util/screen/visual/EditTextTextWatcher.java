package com.sti.research.personalsafetyalert.util.screen.visual;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.sti.research.personalsafetyalert.R;

import java.lang.ref.WeakReference;

public class EditTextTextWatcher implements TextWatcher {

    private final WeakReference<View> reference;

    public EditTextTextWatcher(View view) {
        this.reference = new WeakReference<>(view);
    }

    @Override
    public void beforeTextChanged(CharSequence value, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence value, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable value) {
        final View view = reference.get();
        if (view != null) {
            if (view instanceof TextView) {
                if (value.length() == 0) ((TextView) view).setText(R.string.txt_sample_text_00);
                else ((TextView) view).setText(value);
            }
        }
    }
}
