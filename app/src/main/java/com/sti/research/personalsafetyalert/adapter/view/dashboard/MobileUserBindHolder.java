package com.sti.research.personalsafetyalert.adapter.view.dashboard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemMobileUsersBinding;
import com.sti.research.personalsafetyalert.model.MobileUser;

public class MobileUserBindHolder extends BaseBindHolder {

    private final ItemMobileUsersBinding binding;

    public MobileUserBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemMobileUserId.setText("");
        binding.itemMobileUserUsername.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((MobileUserRecyclerAdapter.OnMobileUserClickListener) itemView.getContext());
    }

    @Override
    public void onBind(MobileUser mobileUser) {
        super.onBind(mobileUser);
        binding.setMobileUsers(mobileUser);
    }
}
