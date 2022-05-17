package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.app.Activity;
import android.content.Context;
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
import com.sti.research.personalsafetyalert.adapter.view.dashboard.MobileUserRecyclerAdapter;
import com.sti.research.personalsafetyalert.adapter.view.logs.UserLogsRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentMobileUserBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.ui.HostScreen;

import java.util.List;

import dagger.android.support.DaggerFragment;


public class MobileUserFragment extends DaggerFragment {

    private FragmentMobileUserBinding binding;

    private MobileUser mobileUser;

    private HostScreen hostScreen;
    private UserLogsRecyclerAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMobileUserBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initContactRecyclerAdapter();
        if (mobileUser != null) {
            Log.e("MOBILEUSER", "onViewCreated: " + mobileUser);

            List<Logs> logsList = mobileUser.getLogs();
            if (logsList.size() > 0) {
                adapter.refresh(logsList);

                for (Logs log : logsList) {
                    Log.e("MOBILEUSER", "onViewCreated: " + log.toString() + " size: " + logsList.size());
                }
            }
        }
    }

    private void initContactRecyclerAdapter() {
        binding.rvLogsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserLogsRecyclerAdapter();
        binding.rvLogsList.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                    hostScreen.onInflate(binding.getRoot(), "tag_fragment_mobileuser_to_settings");
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