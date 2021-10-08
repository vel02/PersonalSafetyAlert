package com.sti.research.personalsafetyalert.adapter.view.contact;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemContactHolderBinding;

public class ContactHolderBindHolder extends BaseBindHolder {

    private final ItemContactHolderBinding binding;

    public ContactHolderBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
    }

    @Override
    protected void initialization() {
    }

}
