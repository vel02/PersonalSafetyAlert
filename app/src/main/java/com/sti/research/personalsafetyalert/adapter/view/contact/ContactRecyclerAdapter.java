package com.sti.research.personalsafetyalert.adapter.view.contact;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ItemContactBinding;
import com.sti.research.personalsafetyalert.model.list.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactBindHolder> {

    private List<Contact> contacts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ContactBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_contact,
                        parent, false);
        return new ContactBindHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ContactBindHolder holder, int position) {
        holder.onBind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return ((contacts != null && contacts.size() > 0) ? contacts.size() : 0);
    }

    public interface OnContactClickListener {
        void onContactResult(Contact contact);
    }

}
