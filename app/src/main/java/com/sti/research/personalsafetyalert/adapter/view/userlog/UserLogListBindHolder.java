package com.sti.research.personalsafetyalert.adapter.view.userlog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemMobileUserLogListBinding;
import com.sti.research.personalsafetyalert.model.UserLog;

public class UserLogListBindHolder extends BaseBindHolder {

    private final ItemMobileUserLogListBinding binding;

    public UserLogListBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemLogsTitle.setText("");
        binding.itemLogsLocation.setText("");
        binding.itemLogsTime.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((UserLogListRecyclerAdapter.OnUserLogListClickListener) itemView.getContext());
    }

    @Override
    public void onBind(UserLog log) {
        super.onBind(log);
        binding.setUserLog(log);
    }
}
