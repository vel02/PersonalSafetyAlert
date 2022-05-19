package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.userlog.UserLogListRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentUserLogListBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.User;
import com.sti.research.personalsafetyalert.model.UserLog;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.DashboardLogFragment;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.screen.main.UserLogPreference;
import com.sti.research.personalsafetyalert.util.screen.permission.MobileUserIDPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.android.support.DaggerFragment;


public class UserLogListFragment extends DaggerFragment {

    private FragmentUserLogListBinding binding;

    private HostScreen hostScreen;
//    private List<UserLog> userLogs;

    private MobileUser mobileUser;


    private UserLogListRecyclerAdapter adapter;

    public void onUserLogListDataReceiver(UserLog log) {
        Log.e("USERLOGLIST", "onUserLogListDataReceiver: " + log);
        hostScreen.onInflate(requireView(), "tag_fragment_user_log_list_to_user_log_list_view", log);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserLogListBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerAdapter();

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(
                        Constants.ResourceManager.RESOURCES_CODE_M,
                        Constants.ResourceManager.RESOURCES_CODE_P)
                .addOnCompleteListener(task -> {


                    //DATABASE FIREBASE
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        getDatabaseData();
                    }
                });
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

            List<UserLog> userLogs = getUserLogs(shotUser, requireContext);
            mobileUser.setUserLogs(userLogs);
            users.add(mobileUser);

            if (mobileUser.getId().equals(MobileUserIDPreference.getInstance().getMobileUserIDPreference(requireContext))) {
//                adapter.refresh(user);
                Log.e("MOBILEUSER", "getMobileUsers: " + mobileUser);
                this.mobileUser = mobileUser;

                Log.e("USERLOG", "onViewCreated: " + this.mobileUser.getUserLogs());
                adapter.refresh(mobileUser.getUserLogs());
            }

        }

        return users;
    }

    private List<UserLog> getUserLogs(DataSnapshot singleSnap, Context requireContext) {
        List<UserLog> userLogs = new ArrayList<>();

        for (DataSnapshot shotLog : singleSnap
                .child(requireContext.getString(R.string.db_node_userlogs))
                .getChildren()) {

            Map<String, Object> objectMap = (Map<String, Object>) shotLog.getValue();

            UserLog userLog = new UserLog();

            userLog.setName(objectMap.get("name").toString());
            userLog.setEmail(objectMap.get("email").toString());
            userLog.setPhoneNumber(objectMap.get("phoneNumber").toString());
            userLog.setAudioPath(objectMap.get("audioPath").toString());
            userLog.setTitle(objectMap.get("title").toString());
            userLog.setMessage(objectMap.get("message").toString());
            userLog.setLatitude(objectMap.get("latitude").toString());
            userLog.setLongitude(objectMap.get("longitude").toString());
            userLog.setLocation(objectMap.get("location").toString());
            userLog.setTimestamp(objectMap.get("timestamp").toString());

            userLogs.add(userLog);
        }
        return userLogs;
    }

    private List<Logs> getLogsUsers(DataSnapshot singleSnap, Context requireContext) {
        List<Logs> logs = new ArrayList<>();
        for (DataSnapshot shotLog : singleSnap
                .child(requireContext.getString(R.string.db_node_logs))
                .getChildren()) {

            Logs log = new Logs();

            Map<String, Object> objectMap = (Map<String, Object>) shotLog.getValue();
            log.setMobileusers_id(objectMap.get("mobileusers_id").toString());
            log.setLog_id(objectMap.get("log_id").toString());
            log.setTitle(objectMap.get("title").toString());
            log.setMessage(objectMap.get("message").toString());
            log.setTimestamp(objectMap.get("timestamp").toString());

            if (objectMap.get("video") != null) log.setVideo(objectMap.get("video").toString());
            log.setImages(objectMap.get("images").toString());

            logs.add(log);
        }

        return logs;
    }

    private void initRecyclerAdapter() {
        binding.rvUserLogList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserLogListRecyclerAdapter();
        binding.rvUserLogList.setAdapter(adapter);
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

//        userLogs = UserLogPreference.getInstance().getUserLogOutput(requireContext());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}