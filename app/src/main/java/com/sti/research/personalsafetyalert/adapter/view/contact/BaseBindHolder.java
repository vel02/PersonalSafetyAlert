package com.sti.research.personalsafetyalert.adapter.view.contact;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.model.list.Contact;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private Contact contact;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(Contact contact) {
        this.contact = contact;
        this.initialization();
        this.clear();
    }

    public Contact getContact() {
        return contact;
    }

}
