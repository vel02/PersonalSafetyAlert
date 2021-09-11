package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.sti.research.personalsafetyalert.databinding.FragmentNotWorkingViewPagerBinding;
import com.sti.research.personalsafetyalert.model.Instruction;


import dagger.android.support.DaggerFragment;

public class NotWorkingFragmentViewPager extends DaggerFragment {

    private FragmentNotWorkingViewPagerBinding binding;

    private Instruction instruction;

    public static NotWorkingFragmentViewPager getInstance(Instruction instruction) {
        NotWorkingFragmentViewPager fragment = new NotWorkingFragmentViewPager();

        if (instruction != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("instruction", instruction);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            instruction = getArguments().getParcelable("instruction");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotWorkingViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.setTitle(instruction.getTitleText());
        binding.setMessage(instruction.getContentText());
        binding.setButton(instruction.getAccessText());

        navigate();
    }

    private void navigate() {
        switch (binding.vpButton.getText().toString()) {
            case "Notification Access":
                binding.vpButton.setOnClickListener(v ->
                        Bubble.message(requireContext(), "Notification Access"));
                break;
            case "Settings":
                binding.vpButton.setOnClickListener(v ->
                        Bubble.message(requireContext(), "Settings"));
                break;
            case "Contact":
                binding.vpButton.setOnClickListener(v ->
                        Bubble.message(requireContext(), "Contact"));
                break;
        }
    }
}
