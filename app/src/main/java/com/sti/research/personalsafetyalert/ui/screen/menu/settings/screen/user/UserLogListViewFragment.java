package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.user;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentUserLogListViewBinding;
import com.sti.research.personalsafetyalert.model.UserLog;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.MobileUserFragmentArgs;

import java.util.Arrays;
import java.util.List;

import dagger.android.support.DaggerFragment;


public class UserLogListViewFragment extends DaggerFragment {

    private FragmentUserLogListViewBinding binding;

    private HostScreen hostScreen;

    private UserLog userLog;

    private MediaPlayer mp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserLogListViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (userLog != null) {
            Log.e("VIEW", "onViewCreated: " + userLog);

            binding.logTitle.setText(userLog.getTitle());
            binding.logMessage.setText(userLog.getMessage());
            binding.logTitle.setText(userLog.getTimestamp());
            binding.logMobileNumbers.setText(userLog.getPhoneNumber());
            binding.logEmails.setText(userLog.getEmail());

            List<String> audioPath = Arrays.asList(userLog.getAudioPath().split(","));
            if (audioPath.get(0) != null) {
                binding.logAudioPathOne.setText(audioPath.get(0));
                binding.logAudioPathOne.setOnClickListener(v -> {
                    if (mp == null) {
                        audioPlayer(audioPath.get(0));
                        Toast.makeText(requireContext(), "Playing...", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (mp.isPlaying()) {
                                mp.stop();
                                mp.reset();
                                mp.release();
                                mp = null;
                                Toast.makeText(requireContext(), "Stop", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
            if (audioPath.get(1) != null) {
                binding.logAudioPathTwo.setText(audioPath.get(1));
                binding.logAudioPathTwo.setOnClickListener(v -> {
                    if (mp == null) {
                        audioPlayer(audioPath.get(1));
                        Toast.makeText(requireContext(), "Playing...", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (mp.isPlaying()) {
                                mp.stop();
                                mp.reset();
                                mp.release();
                                mp = null;
                                Toast.makeText(requireContext(), "Stop", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    public void audioPlayer(String path) {
        //set up MediaPlayer

        mp = new MediaPlayer();

        try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
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

        userLog = UserLogListViewFragmentArgs.fromBundle(getArguments()).getUserlog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}