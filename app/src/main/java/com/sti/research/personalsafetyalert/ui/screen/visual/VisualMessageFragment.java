package com.sti.research.personalsafetyalert.ui.screen.visual;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentVisualMessageBinding;
import com.sti.research.personalsafetyalert.ui.Hostable;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class VisualMessageFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentVisualMessageBinding binding;
    private VisualMessageFragmentViewModel viewModel;

    private Hostable hostable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualMessageBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureActionBarTitle();
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(VisualMessageFragmentViewModel.class);
        navigate();
    }

    private void navigate() {
        binding.btnDone.setOnClickListener(v ->
                hostable.onInflate(requireView(), getString(R.string.tag_fragment_visual_to_home)));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void configureActionBarTitle() {
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle(R.string.title_visual_message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (!(activity instanceof Hostable)) {
            assert activity != null;
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement Hostable interface.");
        }
        hostable = (Hostable) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostable = null;
    }
}