package com.sti.research.personalsafetyalert.ui.welcome.screen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.WelcomePagerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentWelcomeBinding;
import com.sti.research.personalsafetyalert.model.Tutorial;
import com.sti.research.personalsafetyalert.resources.Tutorials;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.welcome.screen.pager.WelcomeFragmentViewPager;
import com.sti.research.personalsafetyalert.util.animation.PopTransformation;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WelcomeFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentWelcomeBinding binding;
    private WelcomeFragmentViewModel viewModel;

    private HostScreen hostScreen;

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

        navigate();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observedSelectedTabPosition().removeObservers(getViewLifecycleOwner());
        viewModel.observedSelectedTabPosition().observe(getViewLifecycleOwner(), this::setBehaviourInstructionButtons);
    }

    private void navigate() {

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBehaviourInstructionButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.skipWelcome.setOnClickListener(v -> hostScreen.onInflate(requireView(), getString(R.string.tag_activity_welcome_to_main)));

        binding.gotItWelcome.setOnClickListener(v -> hostScreen.onInflate(requireView(), getString(R.string.tag_activity_welcome_to_main)));

        binding.nextWelcome.setOnClickListener(v -> {
            int position = binding.tabLayout.getSelectedTabPosition();
            if (position < binding.tabLayout.getTabCount()) {
                int selected = position + 1;
                binding.viewPager.setCurrentItem(selected);
                viewModel.setSelectedTabPosition(selected);
            }
        });
    }

    private void viewPagerAnimation() {
        binding.viewPager.setPageTransformer(true, new PopTransformation());
    }

    private void setBehaviourInstructionButtons(Integer selected) {
        if (selected == binding.tabLayout.getTabCount() - 1) {
            binding.gotItWelcome.setVisibility(View.VISIBLE);
            binding.nextWelcome.setVisibility(View.GONE);
        } else {
            binding.gotItWelcome.setVisibility(View.GONE);
            binding.nextWelcome.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}