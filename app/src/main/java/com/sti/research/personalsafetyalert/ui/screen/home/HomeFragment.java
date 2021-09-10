package com.sti.research.personalsafetyalert.ui.screen.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentHomeBinding;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HomeFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(HomeFragmentViewModel.class);
        binding.setPopupListener(this::clearAllPopup);
        navigateWithGestureDetector();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void navigateWithGestureDetector() {

        final GestureDetector contactGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Toast.makeText(requireActivity(), "Contact", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Snackbar.make(requireView(), getString(R.string.desc_long_press_contact),
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.txt_hide, v -> {
                                }).show();
                    }
                });

        final GestureDetector messageGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Toast.makeText(requireActivity(), "Edit message", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Snackbar.make(requireView(), getString(R.string.desc_long_press_edit_message),
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.txt_hide, v -> {
                                }).show();
                    }
                });

        final GestureDetector visualMessageGestureDetector = new GestureDetector(requireContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Toast.makeText(requireActivity(), "Visualize message", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Snackbar.make(requireView(), getString(R.string.desc_long_press_visual_message),
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.txt_hide, v -> {
                                }).show();
                    }
                });

        binding.homeContact.setOnTouchListener((v, event) -> {
            contactGestureDetector.onTouchEvent(event);
            return rippleEffect(v, event);
        });

        binding.homeEditMessageButton.setOnTouchListener((v, event) -> {
            messageGestureDetector.onTouchEvent(event);
            return rippleEffect(v, event);
        });

        binding.homeVisualMessage.setOnTouchListener((v, event) -> {
            visualMessageGestureDetector.onTouchEvent(event);
            return rippleEffect(v, event);
        });

    }

    //================== Supporting Methods ==================//

    private boolean rippleEffect(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                return true;
            default:
                return false;
        }
    }

    private void clearAllPopup(View view) {
        PopupMenu popup = new PopupMenu(requireActivity(), view);
        popup.inflate(R.menu.menu_popup_more);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_clear_all) {
                Toast.makeText(requireActivity(), "Clear All", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }
}