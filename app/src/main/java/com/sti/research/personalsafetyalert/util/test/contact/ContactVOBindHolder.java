package com.sti.research.personalsafetyalert.util.test.contact;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemContactVoBinding;

public class ContactVOBindHolder extends BaseBindHolder {

    private final ItemContactVoBinding binding;

    public ContactVOBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemContactName.setText("");
        binding.itemContactMobileNumber.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((ContactVORecyclerAdapter.OnContactClickListener) itemView.getContext());
    }

    @Override
    public void onBind(ContactVO contact) {
        super.onBind(contact);
        binding.setContact(contact);
    }
}
