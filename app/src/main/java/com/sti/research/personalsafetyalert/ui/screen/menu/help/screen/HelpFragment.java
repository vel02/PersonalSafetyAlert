package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHelpBinding;

import dagger.android.support.DaggerFragment;


public class HelpFragment extends DaggerFragment {

    private FragmentHelpBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater);
        return binding.getRoot();
    }
}