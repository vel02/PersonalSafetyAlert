package com.sti.research.personalsafetyalert.ui.screen.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentPermissionBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.NavigatePermission;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PermissionFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentPermissionBinding binding;
    private PermissionFragmentViewModel viewModel;

    private HostScreen hostScreen;
    private NavigatePermission navigate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPermissionBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureActionBarTitle();
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(PermissionFragmentViewModel.class);
        navigate();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observedPermissionState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_GRANTED) {
                hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_permission_to_home));
            }
        });
    }

    private void navigate() {
        binding.permissionChoosesToAccept.setOnClickListener(v -> {
            navigate.requestLocationPermission();
        });

        // TODO: 15/09/2021: make function available of this button.
        binding.permissionChoosesToDenied.setOnClickListener(v -> hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_permission_to_home)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!navigate.checkLocationPermission())// not denied (means granted already)
            viewModel.setPermissionState(PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void configureActionBarTitle() {
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("Permission");
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

        if (!(activity instanceof NavigatePermission)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement NavigatePermission interface.");
        }
        navigate = (NavigatePermission) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
        navigate = null;
    }
}