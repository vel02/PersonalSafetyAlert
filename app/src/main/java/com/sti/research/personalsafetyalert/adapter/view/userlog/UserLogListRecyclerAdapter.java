package com.sti.research.personalsafetyalert.adapter.view.userlog;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ItemLogHolderBinding;
import com.sti.research.personalsafetyalert.databinding.ItemMobileUserLogListBinding;
import com.sti.research.personalsafetyalert.model.UserLog;

import java.util.List;

public class UserLogListRecyclerAdapter extends RecyclerView.Adapter<BaseBindHolder> {

    private static final int USER_LOG_TYPE = 1;
    private static final int EXHAUSTED_TYPE = 2;

    private List<UserLog> logs;

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<UserLog> logs) {
        this.logs = logs;
        notifyDataSetChanged();
    }

    public void clear() {
        if (logs != null)
            this.logs.clear();
    }

    @NonNull
    @Override

    public BaseBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case USER_LOG_TYPE:
                ItemMobileUserLogListBinding bindingContact = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_mobile_user_log_list,
                                parent, false);
                return new UserLogListBindHolder(bindingContact.getRoot());
            case EXHAUSTED_TYPE:
            default:
                ItemLogHolderBinding bindingExhausted = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_log_holder,
                                parent, false);
                return new UserLogListHolderBindHolder(bindingExhausted.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindHolder holder, int position) {
        if (getItemViewType(position) == USER_LOG_TYPE) holder.onBind(logs.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (logs != null) {

            if (logs.size() <= 0) {
                return EXHAUSTED_TYPE;
            } else {
                return USER_LOG_TYPE;
            }
        }
        return EXHAUSTED_TYPE;
    }

    @Override
    public int getItemCount() {
        return ((logs != null && logs.size() > 0) ? logs.size() : 1);
    }

    public interface OnUserLogListClickListener {
        void onUserLogListResult(UserLog log);
    }

}
