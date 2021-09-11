package com.sti.research.personalsafetyalert.ui.screen.home;

import static com.sti.research.personalsafetyalert.util.Support.rippleEffect;
import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHomeBinding;
import com.sti.research.personalsafetyalert.ui.Hostable;
import com.sti.research.personalsafetyalert.util.Support;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;

    private Hostable hostable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(HomeFragmentViewModel.class);
        binding.setPopupListener(this::clearAllPopup);
        navigate();
        navigateWithGestureDetector();
    }

    private void navigate() {
        binding.homeEditMessageView.setOnClickListener(v -> {
            Bubble.message(requireActivity(), "Edit message");
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void navigateWithGestureDetector() {

        final GestureDetector contactGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Bubble.message(requireActivity(), "Contact");
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
                        Bubble.message(requireActivity(), "Edit message");
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
                        Bubble.message(requireActivity(), "Visualize message");
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

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        requireActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.action_settings) {
//            Bubble.message(requireActivity(), "Settings");
//            hostable.onInflate(requireView(), "tag_fragment_settings");
//            return true;
//        } else if (item.getItemId() == R.id.action_not_working) {
//            Bubble.message(requireActivity(), "Not Working ?");
//            return true;
//        } else if (item.getItemId() == R.id.action_help) {
//            Bubble.message(requireActivity(), "Help");
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (!(activity instanceof Hostable)) {
            assert activity != null;
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement Hostable interface.");
        }
        hostable = (Hostable) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostable = null;
    }

}