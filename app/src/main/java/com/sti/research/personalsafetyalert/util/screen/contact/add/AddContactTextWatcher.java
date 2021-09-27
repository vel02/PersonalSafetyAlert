package com.sti.research.personalsafetyalert.util.screen.contact.add;

import static com.sti.research.personalsafetyalert.util.Utility.isNotEmpty;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactTextWatcher implements TextWatcher {

    private final EditText name;
    private final EditText number;
    private final EditText email;
    private final Button done;

    public AddContactTextWatcher(EditText name, EditText number, EditText email, Button done) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.done = done;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (name != null && number != null && email != null && done != null) {
            String name = this.name.getText().toString();
            String number = this.number.getText().toString();
            String email = this.email.getText().toString();

            done.setEnabled(isNotEmpty(name) && isNotEmpty(number) && isNotEmpty(email));
        }

    }
}
