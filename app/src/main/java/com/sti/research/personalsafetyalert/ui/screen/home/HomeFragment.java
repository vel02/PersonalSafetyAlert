package com.sti.research.personalsafetyalert.ui.screen.home;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.MessageRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentHomeBinding;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.NavigatePermission;
import com.sti.research.personalsafetyalert.util.MessageComparator;
import com.sti.research.personalsafetyalert.util.Support;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.util.screen.home.HomeInitialMessage;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    private static final String TAG = "test";

    public void onMessageDataReceiver(Message message) {
        message.setTimestamp(String.valueOf(getCurrentTimeAndDateInMillis()));
        viewModel.update(message);
        viewModel.setMessage(message);
    }

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;

    private HostScreen hostScreen;
    private NavigatePermission navigate;
    private MessageRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureActionBarTitle();
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(HomeFragmentViewModel.class);
        binding.setPopupListener(this::clearAllPopup);

        navigate();
        navigateWithGestureDetector();
        subscribeObservers();

        initMessageRecyclerAdapter();
        initAlertCheckedBehaviour();
        initMessages();
    }

    private void initMessageRecyclerAdapter() {
        binding.homeMessageTextList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new MessageRecyclerAdapter();
        binding.homeMessageTextList.setAdapter(adapter);
    }

    private void initAlertCheckedBehaviour() {
        binding.homeSwitch.setChecked(HomeSwitchPreference.getInstance().getSwitchStateState(requireActivity()));
    }

    private void subscribeObservers() {
        viewModel.observedAlertChecked().removeObservers(getViewLifecycleOwner());
        viewModel.observedAlertChecked().observe(getViewLifecycleOwner(), isChecked -> {
            if (isChecked) binding.homeSwitch.setText(R.string.txt_stop_safety);
            else binding.homeSwitch.setText(R.string.txt_start_safety);
        });

        viewModel.observedMessages().removeObservers(getViewLifecycleOwner());
        viewModel.observedMessages().observe(getViewLifecycleOwner(), messages -> {
            if (messages != null) {
                adapter.refresh(messages);
                Collections.sort(messages, new MessageComparator());
                if (messages.size() > 0) {
                    Message message = messages.get(messages.size() - 1);
                    binding.homeMessageDisplay.setText(message.getMessage());
                }
            }
        });

        viewModel.observedMessage().removeObservers(getViewLifecycleOwner());
        viewModel.observedMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                binding.homeMessageDisplay.setText(message.getMessage());
            }
        });

    }

    private void subscribePermissionObserver() {
        viewModel.observedPermissionRequiredState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionRequiredState().observe(getViewLifecycleOwner(), status -> {
            Log.d(TAG, "subscribeObservers: status " + status);
            switch (status) {
                case NONE:
                case PARTIAL:
                    hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_home_to_permission));
                    break;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.loadMessagesDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!navigate.checkLocationPermission()
                && !navigate.checkSendSMSPermission()
                && !navigate.checkRecordAudioPermission()
                && !navigate.checkStoragePermission()) {
            Log.d(TAG, "home completed called");
            viewModel.setPermissionRequiredState(PermissionRepository.RequiredPermissionsState.COMPLETED);
        }

        subscribePermissionObserver();
    }

    private void navigate() {

        binding.homeEditMessageView.setOnClickListener(v ->
                hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_message)));

        binding.homeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAlertChecked(isChecked));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void navigateWithGestureDetector() {

        final GestureDetector contactGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_contact));
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Popup.messageWithAction(requireView(), getString(R.string.desc_long_press_contact));
                    }
                });

        final GestureDetector messageGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_message));
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Popup.messageWithAction(requireView(), getString(R.string.desc_long_press_edit_message));
                    }
                });

        final GestureDetector visualMessageGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        viewModel.setSelectedMessage(binding.homeMessageDisplay.getText().toString());
                        hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_visual_message));
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Popup.messageWithAction(requireView(), getString(R.string.desc_long_press_visual_message));
                    }
                });

        binding.homeContact.setOnTouchListener((v, event) -> {
            contactGestureDetector.onTouchEvent(event);
            return Support.rippleEffect(v, event);
        });

        binding.homeEditMessageButton.setOnTouchListener((v, event) -> {
            messageGestureDetector.onTouchEvent(event);
            return Support.rippleEffect(v, event);
        });

        binding.homeVisualMessage.setOnTouchListener((v, event) -> {
            visualMessageGestureDetector.onTouchEvent(event);
            return Support.rippleEffect(v, event);
        });

    }


    //================== Supporting Methods ==================//

    private void clearAllPopup(View view) {
        PopupMenu popup = new PopupMenu(requireActivity(), view);
        popup.inflate(R.menu.menu_popup_more);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_clear_all) {
                viewModel.deleteAll();
                new WaitResultManager(
                        WaitResultManager.WAIT_LONG,
                        WaitResultManager.WAIT_INTERVAL, () -> {
                    binding.homeMessageDisplay.setText(R.string.txt_sample_text_00);
                    viewModel.setSelectedMessage("");
                    viewModel.loadMessagesDatabase();
                }).start();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void configureActionBarTitle() {
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle(getString(R.string.app_name));
    }

    private void initMessages() {
        if (HomeInitialMessage.getInstance().getInitializeMessagesState(requireActivity())) {
            HomeInitialMessage.getInstance().setInitializeMessagesState(requireActivity(), false);

            List<Message> messages = new ArrayList<>();

            Message message = new Message();
            message.setMessage(getResources().getString(R.string.txt_sample_text_00));
            message.setTimestamp("1");
            messages.add(message);

            message = new Message();
            message.setMessage(getResources().getString(R.string.txt_sample_text_01));
            message.setTimestamp("0");
            messages.add(message);

            message = new Message();
            message.setMessage(getResources().getString(R.string.txt_sample_text_02));
            message.setTimestamp("0");
            messages.add(message);

            message = new Message();
            message.setMessage(getResources().getString(R.string.txt_sample_text_03));
            message.setTimestamp("0");
            messages.add(message);

            message = new Message();
            message.setMessage(getResources().getString(R.string.txt_sample_text_04));
            message.setTimestamp("0");
            messages.add(message);

            viewModel.insert(messages);
        }
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