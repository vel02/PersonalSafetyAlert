package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.logs.UserLogsRecyclerAdapter;
import com.sti.research.personalsafetyalert.adapter.view.userlog.UserLogListRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentViewMobileUserLogsBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.UserLog;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;

import dagger.android.support.DaggerFragment;

public class ViewMobileUserLogsFragment extends DaggerFragment {


    private FragmentViewMobileUserLogsBinding binding;
    private MobileUser mobileUser;

    private HostScreen hostScreen;

    private UserLogListRecyclerAdapter adapter;

    public void onMobileUserLogDataReceiver(UserLog log) {
        Log.e("MOBILEUSER", "onUserLogDataReceiver: " + log);
        hostScreen.onInflate(requireView(), "tag_fragment_mobile_user_log_to_log", log);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewMobileUserLogsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("MOBILEUSERLOGS", "onViewCreated: " + mobileUser);
        initRecyclerAdapter();

        if (mobileUser != null) {
            ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(mobileUser.getUsername() + "'s Mobile logs");
            adapter.refresh(mobileUser.getUserLogs());
        }
    }

    private void initRecyclerAdapter() {
        binding.rvUserLogsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserLogListRecyclerAdapter();
        binding.rvUserLogsList.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_admin, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(requireContext(), R.style.PersonalSafetyAlert_AlertDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.dialog_logout_layout, null);

                TextView positive = view.findViewById(R.id.dialog_button_positive);
                TextView negative = view.findViewById(R.id.dialog_button_negative);
                builder.setCancelable(false);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                negative.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                positive.setOnClickListener(v -> {
                    FirebaseAuth.getInstance().signOut();
//                    hostScreen.onInflate(binding.getRoot(), "tag_fragment_dashboard_to_settings");
                    Intent intent = new Intent(requireActivity(), SettingsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    dialog.dismiss();
                });
            }
        }

        return super.onOptionsItemSelected(item);
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

        mobileUser = MobileUserFragmentArgs.fromBundle(getArguments()).getMobileUser();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}