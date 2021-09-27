package com.sti.research.personalsafetyalert.ui.screen.contact.add;

import static com.sti.research.personalsafetyalert.util.Utility.*;
import static com.sti.research.personalsafetyalert.util.Utility.hideSoftKeyboard;
import static com.sti.research.personalsafetyalert.util.Utility.isNotEmpty;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentAddContactBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.screen.contact.add.AddContactTextWatcher;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class AddContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentAddContactBinding binding;
    private AddContactFragmentViewModel viewModel;

    private HostScreen hostScreen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(AddContactFragmentViewModel.class);
        navigate();
    }

    private void navigate() {

        AddContactTextWatcher watcher = new AddContactTextWatcher(
                binding.addContactPersonName,
                binding.addContactPhoneNumber,
                binding.addContactEmail,
                binding.addContactDone);

        binding.addContactPersonName.addTextChangedListener(watcher);
        binding.addContactPhoneNumber.addTextChangedListener(watcher);
        binding.addContactEmail.addTextChangedListener(watcher);

        binding.addContactDone.setOnClickListener(v -> {

            resetUiBehavior();
            new WaitResultManager(WaitResultManager.WAIT_LONG, WaitResultManager.WAIT_INTERVAL, () -> {
                Popup.message(requireView(), "Contact added.");
                hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_add_contact_to_contact));
            }).start();
        });
    }

    private void resetUiBehavior() {
        binding.addContactPersonName.clearFocus();
        binding.addContactPhoneNumber.clearFocus();
        binding.addContactEmail.clearFocus();
        hideSoftKeyboard(requireParentFragment());
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