package com.sti.research.personalsafetyalert.adapter.view.dashboard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemMobileUserHolderBinding;

public class MobileUserHolderBindHolder extends BaseBindHolder {

    private final ItemMobileUserHolderBinding binding;

    public MobileUserHolderBindHolder(@NonNull View itemView) {
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
