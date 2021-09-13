package com.sti.research.personalsafetyalert.ui.screen.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentContactBinding;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactMessageToPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ContactFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentContactBinding binding;
    private ContactFragmentViewModel viewModel;

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
        navigate();
        initRadioButtonBehavior();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observedRadioSelected().removeObservers(getViewLifecycleOwner());
        viewModel.observedRadioSelected().observe(getViewLifecycleOwner(), selected -> {
            switch (selected) {
                case "Single person":
                    binding.contactSinglePerson.setVisibility(View.VISIBLE);
                    binding.contactContactListCard.setVisibility(View.GONE);
                    break;
                case "Contact list":
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

}