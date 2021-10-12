package com.sti.research.personalsafetyalert.ui.screen.contact.add;

import static com.sti.research.personalsafetyalert.util.Utility.*;
import static com.sti.research.personalsafetyalert.util.Utility.hideSoftKeyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentAddContactBinding;
import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.screen.contact.add.AddContactTextWatcher;
import com.sti.research.personalsafetyalert.util.screen.manager.MobileNetworkManager;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class AddContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentAddContactBinding binding;
    private AddContactFragmentViewModel viewModel;

    private MobileNetworkManager mobileNetworkManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(AddContactFragmentViewModel.class);
        mobileNetworkManager = new MobileNetworkManager();
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

            String contentName = binding.addContactPersonName.getText().toString();
            String contentNumber = binding.addContactPhoneNumber.getText().toString();
            String contentEmail = binding.addContactEmail.getText().toString();

            if (((!contentNumber.startsWith("09") || contentNumber.length() < 11)) || !(isValidEmail(contentEmail))) {
                Popup.message(requireView(), "Using SMS feature required valid number and email. " +
                        "Please, consider registering a valid contact.");
                return;
            }

            mobileNetworkManager.validate(contentNumber);
            String network = mobileNetworkManager.getNetwork();

            Contact contact = new Contact();
            contact.setName(contentName);
            contact.setMobileNumber(contentNumber);
            contact.setMobileNetwork(network);
            contact.setEmail(contentEmail);

            //add to database
            viewModel.insertContact(contact);

            resetUiBehavior();
            Popup.message(requireView(), "Contact added.");
            requireActivity().onBackPressed();

        });
    }

    public boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void resetUiBehavior() {
        binding.addContactPersonName.clearFocus();
        binding.addContactPhoneNumber.clearFocus();
        binding.addContactEmail.clearFocus();
        hideSoftKeyboard(requireParentFragment());
    }

}