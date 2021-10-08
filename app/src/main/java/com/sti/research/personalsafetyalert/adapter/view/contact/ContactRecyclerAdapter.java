package com.sti.research.personalsafetyalert.adapter.view.contact;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ItemContactBinding;
import com.sti.research.personalsafetyalert.databinding.ItemContactHolderBinding;
import com.sti.research.personalsafetyalert.model.list.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<BaseBindHolder> {

    private static final int CONTACT_TYPE = 1;
    private static final int EXHAUSTED_TYPE = 2;

    private List<Contact> contacts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public BaseBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case CONTACT_TYPE:
                ItemContactBinding bindingContact = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_contact,
                                parent, false);
                return new ContactBindHolder(bindingContact.getRoot());
            case EXHAUSTED_TYPE:
            default:
                ItemContactHolderBinding bindingExhausted = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_contact_holder,
                                parent, false);
                return new ContactHolderBindHolder(bindingExhausted.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindHolder holder, int position) {
        if (getItemViewType(position) == CONTACT_TYPE) holder.onBind(contacts.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts.size() <= 0) {

            return EXHAUSTED_TYPE;
        } else {
            return CONTACT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return ((contacts != null && contacts.size() > 0) ? contacts.size() : 1);
    }

    public interface OnContactClickListener {
        void onContactResult(Contact contact);
    }

}
