package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.adapter.NotWorkingPagerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentNotWorkingBinding;
import com.sti.research.personalsafetyalert.model.Instruction;
import com.sti.research.personalsafetyalert.resources.Instructions;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager.NotWorkingFragmentViewPager;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.animation.CubeInScalingTransformation;
import com.sti.research.personalsafetyalert.util.animation.PopTransformation;
import com.sti.research.personalsafetyalert.util.animation.ZoomOutTransformation;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class NotWorkingFragment extends DaggerFragment implements NotWorkingFragmentViewPager.NotWorkingViewPagerListener {

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
            fragment.setNotWorkingViewPagerListener(this);
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
        viewModel.observedSelectedTabPosition().observe(getViewLifecycleOwner(), this::setBehaviourInstructionButtons);
    }


    private void viewPagerAnimation() {
        binding.viewPager.setPageTransformer(true, new ZoomOutTransformation());
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

    private void setBehaviourInstructionButtons(Integer position) {
        if (position == 0) {
            binding.previousInstruction.setVisibility(View.INVISIBLE);
            binding.nextInstruction.setVisibility(View.VISIBLE);
        } else if (position > 0 && position != (binding.tabLayout.getTabCount() - 1)) {
            binding.previousInstruction.setVisibility(View.VISIBLE);
            binding.nextInstruction.setVisibility(View.VISIBLE);
        } else if (position > 0 && position == (binding.tabLayout.getTabCount() - 1)) {
            binding.previousInstruction.setVisibility(View.VISIBLE);
            binding.nextInstruction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        requireActivity().onBackPressed();
    }
}