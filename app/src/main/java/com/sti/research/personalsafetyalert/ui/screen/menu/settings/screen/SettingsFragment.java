package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentSettingsBinding;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.screen.main.UsernamePreference;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SettingsFragment extends DaggerFragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String name = UsernamePreference.getInstance().getUsernameInput(requireActivity());
        if (name.isEmpty()) binding.displayInputName.setText(R.string.txt_anonymous);
        else binding.displayInputName.setText(name);

        binding.layoutInputName.setOnClickListener(v -> initUserName());


        binding.layoutShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hi, did you know?" +
                    "\n\nThere is an app that can send emergency message to your friends and love ones with less effort, when you're not busy, try use Personal Safety Alert for free." +
                    "\n\nCheck this out!" +
                    "\n" +
                    "\nhttps://github.com/vel02/PersonalSafetyAlert";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });
    }

    private void initUserName() {
        androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(requireActivity(), R.style.PersonalSafetyAlert_AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_user_name, null);
        TextView positiveButton = view.findViewById(R.id.dialog_button_positive);
        EditText inputName = view.findViewById(R.id.dialog_input_name);
        builder.setCancelable(false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        positiveButton.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            UsernamePreference.getInstance().setUsernameInput(requireActivity(), name);

            String newName = UsernamePreference.getInstance().getUsernameInput(requireActivity());
            if (newName.isEmpty()) binding.displayInputName.setText(R.string.txt_anonymous);
            else binding.displayInputName.setText(newName);
            dialog.dismiss();
        });
    }
}