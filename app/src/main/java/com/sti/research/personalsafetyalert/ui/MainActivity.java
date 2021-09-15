package com.sti.research.personalsafetyalert.ui;

import static com.sti.research.personalsafetyalert.util.Constants.*;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.BuildConfig;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityMainBinding;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragment;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.visual.VisualMessageFragmentDirections;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements HostScreen, NavigatePermission {

    private static final String TAG = "test";

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        viewModel = new ViewModelProvider(MainActivity.this, providerFactory).get(MainViewModel.class);
        launchAnimation();
        initController();

        /*
            Permission Logic
            - after starting main activity
            - start permission fragment
            - user will accept to apply all permission needed
            - will be triggered in main activity
            - after all permission are accepted
            - proceed button enabled in permission fragment
            - then return to main activity
         */


    }

    private void launchAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        binding.layout.startAnimation(animation);
    }

    private void initController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_permission)
                binding.appBarLayout.setTargetElevation(0F);
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

        switch (screen) {
            case "tag_fragment_contact":
                directions = HomeFragmentDirections.actionNavHomeToNavContact();
                break;

            case "tag_fragment_message":
                directions = HomeFragmentDirections.actionNavHomeToNavMessage();
                break;

            case "tag_fragment_visual_message":
                directions = HomeFragmentDirections.actionNavHomeToNavVisualMessage();
                break;

            case "tag_fragment_message_to_visual":
                directions = MessageFragmentDirections.actionNavMessageToNavVisualMessage();
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
    public void requestLocationPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void requestSendSMSPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_permission_rational,
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.PERMISSION_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                viewModel.setPermissionSendSMS(PackageManager.PERMISSION_GRANTED);
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
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        if (!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof PermissionFragment)) {
            super.onBackPressed();
        }
    }
}