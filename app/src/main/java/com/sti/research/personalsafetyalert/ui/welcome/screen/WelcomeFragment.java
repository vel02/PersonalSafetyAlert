package com.sti.research.personalsafetyalert.ui.welcome.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.NotWorkingPagerAdapter;
import com.sti.research.personalsafetyalert.adapter.WelcomePagerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentWelcomeBinding;
import com.sti.research.personalsafetyalert.model.Tutorial;
import com.sti.research.personalsafetyalert.resources.Tutorials;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;
import com.sti.research.personalsafetyalert.ui.welcome.screen.pager.WelcomeFragmentViewPager;
import com.sti.research.personalsafetyalert.util.animation.CubeInScalingTransformation;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WelcomeFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentWelcomeBinding binding;
    private WelcomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(WelcomeFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Tutorial[] tutorials = Tutorials.getTutorials();
        for (Tutorial tutorial : tutorials) {
            WelcomeFragmentViewPager fragment = WelcomeFragmentViewPager.getInstance(tutorial);
            fragments.add(fragment);
        }

        WelcomePagerAdapter pagerAdapter = new WelcomePagerAdapter(requireActivity().getSupportFragmentManager(), fragments);
        viewPagerAnimation();
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager, true);
    }

    private void viewPagerAnimation() {
        binding.viewPager.setPageTransformer(true, new CubeInScalingTransformation());
    }
}