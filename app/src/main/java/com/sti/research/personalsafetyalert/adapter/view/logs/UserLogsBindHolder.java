package com.sti.research.personalsafetyalert.adapter.view.logs;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemLogBinding;
import com.sti.research.personalsafetyalert.model.Logs;

public class UserLogsBindHolder extends BaseBindHolder {

    private final ItemLogBinding binding;

    public UserLogsBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemLogsTitle.setText("");
        binding.itemLogsMessage.setText("");
        binding.itemLogsTime.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((UserLogsRecyclerAdapter.OnLogClickListener) itemView.getContext());
    }

    @Override
    public void onBind(Logs log) {
        super.onBind(log);
        binding.setLog(log);
    }
}
