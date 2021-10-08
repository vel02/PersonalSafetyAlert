package com.sti.research.personalsafetyalert.ui.screen.home;

import static com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentViewModel.LocationServiceState.ACTIVATE_OFF;
import static com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentViewModel.LocationServiceState.ACTIVATE_ON;
import static com.sti.research.personalsafetyalert.util.Utility.*;
import static com.sti.research.personalsafetyalert.util.Utility.Dialog.*;

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
import com.sti.research.personalsafetyalert.adapter.view.message.MessageRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.FragmentHomeBinding;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.model.single.Contact;
import com.sti.research.personalsafetyalert.repository.PermissionRepository;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.LocationServiceListener;
import com.sti.research.personalsafetyalert.ui.NavigatePermission;
import com.sti.research.personalsafetyalert.util.MessageComparator;
import com.sti.research.personalsafetyalert.util.Support;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactStoreSinglePerson;
import com.sti.research.personalsafetyalert.util.screen.contact.SelectPreferredContactPreference;
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

    private LocationServiceListener locationServiceListener;

    private List<com.sti.research.personalsafetyalert.model.list.Contact> contacts;

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

    private void subscribePermissionObserver() {
        viewModel.observedPermissionRequiredState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionRequiredState().observe(getViewLifecycleOwner(), status -> {
            Log.d(TAG, "HOME FRAGMENT: SUBSCRIBE RESULT PERMISSION = " + status);
            switch (status) {
                case NONE:
                case PARTIAL:
                    hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_home_to_permission));
                    break;
            }
        });
    }

    private void subscribeObservers() {
        viewModel.observedContactList().removeObservers(getViewLifecycleOwner());
        viewModel.observedContactList().observe(getViewLifecycleOwner(), contacts -> this.contacts = contacts);

        viewModel.observedLocationServiceState().removeObservers(getViewLifecycleOwner());
        viewModel.observedLocationServiceState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case ACTIVATE_ON:
                    if (this.validation()
                            && this.checkInternetConnection()
                            && this.checkGPSConnection()) {
                        Log.d(TAG, "HOME FRAGMENT: ACTIVATED");
                        if (this.isNotificationLocationNotActivated()) {
                            locationServiceListener.requestNotificationLocation();
                            binding.homeSwitch.setText(getString(R.string.txt_stop_safety));
                        }
                    } else viewModel.setLocationServiceState(ACTIVATE_OFF);
                    break;

                case ACTIVATE_OFF:
                    Log.d(TAG, "HOME FRAGMENT: DEACTIVATED");
                    if (this.isNotificationLocationActivated()) {
                        locationServiceListener.removeNotificationLocation();
                        binding.homeSwitch.setText(getString(R.string.txt_start_safety));
                    }
                    break;

            }
        });

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

    private boolean checkInternetConnection() {
        if (Connection.isWifiConnected(requireActivity()) || Connection.isMobileConnected(requireActivity())) {
            return true;
        } else {
            if (binding.homeSwitch.isChecked()) binding.homeSwitch.setChecked(false);
            initAlertDialogBuilder(requireActivity(), R.layout.dialog_internet_layout);
            return false;
        }
    }

    private boolean checkGPSConnection() {
        if (Connection.isGPSEnabled(requireActivity())) {
            return true;
        } else {
            if (binding.homeSwitch.isChecked()) binding.homeSwitch.setChecked(false);
            initAlertDialogBuilder(requireActivity(), R.layout.dialog_gps_layout);
            return false;
        }
    }

    private void checkRegisteredContact() {
        initAlertDialogBuilder(requireActivity(), R.layout.dialog_register_contact_layout);
    }

    private boolean validation() {
        switch (SelectPreferredContactPreference.getInstance().getSelectPreferredContact(requireActivity())) {
            case SelectPreferredContactPreference.SELECT_PREFERRED_CONTACT_SINGLE:
                Contact contact = ContactStoreSinglePerson.getInstance().restoreContactSinglePerson(requireActivity());
                if (contact == null || contact.getNumber().isEmpty()) {
                    if (binding.homeSwitch.isChecked()) binding.homeSwitch.setChecked(false);
                    //dialog
                    this.checkRegisteredContact();
                    return false;
                }
                return true;
            case SelectPreferredContactPreference.SELECT_PREFERRED_CONTACT_MULTIPLE:
                if (contacts == null || contacts.size() < 1) {
                    if (binding.homeSwitch.isChecked()) binding.homeSwitch.setChecked(false);
                    //dialog
                    this.checkRegisteredContact();
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    private void initAlertCheckedBehaviour() {
        this.toggleAlertBehaviour();
        binding.homeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.setLocationServiceState(isChecked ? ACTIVATE_ON : ACTIVATE_OFF));
    }

    private void toggleAlertBehaviour() {
        boolean activated = this.isNotificationLocationActivated();
        binding.homeSwitch.setChecked(activated);
        if (activated) {
            binding.homeSwitch.setText(getString(R.string.txt_stop_safety));
        } else binding.homeSwitch.setText(getString(R.string.txt_start_safety));
    }

    private boolean isNotificationLocationActivated() {
        return HomeSwitchPreference.getInstance().getSwitchStateState(requireActivity());
    }

    private boolean isNotificationLocationNotActivated() {
        return !HomeSwitchPreference.getInstance().getSwitchStateState(requireActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.loadMessagesDatabase();
        viewModel.loadContactList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!navigate.checkLocationPermission()
                && !navigate.checkSendSMSPermission()
                && !navigate.checkRecordAudioPermission()
                && !navigate.checkStoragePermission()) {
            Log.d(TAG, "HOME FRAGMENT: PERMISSION COMPLETED");
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

        if (!(activity instanceof LocationServiceListener)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement LocationServiceListener interface.");
        }
        locationServiceListener = (LocationServiceListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
        navigate = null;
        locationServiceListener = null;
    }

}