package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.howto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHowToBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HowToFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentHowToBinding binding;
    private HowToViewModel viewModel;

    private HostScreen hostScreen;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHowToBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(HowToViewModel.class);
        navigate();
    }

    private void navigate() {
        binding.howToNextAndDone.setOnClickListener(v -> {
            if (binding.howToStepTwo.getVisibility() == View.INVISIBLE) {
                toggle(binding.howToStepTwo);
            } else if (binding.howToStepThree.getVisibility() == View.INVISIBLE) {
                toggle(binding.howToStepThree);
            } else if (binding.howToStepFour.getVisibility() == View.INVISIBLE) {
                toggle(binding.howToStepFour);
                binding.howToNextAndDone.setText(R.string.txt_done);
            } else if (binding.howToStepFour.getVisibility() == View.VISIBLE) {
                hostScreen.onInflate(v, getString(R.string.tag_fragment_how_to_to_help));
            }
        });
    }

    private void toggle(View target) {

        @SuppressLint("RtlHardcoded") Transition transition = new Slide(Gravity.LEFT);
        transition.setDuration(500);
        transition.addTarget(target);

        TransitionManager.beginDelayedTransition(binding.howToParentLayout, transition);
        target.setVisibility(View.VISIBLE);
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