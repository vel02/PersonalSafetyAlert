package com.sti.research.personalsafetyalert.adapter.view.userlog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemLogHolderBinding;

public class UserLogListHolderBindHolder extends BaseBindHolder {

    private final ItemLogHolderBinding binding;

    public UserLogListHolderBindHolder(@NonNull View itemView) {
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
