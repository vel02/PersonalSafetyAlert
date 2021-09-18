package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHelpBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.NavigatePermission;

import dagger.android.support.DaggerFragment;


public class HelpFragment extends DaggerFragment {

    private FragmentHelpBinding binding;

    private HostScreen hostScreen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navigate();
    }

    private void navigate() {
        binding.helpHowTo.setOnClickListener(v -> {
            hostScreen.onInflate(v, getString(R.string.tag_fragment_help_to_how_to));
        });

        binding.helpNotWorking.setOnClickListener(v -> {
            hostScreen.onInflate(v, getString(R.string.tag_fragment_help_to_not_working));
        });

        binding.helpContactUs.setOnClickListener(v -> {
            hostScreen.onInflate(v, getString(R.string.tag_fragment_help_to_contact_us));
        });
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}