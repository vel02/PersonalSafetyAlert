package com.sti.research.personalsafetyalert.ui.screen.contact.update;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentUpdateContactBinding;
import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.screen.contact.add.AddContactTextWatcher;
import com.sti.research.personalsafetyalert.util.screen.manager.MobileNetworkManager;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class UpdateContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentUpdateContactBinding binding;
    private UpdateContactFragmentViewModel viewModel;

    private Contact contact;

    private MobileNetworkManager mobileNetworkManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateContactBinding.inflate(inflater);
        if (contact != null) {
            binding.updateContactPersonName.setText(contact.getName());
            binding.updateContactEmail.setText(contact.getEmail());
            binding.updateContactPhoneNumber.setText(contact.getMobileNumber());
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(UpdateContactFragmentViewModel.class);
        mobileNetworkManager = new MobileNetworkManager();
        navigate();
    }

    private void navigate() {

        AddContactTextWatcher watcher = new AddContactTextWatcher(
                binding.updateContactPersonName,
                binding.updateContactPhoneNumber,
                binding.updateContactEmail,
                null);

        binding.updateContactPersonName.addTextChangedListener(watcher);
        binding.updateContactPhoneNumber.addTextChangedListener(watcher);
        binding.updateContactEmail.addTextChangedListener(watcher);

    }

    private void resetUiBehavior() {
        binding.updateContactPersonName.clearFocus();
        binding.updateContactPhoneNumber.clearFocus();
        binding.updateContactEmail.clearFocus();
        hideSoftKeyboard(requireParentFragment());
    }

    public boolean isValidEmail(CharSequence target) {

        String EMAIL_VALIDATION_GMAIL = "gmail.com";
        String EMAIL_VALIDATION_YAHOO = "yahoo.com";
        String EMAIL_VALIDATION_PROTONMAIL = "protonmail.com";

        if (Patterns.EMAIL_ADDRESS.matcher(target).matches()) {

            int beginIndex = target.toString().indexOf("@") + 1;
            int lastIndex = target.toString().length();

            String email_postfix = target.toString().substring(beginIndex, lastIndex);
            return email_postfix.equals(EMAIL_VALIDATION_GMAIL)
                    || email_postfix.equals(EMAIL_VALIDATION_YAHOO)
                    || email_postfix.equals(EMAIL_VALIDATION_PROTONMAIL);

        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_contact, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            viewModel.deleteContact(contact);
            Popup.message(requireView(), "Contact deleted.");
            requireActivity().onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_done) {
            String contentName = binding.updateContactPersonName.getText().toString();
            String contentNumber = binding.updateContactPhoneNumber.getText().toString();
            String contentEmail = binding.updateContactEmail.getText().toString();

            if (((!contentNumber.startsWith("09") || contentNumber.length() < 11)) || !(isValidEmail(contentEmail))) {
                Popup.message(requireView(), "Using SMS feature required valid number and email. " +
                        "Please, consider registering a valid contact.");
                hideSoftKeyboard(requireParentFragment());
                return true;
            }

            mobileNetworkManager.validate(contentNumber);
            String network = mobileNetworkManager.getNetwork();

            contact.setName(contentName);
            contact.setMobileNumber(contentNumber);
            contact.setMobileNetwork(network);
            contact.setEmail(contentEmail);

            //update contact
            viewModel.updateContact(contact);

            resetUiBehavior();
            Popup.message(requireView(), "Contact updated.");
            requireActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contact = UpdateContactFragmentArgs.fromBundle(getArguments()).getContact();
    }

}