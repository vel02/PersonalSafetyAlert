package com.sti.research.personalsafetyalert.adapter.view.logs;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemLogHolderBinding;

public class UserLogsHolderBindHolder extends BaseBindHolder {

    private final ItemLogHolderBinding binding;

    public UserLogsHolderBindHolder(@NonNull View itemView) {
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
