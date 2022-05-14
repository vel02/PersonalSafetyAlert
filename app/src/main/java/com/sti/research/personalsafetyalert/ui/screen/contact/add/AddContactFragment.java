package com.sti.research.personalsafetyalert.ui.screen.contact.add;

import static com.sti.research.personalsafetyalert.util.Utility.*;
import static com.sti.research.personalsafetyalert.util.Utility.hideSoftKeyboard;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.util.Log;
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
import com.sti.research.personalsafetyalert.util.test.contact.ContactVO;
import com.sti.research.personalsafetyalert.util.test.contact.ContactVORecyclerAdapter;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class AddContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentAddContactBinding binding;
    private AddContactFragmentViewModel viewModel;

    private MobileNetworkManager mobileNetworkManager;

    private ContactVORecyclerAdapter adapter;

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

        binding.rvContactList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new ContactVORecyclerAdapter();
        binding.rvContactList.setAdapter(adapter);

        navigate();
    }


    private void retrieveMobileContacts() {
//        List<ContactVO> contactVOList = new ArrayList();

        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        List<ContactVO> userList = new ArrayList<>();

        String lastPhoneName = " ";
        if (phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String contactId = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String photoUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (!name.equalsIgnoreCase(lastPhoneName)) {
                    lastPhoneName = name;
                    ContactVO user = new ContactVO();
                    user.setContactName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    user.setContactNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    userList.add(user);
                    Log.d("getContactsList", name + "---" + phoneNumber + " -- " + contactId + " -- " + photoUri);
                }
            }
        }
        phones.close();

        adapter.refresh(userList);

    }

    private void navigate() {


        //CONTACT LIST REPLACEMENT CODE...
        //STEP #1: RETRIEVE CONTACTS THROUGH MOBILE PHONE SYSTEM
        retrieveMobileContacts();
        binding.addContactDone.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        //STEP #2: DISPLAY CONTACTS RETRIEVED TO RECYCLERVIEW
        //STEP #3: APPLY MARKING FUNCTIONALITY
        //          IF MARKED
        //              ADDED TO A LIST OF CONTACTS TO BE A RECEIVER
        //          ELSE
        //              DELETE TO A LIST OF CONTACTS TO BE A RECEIVER


//
//        AddContactTextWatcher watcher = new AddContactTextWatcher(
//                binding.addContactPersonName,
//                binding.addContactPhoneNumber,
//                binding.addContactEmail,
//                binding.addContactDone);
//
//        binding.addContactPersonName.addTextChangedListener(watcher);
//        binding.addContactPhoneNumber.addTextChangedListener(watcher);
//        binding.addContactEmail.addTextChangedListener(watcher);
//
//
//        binding.addContactDone.setOnClickListener(v -> {
//
//            String contentName = binding.addContactPersonName.getText().toString();
//            String contentNumber = binding.addContactPhoneNumber.getText().toString();
//            String contentEmail = binding.addContactEmail.getText().toString();
//
//            if (((!contentNumber.startsWith("09") || contentNumber.length() < 11)) || !(isValidEmail(contentEmail))) {
//                Popup.message(requireView(), "Using SMS feature required valid number and email. " +
//                        "Please, consider registering a valid contact.");
//                return;
//            }
//
//            mobileNetworkManager.validate(contentNumber);
//            String network = mobileNetworkManager.getNetwork();
//
//            Contact contact = new Contact();
//            contact.setName(contentName);
//            contact.setMobileNumber(contentNumber);
//            contact.setMobileNetwork(network);
//            contact.setEmail(contentEmail);
//
//            //add to database
//            viewModel.insertContact(contact);
//
//            resetUiBehavior();
//            Popup.message(requireView(), "Contact added.");
//            requireActivity().onBackPressed();
//
//        });
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

    private void resetUiBehavior() {
//        binding.addContactPersonName.clearFocus();
//        binding.addContactPhoneNumber.clearFocus();
//        binding.addContactEmail.clearFocus();
        hideSoftKeyboard(requireParentFragment());
    }


}