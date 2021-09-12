package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentSettingsBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SettingsFragment extends DaggerFragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.layoutShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hi, did you know?" +
                    "\n\nThere is an app that can send emergency message to your friends and love ones with less effort, when you're not busy, try use Personal Safety Alert for free." +
                    "\n\nCheck this out!" +
                    "\n" +
                    "\nhttps://github.com/vel02/PersonalSafetyAlert";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });
    }
}