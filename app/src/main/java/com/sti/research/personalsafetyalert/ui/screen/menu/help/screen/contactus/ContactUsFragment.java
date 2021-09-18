package com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus;

import static com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus.ContactUsViewModel.*;
import static com.sti.research.personalsafetyalert.util.Constants.*;
import static com.sti.research.personalsafetyalert.util.Utility.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.FragmentContactUsBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ContactUsFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    private FragmentContactUsBinding binding;
    private ContactUsViewModel viewModel;

    private HostScreen hostScreen;

    private final List<String> pathImages = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactUsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity(), providerFactory).get(ContactUsViewModel.class);
        navigate();
        subscribeObservers();
    }

    private void subscribeObservers() {

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

    }

    @SuppressWarnings("deprecation")
    private void selectImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GalleryManager.PICK_GALLERY_REQUEST);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == GalleryManager.PICK_GALLERY_REQUEST) {
                assert data != null;
                Uri imageUri = data.getData();
                this.pathImages.add(getImageUriPath(imageUri));
                setScreenshotPerSlot(imageUri);
            }
    }

    private void setScreenshotPerSlot(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
            switch (viewModel.getScreenshotSlot()) {
                case SLOT_ONE:
                    binding.contactUsSsOneAdd.setVisibility(View.GONE);
                    binding.contactUsSsOneImage.setVisibility(View.VISIBLE);
                    binding.contactUsSsOneImage.setImageBitmap(bitmap);
                    break;
                case SLOT_TWO:
                    binding.contactUsSsTwoAdd.setVisibility(View.GONE);
                    binding.contactUsSsTwoImage.setVisibility(View.VISIBLE);
                    binding.contactUsSsTwoImage.setImageBitmap(bitmap);
                    break;
                case SLOT_THREE:
                    binding.contactUsSsThreeAdd.setVisibility(View.GONE);
                    binding.contactUsSsThreeImage.setVisibility(View.VISIBLE);
                    binding.contactUsSsThreeImage.setImageBitmap(bitmap);
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
                this.sendUserConcern();
                hostScreen.onInflate(requireView(), getString(R.string.tag_fragment_contact_us_to_help));
            } else Bubble.message(requireActivity(),
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

        viewModel.sendEmailWithAttachments(
                body,
                MessagingManager.EMAIL_HOST,
                withSuggestion,
                pathImages);
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

}