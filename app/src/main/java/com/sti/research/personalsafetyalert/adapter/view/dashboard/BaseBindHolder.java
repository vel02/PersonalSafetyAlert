package com.sti.research.personalsafetyalert.adapter.view.dashboard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.list.Contact;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private MobileUser mobileUser;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(MobileUser mobileUser) {
        this.mobileUser = mobileUser;
        this.initialization();
        this.clear();
    }

    public MobileUser getMobileUsers() {
        return mobileUser;
    }

}
