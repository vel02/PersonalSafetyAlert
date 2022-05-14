package com.sti.research.personalsafetyalert.util.test.contact;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private ContactVO contact;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(ContactVO contact) {
        this.contact = contact;
        this.initialization();
        this.clear();
    }

    public ContactVO getContact() {
        return contact;
    }

}
