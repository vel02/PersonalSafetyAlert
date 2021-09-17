package com.sti.research.personalsafetyalert.ui.welcome.screen.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.NotWorkingPagerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentWelcomeBinding;
import com.sti.research.personalsafetyalert.databinding.FragmentWelcomeViewPagerBinding;
import com.sti.research.personalsafetyalert.model.Instruction;
import com.sti.research.personalsafetyalert.model.Tutorial;
import com.sti.research.personalsafetyalert.resources.Instructions;
import com.sti.research.personalsafetyalert.resources.Tutorials;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;

import java.util.ArrayList;

import dagger.android.support.DaggerFragment;

public class WelcomeFragmentViewPager extends DaggerFragment {

    private FragmentWelcomeViewPagerBinding binding;

    private Tutorial tutorial;

    public static WelcomeFragmentViewPager getInstance(Tutorial tutorial) {
        WelcomeFragmentViewPager fragment = new WelcomeFragmentViewPager();

        if (tutorial != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("tutorial", tutorial);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tutorial = getArguments().getParcelable("tutorial");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.welcomeTitle.setText(tutorial.getTitle());
        binding.welcomeDescription.setText(tutorial.getDescription());
        binding.welcomeLogo.setImageResource(tutorial.getLogo());

    }

}