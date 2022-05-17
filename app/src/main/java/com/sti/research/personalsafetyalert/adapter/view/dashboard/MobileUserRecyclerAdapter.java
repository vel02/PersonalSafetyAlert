package com.sti.research.personalsafetyalert.adapter.view.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ItemMobileUserHolderBinding;
import com.sti.research.personalsafetyalert.databinding.ItemMobileUsersBinding;
import com.sti.research.personalsafetyalert.model.MobileUser;

import java.util.ArrayList;
import java.util.List;

public class MobileUserRecyclerAdapter extends RecyclerView.Adapter<BaseBindHolder> {

    private static final int MOBILE_USER_TYPE = 1;
    private static final int EXHAUSTED_TYPE = 2;

    private List<MobileUser> mobileUsers = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<MobileUser> mobileUsers) {
        this.mobileUsers = mobileUsers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public BaseBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case MOBILE_USER_TYPE:
                ItemMobileUsersBinding bindingContact = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_mobile_users,
                                parent, false);
                return new MobileUserBindHolder(bindingContact.getRoot());
            case EXHAUSTED_TYPE:
            default:
                ItemMobileUserHolderBinding bindingExhausted = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_mobile_user_holder,
                                parent, false);
                return new MobileUserHolderBindHolder(bindingExhausted.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindHolder holder, int position) {
        if (getItemViewType(position) == MOBILE_USER_TYPE) holder.onBind(mobileUsers.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (mobileUsers.size() <= 0) {

            return EXHAUSTED_TYPE;
        } else {
            return MOBILE_USER_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return ((mobileUsers != null && mobileUsers.size() > 0) ? mobileUsers.size() : 1);
    }

    public interface OnMobileUserClickListener {
        void onMobileUserResult(MobileUser mobileUser);
    }

}
