package com.sti.research.personalsafetyalert.ui.screen.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentPermissionBinding;
import com.sti.research.personalsafetyalert.repository.PermissionRepository.RequiredPermissionsState;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.NavigatePermission;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PermissionFragment extends DaggerFragment {

    private static final String TAG = "test";

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

        //TODO transfer this to permission check screen
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            initAlertDialogBuilder(requireActivity(), R.layout.dialog_manage_external_storage);
//        }

        configureActionBarTitle();
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(PermissionFragmentViewModel.class);
        navigate();
        subscribeObservers();
    }

//    public void initAlertDialogBuilder(FragmentActivity context, int layout) {
//        androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(context, R.style.PersonalSafetyAlert_AlertDialogTheme);
//        View view = context.getLayoutInflater().inflate(layout, null);
//        TextView positiveButton = view.findViewById(R.id.dialog_button_positive);
//        builder.setCancelable(false);
//        builder.setView(view);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        positiveButton.setOnClickListener(v -> {
//            dialog.dismiss();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                if (Environment.isExternalStorageManager()) {
//                    //todo when permission is granted
//                } else {
//                    //request for the permission
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                    Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
//                    intent.setData(uri);
//                    startActivity(intent);
//                }
//            }
//        });
//    }

    private void subscribeObservers() {
        viewModel.observedPermissionLocationState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionLocationState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_GRANTED) {
                binding.permissionLocationDone.setVisibility(View.VISIBLE);
            }
        });

        viewModel.observedPermissionSendSMSState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionSendSMSState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_GRANTED) {
                binding.permissionSendSmsDone.setVisibility(View.VISIBLE);
            }
        });

        viewModel.observedRecordAudioState().removeObservers(getViewLifecycleOwner());
        viewModel.observedRecordAudioState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_GRANTED) {
                binding.permissionRecordAudioDone.setVisibility(View.VISIBLE);
            }
        });

        viewModel.observedPermissionStorageState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionStorageState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_GRANTED) {
                binding.permissionStorageDone.setVisibility(View.VISIBLE);
            }
        });

        viewModel.observedPermissionRequiredState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionRequiredState().observe(getViewLifecycleOwner(), status -> {
            if (status == RequiredPermissionsState.COMPLETED) {
                binding.permissionProceed.setEnabled(true);
            }
        });

    }

    private void navigate() {
        binding.permissionLocation.setOnClickListener(v -> navigate.requestLocationPermission());

        binding.permissionSendSms.setOnClickListener(v -> navigate.requestSendSMSPermission());

        binding.permissionRecordAudio.setOnClickListener(v -> navigate.requestRecordAudioPermission());

        binding.permissionStorage.setOnClickListener(v -> navigate.requestStoragePermission());

        binding.permissionProceed.setOnClickListener(v -> {
            hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_permission_to_home));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!navigate.checkLocationPermission()
                && !navigate.checkSendSMSPermission()
                && !navigate.checkRecordAudioPermission()
                && !navigate.checkStoragePermission()) {
            Log.d(TAG, "completed called");
            viewModel.setPermissionLocationState(PackageManager.PERMISSION_GRANTED);
            viewModel.setPermissionSendSMSState(PackageManager.PERMISSION_GRANTED);
            viewModel.setPermissionRecordAudioState(PackageManager.PERMISSION_GRANTED);
            viewModel.setPermissionStorageState(PackageManager.PERMISSION_GRANTED);

            viewModel.setPermissionRequiredState(RequiredPermissionsState.COMPLETED);
        } else if (!navigate.checkLocationPermission()
                || !navigate.checkSendSMSPermission()
                || !navigate.checkRecordAudioPermission()
                || !navigate.checkStoragePermission()) {// not denied (means granted already)
            Log.d(TAG, "partial called");
            if (!navigate.checkLocationPermission())
                viewModel.setPermissionLocationState(PackageManager.PERMISSION_GRANTED);
            if (!navigate.checkSendSMSPermission())
                viewModel.setPermissionSendSMSState(PackageManager.PERMISSION_GRANTED);
            if (!navigate.checkRecordAudioPermission())
                viewModel.setPermissionRecordAudioState(PackageManager.PERMISSION_GRANTED);
            if (!navigate.checkStoragePermission())
                viewModel.setPermissionStorageState(PackageManager.PERMISSION_GRANTED);

            viewModel.setPermissionRequiredState(RequiredPermissionsState.PARTIAL);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void configureActionBarTitle() {
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("");
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