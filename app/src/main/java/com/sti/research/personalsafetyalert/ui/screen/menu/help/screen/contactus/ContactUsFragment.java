package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus;

import static com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus.ContactUsViewModel.*;
import static com.sti.research.personalsafetyalert.util.Constants.*;
import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentContactUsBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.util.screen.permission.MobileUserIDPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ContactUsFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentContactUsBinding binding;
    private ContactUsViewModel viewModel;

    private HostScreen hostScreen;

    private final List<String> pathAttachements = new ArrayList<>();

    private String uriVideo;
    private String uriImages = "";

    private OnDialogSettingsDisplay onDialogSettingsDisplay;

    public interface OnDialogSettingsDisplay {
        AlertDialog onDialogDisplay(String content);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactUsBinding.inflate(inflater);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
//            Toast.makeText(requireActivity(), "Already Authenticated", Toast.LENGTH_SHORT).show();
        } else {
            // No user is signed in
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(ResourceManager.RESOURCES_CODE_M,
                            ResourceManager.RESOURCES_CODE_P)
                    .addOnCompleteListener(task -> {
//                        Toast.makeText(requireActivity(), "Authentication Success", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                //                    Toast.makeText(requireActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show()
            });

        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(ContactUsViewModel.class);
        dialogLoadingDisplay();
        dialogSendingDisplay();

        navigate();
        subscribeObservers();
    }

    private void subscribeObservers() {

        viewModel.getVideoUri().removeObservers(getViewLifecycleOwner());
        viewModel.getVideoUri().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String uri) {
                if (uri != null) {
                    uriVideo = uri;
                    dialogLoading.dismiss();

                }
            }
        });

        viewModel.getImageUri().removeObservers(getViewLifecycleOwner());
        viewModel.getImageUri().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String uri) {
                if (uri != null) {
                    uriImages += uri + ",";
                    dialogLoading.dismiss();
                }
            }
        });

    }

    private void navigate() {
        binding.contactUsSsOneAdd.setOnClickListener(v -> {
            selectImageFromGallery();
            viewModel.setScreenshotSlot(ScreenshotSlot.SLOT_ONE);
        });

        binding.contactUsSsTwoAdd.setOnClickListener(v -> {
            selectImageFromGallery();
            viewModel.setScreenshotSlot(ScreenshotSlot.SLOT_TWO);
        });

        binding.contactUsSsThreeAdd.setOnClickListener(v -> {
            selectImageFromGallery();
            viewModel.setScreenshotSlot(ScreenshotSlot.SLOT_THREE);
        });

        binding.contactUsUploadVideo.setOnClickListener(view -> {

            //remove last selected video if it does, otherwise will not execute this
            if (this.pathAttachements.size() > 0) {
                int lastItem = this.pathAttachements.size() - 1;
                String path = this.pathAttachements.get(lastItem);
                String lastSegment = path.substring(path.indexOf(".") + 1);
                Log.e("ATTACHMENT", "LAST SEGMENT " + lastSegment);
                if (lastSegment.equals("mp4")) {
                    this.pathAttachements.remove(lastItem);
                }
            }

            selectVideoFromGallery();
        });

        binding.contactUsNotWorking.setOnClickListener(v -> {
            hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_contact_us_to_not_working));
        });

    }

    @SuppressWarnings("deprecation")
    private void selectImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GalleryManager.PICK_GALLERY_IMAGE_REQUEST);
    }

    @SuppressWarnings("deprecation")
    private void selectVideoFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/*");
        startActivityForResult(photoPickerIntent, GalleryManager.PICK_GALLERY_VIDEO_REQUEST);
    }

    private AlertDialog dialogLoading;
    private AlertDialog dialogSending;

    private void dialogLoadingDisplay() {
        androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(requireContext(), R.style.PersonalSafetyAlert_AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_loading_layout, null);

        builder.setCancelable(false);
        builder.setView(view);
        dialogLoading = builder.create();
    }

    private void dialogSendingDisplay() {
        androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(requireContext(), R.style.PersonalSafetyAlert_AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_sending_layout, null);

        builder.setCancelable(false);
        builder.setView(view);
        dialogSending = builder.create();
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GalleryManager.PICK_GALLERY_IMAGE_REQUEST) {
                assert data != null;
                Uri imageUri = data.getData();
                viewModel.writeUserPhoto(requireView(), imageUri);

                dialogLoading.show();


                this.pathAttachements.add(getImageUriPath(imageUri));
                setScreenshotPerSlot(imageUri);
            } else if (requestCode == GalleryManager.PICK_GALLERY_VIDEO_REQUEST) {
                assert data != null;
                Uri selectedVideoUri = data.getData();
                viewModel.writeUserVideo(requireView(), selectedVideoUri);

                dialogLoading.show();


                // MEDIA GALLERY
                String selectedVideoPath = getVideoUriPath(selectedVideoUri);
                if (selectedVideoPath != null) {
                    this.pathAttachements.add(selectedVideoPath);

                    List<String> pathSegments = Arrays.asList(selectedVideoPath.split("/"));
                    String lastSegment = pathSegments.get(pathSegments.size() - 1);
                    binding.contactUsVideoFilenameUploaded.setText(lastSegment + " attached.");
                    Log.e("VIDEO", "MEDIA PATH " + selectedVideoPath);
                }

            }
        }
    }


    private void setScreenshotPerSlot(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
            switch (viewModel.getScreenshotSlot()) {
                case SLOT_ONE:
                    binding.contactUsSsOneAdd.setVisibility(View.GONE);
                    binding.contactUsSsOneImage.setVisibility(View.VISIBLE);
//                    binding.contactUsSsOneImage.setImageBitmap(bitmap);
                    Glide.with(this).load(bitmap).into(binding.contactUsSsOneImage);
                    break;
                case SLOT_TWO:
                    binding.contactUsSsTwoAdd.setVisibility(View.GONE);
                    binding.contactUsSsTwoImage.setVisibility(View.VISIBLE);
//                    binding.contactUsSsTwoImage.setImageBitmap(bitmap);
                    Glide.with(this).load(bitmap).into(binding.contactUsSsTwoImage);
                    break;
                case SLOT_THREE:
                    binding.contactUsSsThreeAdd.setVisibility(View.GONE);
                    binding.contactUsSsThreeImage.setVisibility(View.VISIBLE);
//                    binding.contactUsSsThreeImage.setImageBitmap(bitmap);
                    Glide.with(this).load(bitmap).into(binding.contactUsSsThreeImage);
                    break;
            }
        } catch (IOException e) {
            Log.i("TAG", "Some exception " + e);
        }
    }

    private String getImageUriPath(Uri imageUri) {
        //REFERENCES:
        //https://stackoverflow.com/questions/17546101/get-real-path-for-uri-android
        //https://stackoverflow.com/questions/23527767/open-failed-eacces-permission-denied
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(imageUri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            Log.d("test", "REAL PATH:  " + imagePath);
            return imagePath;
        }
        cursor.close();
        return "";
    }

    public String getVideoUriPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        getActivity().getContentResolver();
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_contact_us, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            if (!TextUtils.isEmpty(binding.contactUsMessage.getText())) {
                dialogSending.show();
                this.sendUserConcern();
                new WaitResultManager(10000, 1000, new WaitResultManager.WaitResultReceiverListener() {
                    @Override
                    public void onFinished() {
                        //loading here.....
                        dialogSending.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_contact_us_to_help));
                    }
                }).start();
            } else Popup.message(requireView(),
                    "Please give as a clear detail of your concerns.");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendUserConcern() {
        boolean withSuggestion = binding.contactUsSuggestion.isChecked();
        boolean userTried = binding.contactUsTried.isChecked();

        String body = binding.contactUsMessage.getText().toString()
                + "\n\n" + (userTried ? getString(R.string.txt_user_tried) : "");

        String title = "";
        if (withSuggestion) title = MessagingManager.EMAIL_SUBJECT_WITH_SUGGESTION;
        else title = MessagingManager.EMAIL_SUBJECT_WITHOUT_SUGGESTION;

        viewModel.sendEmailWithAttachments(
                body,
                MessagingManager.EMAIL_HOST,
                withSuggestion,
                pathAttachements);

        //DATABASE FIREBASE
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            DatabaseReference reference = FirebaseDatabase.getInstance("https://personalsafetyalert-a5eef-default-rtdb.firebaseio.com/").getReference();

            String mobileusersId = MobileUserIDPreference.getInstance().getMobileUserIDPreference(requireActivity());

            String logsId = reference.child(getString(R.string.db_node_logs))
                    .push().getKey();

            Logs logs = new Logs();
            logs.setMobileusers_id(mobileusersId);
            logs.setLog_id(logsId);
            logs.setTitle(title);
            logs.setMessage(body);
            logs.setVideo(uriVideo);
            logs.setImages(uriImages);
            logs.setTimestamp(getTimestamp());


            reference
                    .child(getString(R.string.db_node_admin))
                    .child(user.getUid())

                    .child(getString(R.string.db_node_mobileusers))
                    .child(mobileusersId)//"-N2A_n8ph3wbXtxO8OI0")//"-N2AWkR8zT4v0sJ51dg7") //mobileusers id

                    .child(getString(R.string.db_node_logs))
                    .child(logsId)

                    .setValue(logs);
        }

    }

    private String getTimestamp() {
        String pattern = "MMMM dd, yyyy - HH:mm:ss a";//"yyyy-MM-dd'T'HH:mm:ss'Z'"
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
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
//        Toast.makeText(requireActivity(), "User signed out", Toast.LENGTH_SHORT).show();
//        FirebaseAuth.getInstance().signOut();
    }


    //---------------------


}