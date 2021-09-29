package com.sti.research.personalsafetyalert.adapter.view.message;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ItemMessageBinding;
import com.sti.research.personalsafetyalert.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageBindHolder> {

    private List<Message> messages = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void refresh(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_message,
                        parent, false);
        return new MessageBindHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MessageBindHolder holder, int position) {
        holder.onBind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return ((messages != null && messages.size() > 0) ? messages.size() : 0);
    }

    public interface OnMessageClickListener {
        void onMessageResult(Message message);
    }

}
