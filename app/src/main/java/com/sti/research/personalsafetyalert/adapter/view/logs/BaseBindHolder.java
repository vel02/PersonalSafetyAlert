package com.sti.research.personalsafetyalert.adapter.view.logs;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.model.Logs;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private Logs logs;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(Logs logs) {
        this.logs = logs;
        this.initialization();
        this.clear();
    }

    public Logs getMobileUsers() {
        return logs;
    }

}
