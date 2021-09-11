package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentNotWorkingBinding;

import dagger.android.support.DaggerFragment;

public class NotWorkingFragment extends DaggerFragment {

    private FragmentNotWorkingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotWorkingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}