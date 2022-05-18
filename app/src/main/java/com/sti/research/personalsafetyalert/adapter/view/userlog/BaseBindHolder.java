package com.sti.research.personalsafetyalert.adapter.view.userlog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.UserLog;

public abstract class BaseBindHolder extends RecyclerView.ViewHolder {

    private UserLog userLog;

    public BaseBindHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    protected abstract void initialization();

    public void onBind(UserLog userLog) {
        this.userLog = userLog;
        this.initialization();
        this.clear();
    }

    public UserLog getMobileUsers() {
        return userLog;
    }

}
