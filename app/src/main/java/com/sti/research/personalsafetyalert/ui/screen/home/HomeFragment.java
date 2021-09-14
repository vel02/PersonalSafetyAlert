package com.sti.research.personalsafetyalert.ui.screen.home;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHomeBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.Support;
import com.sti.research.personalsafetyalert.util.screen.home.HomeSwitchPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    private static final String TAG = "test";

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;

    private HostScreen hostScreen;


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

        /*
            if permission is not permitted
                prompt UI permission
                if user chose to deny permitting it
                    let them use the app without it
                else if user chooses to accept permission
                    let them use the app

            NOTE
            1.

         */

        binding.setPopupListener(this::clearAllPopup);
        navigate();
        navigateWithGestureDetector();
        initAlertCheckedBehaviour();
        subscribeObservers();
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

        viewModel.observedPermissionLocationState().removeObservers(getViewLifecycleOwner());
        viewModel.observedPermissionLocationState().observe(getViewLifecycleOwner(), permission -> {
            if (permission == PackageManager.PERMISSION_DENIED)
                hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_home_to_permission));
        });
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
                        Popup.message(requireView(), getString(R.string.desc_long_press_contact));
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
                        Popup.message(requireView(), getString(R.string.desc_long_press_edit_message));
                    }
                });

        final GestureDetector visualMessageGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_visual_message));
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Popup.message(requireView(), getString(R.string.desc_long_press_visual_message));
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
                Bubble.message(requireActivity(), "Clear All");
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