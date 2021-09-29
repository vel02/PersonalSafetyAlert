package com.sti.research.personalsafetyalert.adapter.view.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.model.Message;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private Message message;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(Message message) {
        this.message = message;
        this.initialization();
        this.clear();
    }

    public Message getMessage() {
        return message;
    }
}
