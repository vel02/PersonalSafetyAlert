package com.sti.research.personalsafetyalert.ui.screen.contact;

import static com.sti.research.personalsafetyalert.util.Utility.*;
import static com.sti.research.personalsafetyalert.util.Utility.isNotEmpty;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentContactBinding;
import com.sti.research.personalsafetyalert.model.Contact;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.WaitResultManager;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactMessageToPreference;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactStoreSinglePerson;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentContactBinding binding;
    private ContactFragmentViewModel viewModel;

    private HostScreen hostScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(ContactFragmentViewModel.class);
        binding.setPopupListener(this::clearAllPopup);
        navigate();
        initRadioButtonBehavior();
        subscribeObservers();
        restoreSinglePersonContact();
    }

    private void restoreSinglePersonContact() {
        Contact contact = ContactStoreSinglePerson.getInstance().restoreContactSinglePerson(requireActivity());
        if (contact != null && isNotEmpty(contact.getNumber()) && isNotEmpty(contact.getEmail())) {
            binding.contactMobileNumber.setText(contact.getNumber());
            binding.contactEmail.setText(contact.getEmail());
            this.confirmBehavior();
        }
    }

    private void subscribeObservers() {
        viewModel.observedRadioSelected().removeObservers(getViewLifecycleOwner());
        viewModel.observedRadioSelected().observe(getViewLifecycleOwner(), selected -> {
            switch (selected) {
                case "Send to one specific contact":
                    binding.contactSinglePerson.setVisibility(View.VISIBLE);
                    binding.contactContactListCard.setVisibility(View.GONE);
                    break;
                case "Send to list of contacts":
                    binding.contactSinglePerson.setVisibility(View.GONE);
                    binding.contactContactListCard.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void navigate() {
        binding.contactRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selected = group.getCheckedRadioButtonId();
            RadioButton button = binding.getRoot().findViewById(selected);
            viewModel.setRadioSelected(button.getTag().toString());
        });

        binding.contactConfirm.setOnClickListener(v -> {

            String contentNumber = binding.contactMobileNumber.getText().toString();
            String contentEmail = binding.contactEmail.getText().toString();

            String number = "";
            String email = "";

            if (contentNumber.isEmpty() && contentEmail.isEmpty()) {
                Popup.message(requireView(), "Using SMS feature required valid number and email. " +
                        "Please, consider registering a valid contact.");
            } else {
                if (contentNumber.isEmpty() || (!contentNumber.startsWith("0") || contentNumber.length() < 11)) {
                    Popup.message(requireView(), "Using SMS feature required valid number and email. " +
                            "Please, consider registering a valid contact.");
                } else number = contentNumber;

                if (contentEmail.isEmpty() || !(isValidEmail(contentEmail))) {
                    Popup.message(requireView(), "Using SMS feature required valid number and email. " +
                            "Please, consider registering a valid contact.");
                } else if (isValidEmail(contentEmail)) {
                    email = contentEmail;
                }
            }

            if (isNotEmpty(number) && isNotEmpty(email)) {//satisfied
                this.confirmBehavior();
                Contact contact = new Contact(number, email);
                ContactStoreSinglePerson.getInstance()
                        .storeContactSinglePerson(requireActivity(), contact);
            }

        });

        binding.contactEdit.setOnClickListener(v -> {
            if (binding.contactConfirm.getVisibility() == View.GONE) {
                this.editBehavior();
            }
        });

        binding.contactAddContact.setOnClickListener(v -> {
            hostScreen.onInflate(v, getString(R.string.tag_fragment_contact_to_add_contact));
        });
    }

    private void initRadioButtonBehavior() {
        String radioSelected = ContactMessageToPreference.getInstance().getMessageToRadioSelectState(requireActivity());
        binding.contactRadioGroup.check(radioSelected.equals(getString(R.string.txt_single_person))
                ? binding.contactRadioSinglePerson.getId() : binding.contactRadioContactList.getId());

        if (binding.contactRadioSinglePerson.isChecked()) {
            viewModel.setRadioSelected(getString(R.string.txt_single_person));
        } else if (binding.contactRadioContactList.isSelected()) {
            viewModel.setRadioSelected(getString(R.string.txt_contact_list));
        }
    }

    private void clearAllPopup(View view) {
        PopupMenu popup = new PopupMenu(requireActivity(), view);
        popup.inflate(R.menu.menu_popup_more);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_clear_all) {
                Bubble.message(requireActivity(), "Clear All!");
                return true;
            }
            return false;
        });
        popup.show();
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

    public String formatPhoneNumber(String number) {
        //09166718943 -> +639166718943
        return number.replaceFirst("0", "+63");
    }

    public boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void editBehavior() {
        binding.contactMobileNumber.setEnabled(true);
        binding.contactEmail.setEnabled(true);
        binding.contactConfirm.setVisibility(View.VISIBLE);
        binding.contactEdit.setVisibility(View.GONE);
    }

    private void confirmBehavior() {
        binding.contactMobileNumber.setEnabled(false);
        binding.contactEmail.setEnabled(false);
        binding.contactConfirm.setVisibility(View.GONE);
        binding.contactEdit.setVisibility(View.VISIBLE);
    }

}