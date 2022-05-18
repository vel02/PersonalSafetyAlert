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

import com.sti.research.personalsafetyalert.adapter.view.userlog.UserLogListRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentUserLogListBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.UserLog;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.screen.main.UserLogPreference;

import java.util.List;

import dagger.android.support.DaggerFragment;


public class UserLogListFragment extends DaggerFragment {

    private FragmentUserLogListBinding binding;

    private HostScreen hostScreen;
    private List<UserLog> userLogs;

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
        if (userLogs != null && userLogs.size() > 0) {
            adapter.refresh(userLogs);
            for (UserLog userLog : userLogs) {
                Log.e("USERLOG", "onViewCreated: " + userLog);
            }
        }


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

        userLogs = UserLogPreference.getInstance().getUserLogOutput(requireContext());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}