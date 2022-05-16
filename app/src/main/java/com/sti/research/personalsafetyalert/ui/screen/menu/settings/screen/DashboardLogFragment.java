package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentDashboardLogBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.android.support.DaggerFragment;


public class DashboardLogFragment extends DaggerFragment {

    private static final String TAG = "RETRIEVING!";
    private FragmentDashboardLogBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardLogBinding.inflate(inflater);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://personalsafetyalert-a5eef-default-rtdb.firebaseio.com/").getReference();

        Query query = reference.child(getString(R.string.db_node_admin))
                .orderByKey()
                .equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnap : snapshot.getChildren()) {
                    User user = new User();

                    Map<String, Object> objectMap = (Map<String, Object>) singleSnap.getValue();
                    user.setEmail(objectMap.get("email").toString());

                    List<MobileUser> mobileUsers = getMobileUsers(singleSnap, requireContext());
                    user.setUsers(mobileUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
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
        Log.e(TAG, "onDataChange: mobileusers: " + users.toString());

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


}