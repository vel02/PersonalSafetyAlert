package com.sti.research.personalsafetyalert.adapter.view.contact;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemContactBinding;
import com.sti.research.personalsafetyalert.model.list.Contact;

public class ContactBindHolder extends BaseBindHolder {

    private final ItemContactBinding binding;

    public ContactBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemContactName.setText("");
        binding.itemContactEmail.setText("");
        binding.itemContactNetwork.setText("");
        binding.itemContactMobileNumber.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((ContactRecyclerAdapter.OnContactClickListener) itemView.getContext());
    }

    @Override
    public void onBind(Contact contact) {
        super.onBind(contact);
        binding.setContact(contact);
    }
}
