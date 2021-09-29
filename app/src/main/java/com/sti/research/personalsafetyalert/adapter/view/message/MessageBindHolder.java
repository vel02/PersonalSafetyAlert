package com.sti.research.personalsafetyalert.adapter.view.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.databinding.ItemMessageBinding;
import com.sti.research.personalsafetyalert.model.Message;

public class MessageBindHolder extends BaseBindHolder {

    private static final String TAG = "MessageBindHolder";

    private final ItemMessageBinding binding;

    public MessageBindHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected void clear() {
        binding.itemMessageContent.setText("");
    }

    @Override
    protected void initialization() {
        binding.setListener((MessageRecyclerAdapter.OnMessageClickListener) itemView.getContext());
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        binding.setMessage(message);
    }
}
