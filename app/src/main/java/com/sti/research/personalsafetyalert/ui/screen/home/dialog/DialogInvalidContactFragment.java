package com.sti.research.personalsafetyalert.ui.screen.home.dialog;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentDialogInvalidContactBinding;

import java.util.Objects;

import dagger.android.support.DaggerDialogFragment;
import dagger.android.support.DaggerFragment;

public class DialogInvalidContactFragment extends DaggerDialogFragment {

    private FragmentDialogInvalidContactBinding binding;

    @Override
    public int getTheme() {
        return R.style.PersonalSafetyAlertRoundedCornersDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        window.setLayout(900, 550);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDialogInvalidContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        binding.dialogButtonPositive.setOnClickListener(v -> {
            dismiss();
        });
    }

}