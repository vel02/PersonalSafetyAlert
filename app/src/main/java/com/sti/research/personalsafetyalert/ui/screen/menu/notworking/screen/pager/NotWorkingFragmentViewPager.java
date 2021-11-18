package com.sti.research.personalsafetyalert.ui.screen.menu.notworking.screen.pager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        binding.vpButton.setText(instruction.getAccessText());

        navigate();
    }

    private void navigate() {
        switch (binding.vpButton.getText().toString()) {
            case "Settings":
                binding.vpButton.setVisibility(View.VISIBLE);
                binding.vpButton.setOnClickListener(v -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromParts("package", binding.vpButton.getContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                });
                break;
            case "Go to Help":
                binding.vpButton.setOnClickListener(v -> {
                    listener.onBackPressed();
                });
                break;
        }
    }

    private NotWorkingViewPagerListener listener;

    public interface NotWorkingViewPagerListener {
        void onBackPressed();
    }

    public void setNotWorkingViewPagerListener(NotWorkingViewPagerListener listener) {
        this.listener = listener;
    }
}
