package com.sti.research.personalsafetyalert.ui.screen.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentContactBinding;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureActionBarTitle();
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(ContactFragmentViewModel.class);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void configureActionBarTitle() {
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle(R.string.title_contact);
    }
}