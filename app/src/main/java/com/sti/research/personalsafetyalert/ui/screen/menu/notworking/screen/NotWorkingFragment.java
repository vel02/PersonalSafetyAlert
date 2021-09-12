package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.adapter.NotWorkingPagerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentNotWorkingBinding;
import com.sti.research.personalsafetyalert.model.Instruction;
import com.sti.research.personalsafetyalert.resources.Instructions;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;
import com.sti.research.personalsafetyalert.util.animation.CubeInScalingTransformation;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class NotWorkingFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentNotWorkingBinding binding;

    private NotWorkingFragmentViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotWorkingBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(NotWorkingFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Instruction[] instructions = Instructions.getInstructions();
        for (Instruction instruction : instructions) {
            NotWorkingFragmentViewPager fragment = NotWorkingFragmentViewPager.getInstance(instruction);
            fragments.add(fragment);
        }

        NotWorkingPagerAdapter pagerAdapter = new NotWorkingPagerAdapter(requireActivity().getSupportFragmentManager(), fragments);
        viewPagerAnimation();
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager, true);

        navigate();

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observedSelectedTabPosition().removeObservers(getViewLifecycleOwner());
        viewModel.observedSelectedTabPosition().observe(getViewLifecycleOwner(), selectedTabPosition -> {
            if (selectedTabPosition == 0) {
                binding.previousInstruction.setVisibility(View.INVISIBLE);
                binding.nextInstruction.setVisibility(View.VISIBLE);
            } else if (selectedTabPosition > 0 && selectedTabPosition != (binding.tabLayout.getTabCount() - 1)) {
                binding.previousInstruction.setVisibility(View.VISIBLE);
                binding.nextInstruction.setVisibility(View.VISIBLE);
            } else if (selectedTabPosition > 0 && selectedTabPosition == (binding.tabLayout.getTabCount() - 1)) {
                binding.previousInstruction.setVisibility(View.VISIBLE);
                binding.nextInstruction.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void viewPagerAnimation() {
        binding.viewPager.setPageTransformer(true, new CubeInScalingTransformation());
    }

    private void navigate() {
        binding.previousInstruction.setOnClickListener(v -> {
            int position = binding.tabLayout.getSelectedTabPosition();
            if (position > 0) {
                int selected = position - 1;
                binding.viewPager.setCurrentItem(selected);
                viewModel.setSelectedTabPosition(selected);
            }

        });

        binding.nextInstruction.setOnClickListener(v -> {
            int position = binding.tabLayout.getSelectedTabPosition();
            if (position < binding.tabLayout.getTabCount()) {
                int selected = position + 1;
                binding.viewPager.setCurrentItem(selected);
                viewModel.setSelectedTabPosition(selected);
            }
        });
    }
}