package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentSettingsBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.model.User;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.screen.main.UsernamePreference;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.util.screen.permission.MobileUserIDPreference;

import dagger.android.support.DaggerFragment;

public class SettingsFragment extends DaggerFragment implements HostScreen {

    private FragmentSettingsBinding binding;

    private final static String TAG = "FIREBASE";

    private HostScreen hostScreen;


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

//        registerNewEmail("personal.safety.alert.bot@gmail.com", "personal@alert");


        binding.layoutUserLogReview.setOnClickListener(v -> {


            hostScreen.onInflate(requireView(), "tag_fragment_settings_to_user_log_list");


        });


        binding.layoutAdminAuth.setOnClickListener(vw -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in

                //redirect to dashboard logs
                hostScreen.onInflate(requireView(), "tag_fragment_settings_to_dashboardlog");

            } else {
                // No user is signed in

                Dialog dialog = new Dialog(requireActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_admin_auth_layout);

                TextView dialogPositive = dialog.findViewById(R.id.dialog_button_positive);
                EditText dialogEmail = dialog.findViewById(R.id.dialog_email);
                EditText dialogPassword = dialog.findViewById(R.id.dialog_password);
                dialogPositive.setOnClickListener(view1 -> {

                    if (!(dialogEmail.getText().toString().isEmpty())
                            && !(dialogPassword.getText().toString().isEmpty())) {
                        Log.d(TAG, "Dialog: attempting to authenticate. ");

                        FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(
                                        dialogEmail.getText().toString(),
                                        dialogPassword.getText().toString())
                                .addOnCompleteListener(task -> {

                                    //redirect to dashboard logs
                                    hostScreen.onInflate(requireView(), "tag_fragment_settings_to_dashboardlog");

                                }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show());

                    } else {
                        Toast.makeText(requireActivity(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                });

                dialog.show();

            }


        });


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

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(
                        "personal.safety.alert.bot@gmail.com",
                        "personal@alert")
                .addOnCompleteListener(task -> {
                })
//                        Toast.makeText(requireActivity(), "Authentication Success", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> FirebaseAuth.getInstance().signOut());

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


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
//                Toast.makeText(requireActivity(), "ONLINE", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "initUserName: ONLINE ");
                DatabaseReference reference = FirebaseDatabase.getInstance("https://personalsafetyalert-a5eef-default-rtdb.firebaseio.com/").getReference();
                reference
                        .child(getString(R.string.db_node_admin))
                        .child(user.getUid())

                        .child(getString(R.string.db_node_mobileusers))
                        .child(MobileUserIDPreference.getInstance().getMobileUserIDPreference(requireActivity()))
                        .child("username")

                        .setValue(name);

                onDialogSettingsDisplay.onDialogDisplay("Updating user's name...");
            }


            String newName = UsernamePreference.getInstance().getUsernameInput(requireActivity());
            if (newName.isEmpty()) binding.displayInputName.setText(R.string.txt_anonymous);
            else binding.displayInputName.setText(newName);
            dialog.dismiss();
        });
    }

    private OnDialogSettingsDisplay onDialogSettingsDisplay;

    public interface OnDialogSettingsDisplay {
        void onDialogDisplay(String content);
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

        if (!(activity instanceof OnDialogSettingsDisplay)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement OnDialogSettingsDisplay interface.");
        }

        onDialogSettingsDisplay = (OnDialogSettingsDisplay) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
        onDialogSettingsDisplay = null;
    }

    /*
            ----------------------- FIREBASE SETUP -----------------------------
     */

    private void registerNewEmail(String email, String password) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "onComplete: " + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: AuthState: success");

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user != null) {
                            User admin = new User();
                            admin.setAdmin_id(user.getUid());
                            admin.setEmail(user.getEmail());

                            DatabaseReference reference = FirebaseDatabase.getInstance("https://personalsafetyalert-a5eef-default-rtdb.firebaseio.com/").getReference();

                            reference.child(getString(R.string.db_node_admin))
                                    .child(user.getUid())
                                    .setValue(admin);


                            MobileUser mobileUser = new MobileUser();
                            mobileUser.setUsername(UsernamePreference.getInstance().getUsernameInput(requireActivity()));

                            String mobileusersId = reference.child(getString(R.string.db_node_mobileusers))
                                    .push().getKey();

                            reference
                                    .child(getString(R.string.db_node_admin))
                                    .child(user.getUid())

                                    .child(getString(R.string.db_node_mobileusers))
                                    .child(mobileusersId)

                                    .setValue(mobileUser);

                            Logs logs = new Logs();
                            logs.setMobileusers_id("");
                            logs.setTitle("");
                            logs.setTimestamp("");


                            String logsId = reference.child(getString(R.string.db_node_logs))
                                    .push().getKey();

                            reference
                                    .child(getString(R.string.db_node_admin))
                                    .child(user.getUid())


                                    .child(getString(R.string.db_node_mobileusers))
                                    .child(mobileusersId)

                                    .child(getString(R.string.db_node_logs))
                                    .child(logsId)

                                    .setValue(logs);


                        }

                    } else {
                        Toast.makeText(requireActivity(), "Unable to Register", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    @Override
    public void onInflate(View view, String screen) {

    }

    @Override
    public void onInflate(View view, String screen, Object object) {

    }
}