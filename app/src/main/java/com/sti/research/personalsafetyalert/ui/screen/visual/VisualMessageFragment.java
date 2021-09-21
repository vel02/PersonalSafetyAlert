package com.sti.research.personalsafetyalert.ui.screen.visual;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentVisualMessageBinding;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.screen.visual.EditTextTextWatcher;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class VisualMessageFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentVisualMessageBinding binding;
    private VisualMessageFragmentViewModel viewModel;

    private HostScreen hostScreen;

    private String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualMessageBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(VisualMessageFragmentViewModel.class);
        navigate();

        if (message != null && isNotEmpty(message))
            binding.visualEditCustomMessage.setText(message);
    }

    private void navigate() {
        binding.visualDone.setOnClickListener(v -> {
            String content = binding.visualEditCustomMessage.getText().toString();
            saveNewMessage(content);
            hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_visual_to_home));
        });

        binding.visualEditCustomMessage.addTextChangedListener(new EditTextTextWatcher(binding.visualDisplayCustomMessage));
    }

    private void saveNewMessage(String content) {
        if (isNotEmpty(content) && !content.equals(viewModel.getCurrentSelectedMessage())) {
            Message message = new Message();
            message.setMessage(content);
            message.setTimestamp(String.valueOf(getCurrentTimeAndDateInMillis()));
            Popup.message(requireView(), "Message Saved!");
            viewModel.insertMessage(message);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (!(activity instanceof HostScreen)) {
            assert activity != null;
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement HostScreen interface.");
        }
        hostScreen = (HostScreen) activity;

        message = VisualMessageFragmentArgs.fromBundle(getArguments()).getMessage();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}