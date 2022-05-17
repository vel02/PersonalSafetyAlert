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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.dashboard.MobileUserRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentDashboardLogBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.User;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.ui.welcome.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.android.support.DaggerFragment;


public class DashboardLogFragment extends DaggerFragment {

    private static final String TAG = "RETRIEVING!";
    private FragmentDashboardLogBinding binding;

    private MobileUserRecyclerAdapter adapter;

    private HostScreen hostScreen;


    private List<MobileUser> mobileUserList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onMobileUserDataReceiver(MobileUser mobileUser) {
        Log.e("DASHBOARD", "onMobileUserDataReceiver: " + mobileUser);
        hostScreen.onInflate(requireView(), "tag_fragment_dashboard_to_mobileuser", mobileUser);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardLogBinding.inflate(inflater);
        getDatabaseData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initContactRecyclerAdapter();
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

    private void initContactRecyclerAdapter() {
        binding.rvMobileUserList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MobileUserRecyclerAdapter();
        binding.rvMobileUserList.setAdapter(adapter);
    }

    private void getDatabaseData() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://personalsafetyalert-a5eef-default-rtdb.firebaseio.com/").getReference();

        Query query = reference.child(getString(R.string.db_node_admin))
                .orderByKey()
                .equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<MobileUser> mobileUsers = null;

                for (DataSnapshot singleSnap : snapshot.getChildren()) {
                    User user = new User();

                    Map<String, Object> objectMap = (Map<String, Object>) singleSnap.getValue();
                    user.setEmail(objectMap.get("email").toString());

                    mobileUsers = getMobileUsers(singleSnap, requireContext());
                    user.setUsers(mobileUsers);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<MobileUser> getMobileUsers(DataSnapshot singleSnap, Context requireContext) {
        List<MobileUser> users = new ArrayList<>();
        for (DataSnapshot shotUser : singleSnap
                .child(requireContext.getString(R.string.db_node_mobileusers))
                .getChildren()) {

            MobileUser mobileUser = new MobileUser();//shotUsers.getValue(MobileUser.class);
            Map<String, Object> objectMap = (Map<String, Object>) shotUser.getValue();

            mobileUser.setId(objectMap.get("id").toString());
            mobileUser.setUsername(objectMap.get("username").toString());
            mobileUser.setAdmin_id(objectMap.get("admin_id").toString());

            List<Logs> logs = getLogsUsers(shotUser, requireContext);
            mobileUser.setLogs(logs);
            users.add(mobileUser);

        }

        adapter.refresh(users);

        DashboardLogFragment.this.mobileUserList = users;
        for (MobileUser mobileuser : mobileUserList) {
            Log.e(TAG, "onCreateView: " + mobileuser + "\n\n");
        }

//        Log.e(TAG, "onDataChange: mobileusers: " + users.toString());

        return users;
    }

    private List<Logs> getLogsUsers(DataSnapshot singleSnap, Context requireContext) {
        List<Logs> logs = new ArrayList<>();
        for (DataSnapshot shotLog : singleSnap
                .child(requireContext.getString(R.string.db_node_logs))
                .getChildren()) {

            Logs log = new Logs();

            Map<String, Object> objectMap = (Map<String, Object>) shotLog.getValue();
            log.setMobileusers_id(objectMap.get("mobileusers_id").toString());
            log.setTitle(objectMap.get("title").toString());
            log.setMessage(objectMap.get("message").toString());
            log.setTimestamp(objectMap.get("timestamp").toString());
            log.setVideo(objectMap.get("video").toString());
            log.setImages(objectMap.get("images").toString());

            logs.add(log);
        }

        return logs;
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