package com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentLogBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.util.screen.permission.MobileUserIDPreference;

import java.util.Arrays;
import java.util.List;

import dagger.android.support.DaggerFragment;


public class LogFragment extends DaggerFragment {

    private FragmentLogBinding binding;

    private HostScreen hostScreen;
    private Logs log;

    private MediaController mediaController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("LOGS", "onViewCreated: " + log.toString());

        if (log != null) {
            binding.logTitle.setText(log.getTitle());
            binding.logMessage.setText(log.getMessage());
            binding.logTime.setText(log.getTimestamp());


            if (log.getVideo() != null && !log.getVideo().isEmpty()) {
                String link = "Download video with link.";

                binding.logVideoCard.setVisibility(View.VISIBLE);

                binding.logVideoLink.setText(link);
                binding.logVideoLink.setPaintFlags(binding.logVideoLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                binding.logVideoLink.setOnClickListener(view1 -> goToURL(log.getVideo()));


                mediaController = new MediaController(requireContext());
                mediaController.setAnchorView(binding.logVideo);

                binding.logVideo.setMediaController(mediaController);
                binding.logVideo.setVideoPath(log.getVideo());//Uri.parse(filePath));


                binding.logVideo.setOnClickListener(v -> {
                    binding.logVideo.start();
                });
            } else {
                binding.logVideoCard.setVisibility(View.GONE);
            }


            List<String> images = Arrays.asList(log.getImages().split(","));
            for (String image : images) {
                Log.e("LOGS", "onViewCreated: " + image);
            }

            if (images.size() > 0) {
                binding.logImageCard.setVisibility(View.VISIBLE);

                String link = "Download image with link.";

                for (int i = 0; i < images.size(); i++) {
                    String image = images.get(i);
                    if (i == 0) {
                        if (!image.isEmpty()) {
                            binding.logImageLinkOne.setText(link);
                            binding.logImageLinkOne.setPaintFlags(binding.logImageLinkOne.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            binding.logImageLinkOne.setOnClickListener(view1 -> goToURL(image));
                            Glide.with(this).load(image).into(binding.logImageOne);
                        }
                    }

                    if (i == 1) {
                        if (!image.isEmpty()) {
                            binding.logImageLinkTwo.setText(link);
                            binding.logImageLinkTwo.setPaintFlags(binding.logImageLinkTwo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            binding.logImageLinkTwo.setOnClickListener(view1 -> goToURL(image));
                            Glide.with(this).load(image).into(binding.logImageTwo);
                        }
                    }

                    if (i == 2) {
                        if (!image.isEmpty()) {
                            binding.logImageLinkThree.setText(link);
                            binding.logImageLinkThree.setPaintFlags(binding.logImageLinkThree.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            binding.logImageLinkThree.setOnClickListener(view1 -> goToURL(image));
                            Glide.with(this).load(image).into(binding.logImageThree);
                        }
                    }

                    if (image.isEmpty() || image.contains(" ")) {
                        binding.logImageCard.setVisibility(View.GONE);
                    } else binding.logImageCard.setVisibility(View.VISIBLE);

                }
            } else {
                binding.logImageCard.setVisibility(View.GONE);
            }
        }

    }

    private void goToURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_admin, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(requireContext(), R.style.PersonalSafetyAlert_AlertDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.dialog_logout_layout, null);

                TextView positive = view.findViewById(R.id.dialog_button_positive);
                TextView negative = view.findViewById(R.id.dialog_button_negative);
                builder.setCancelable(false);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                negative.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                positive.setOnClickListener(v -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(requireActivity(), SettingsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                });
            }
        } else if (item.getItemId() == R.id.action_delete) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference
                        .child(getActivity().getString(R.string.db_node_admin))
                        .child(FirebaseAuth.getInstance().getUid())

                        .child(getActivity().getString(R.string.db_node_mobileusers))
                        .child(log.getMobileusers_id())//MobileUserIDPreference.getInstance().getMobileUserIDPreference(requireContext()))

                        .child(getActivity().getString(R.string.db_node_logs))
                        .child(log.getLog_id())//log.getMobileusers_id())

                        .removeValue();
//                requireActivity().onBackPressed();

                hostScreen.onInflate(binding.getRoot(), "tag_fragment_log_to_dashboard");
            }
        }

        return super.onOptionsItemSelected(item);
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

        log = LogFragmentArgs.fromBundle(getArguments()).getLog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostScreen = null;
    }
}