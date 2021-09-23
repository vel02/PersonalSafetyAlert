package com.sti.research.personalsafetyalert.ui;

import static com.sti.research.personalsafetyalert.util.Constants.*;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.BuildConfig;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.MessageRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.ActivityMainBinding;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragment;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragment;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.visual.VisualMessageFragmentDirections;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements HostScreen, NavigatePermission,
        MessageRecyclerAdapter.OnMessageClickListener {

    private static final String TAG = "test";

    @Override
    public void onMessageResult(Message message) {
        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof HomeFragment)) {
            HomeFragment fragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            fragment.onMessageDataReceiver(message);
        }
    }

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private void getIntentObject() {
        Constants.TransitionType type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        if (this.viewModel.observedTransitionType().getValue() == null)
            this.viewModel.setTransitionType(type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //permission to use fade transition
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        viewModel = new ViewModelProvider(MainActivity.this, providerFactory).get(MainViewModel.class);
        getIntentObject();
        initController();
        subscribeObservers();
    }

    @SuppressLint("RtlHardcoded")
    private void subscribeObservers() {
        viewModel.observedTransitionType().observe(this, transitionType -> {
            if (transitionType == TransitionType.Fade) {
                Fade enterTransition = new Fade();
                enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setEnterTransition(enterTransition);
            }
        });
    }

    private void initController() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_permission
                    || destination.getId() == R.id.nav_message
                    || destination.getId() == R.id.nav_visual_message
                    || destination.getId() == R.id.nav_contact
            ) {
                //noinspection deprecation
                binding.appBarLayout.setTargetElevation(0F);
                if (destination.getId() == R.id.nav_message
                        || destination.getId() == R.id.nav_visual_message
                        || destination.getId() == R.id.nav_contact
                ) {
                    binding.toolbar.setTitle("");
                } else if (destination.getId() == R.id.nav_home) {
                    binding.toolbar.setTitle(R.string.app_name);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_not_working) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, NotWorkingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInflate(View view, String screen) {

        NavDirections directions;
        final String content;

        switch (screen) {
            case "tag_fragment_contact":
                directions = HomeFragmentDirections.actionNavHomeToNavContact();
                break;

            case "tag_fragment_message":
                directions = HomeFragmentDirections.actionNavHomeToNavMessage();
                break;

            case "tag_fragment_visual_message":
                content = viewModel.getSelectedMessage();
                directions = HomeFragmentDirections.actionNavHomeToNavVisualMessage(content);
                break;

            case "tag_fragment_message_to_visual":
                content = viewModel.getAddMessage();
                directions = MessageFragmentDirections.actionNavMessageToNavVisualMessage(content);
                break;

            case "tag_fragment_message_to_home":
                directions = MessageFragmentDirections.actionNavMessageToNavHome();
                break;

            case "tag_fragment_visual_to_home":
                directions = VisualMessageFragmentDirections.actionNavVisualMessageToNavHome();
                break;

            case "tag_fragment_home_to_permission":
                directions = HomeFragmentDirections.actionNavHomeToNavPermission();
                break;

            case "tag_fragment_permission_to_home":
                directions = PermissionFragmentDirections.actionNavPermissionToNavHome();
                break;

            case "tag_fragment_contact_to_add_contact":
                directions = ContactFragmentDirections.actionNavContactToAddContactFragment();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        Navigation.findNavController(view).navigate(directions);
    }

    @Override
    public boolean checkLocationPermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public boolean checkSendSMSPermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public boolean checkRecordAudioPermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public boolean checkStoragePermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public void requestLocationPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_location_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_NETWORK_STATE},
                                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE},
                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void requestSendSMSPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_send_sms_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE);
        }
    }

    @Override
    public void requestRecordAudioPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_audio_record_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE);
        }
    }

    @Override
    public void requestStoragePermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_storage_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PermissionManager.PERMISSION_STORAGE_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionManager.PERMISSION_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.PERMISSION_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                viewModel.setPermissionLocationState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionSendSMSState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionRecordAudioState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionStorageState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    public void onBackPressed() {
        assert navHostFragment != null;
        if (!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof PermissionFragment)) {
            super.onBackPressed();
        }
    }
}