package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentDashboardLogBinding;

import dagger.android.support.DaggerFragment;


public class DashboardLogFragment extends DaggerFragment {

    private FragmentDashboardLogBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardLogBinding.inflate(inflater);
        return binding.getRoot();
    }
}