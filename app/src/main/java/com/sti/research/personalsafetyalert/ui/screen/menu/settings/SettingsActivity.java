package com.sti.research.personalsafetyalert.ui.screen.menu.settings;


import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sti.research.personalsafetyalert.BaseActivity;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.dashboard.MobileUserRecyclerAdapter;
import com.sti.research.personalsafetyalert.adapter.view.logs.UserLogsRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.ActivitySettingsBinding;
import com.sti.research.personalsafetyalert.model.Logs;
import com.sti.research.personalsafetyalert.model.MobileUser;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.DashboardLogFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.DashboardLogFragmentDirections;
//import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.LogFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.MobileUserFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.MobileUserFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.SettingsFragment;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.screen.SettingsFragmentDirections;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SettingsActivity extends BaseActivity implements HostScreen,
        SettingsFragment.OnDialogSettingsDisplay,
        MobileUserRecyclerAdapter.OnMobileUserClickListener,
        UserLogsRecyclerAdapter.OnLogClickListener {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivitySettingsBinding binding;
    private SettingsViewModel viewModel;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private final static String TAG = "FIREBASE";

    //FIREBASE
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    public void onMobileUserResult(MobileUser mobileUser) {
        Log.e(TAG, "onMobileUserResult: CLICKED! " + mobileUser.getId());
        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof DashboardLogFragment)) {
            DashboardLogFragment fragment = (DashboardLogFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            fragment.onMobileUserDataReceiver(mobileUser);
        }
    }

    @Override
    public void onLogResult(Logs log) {
        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof MobileUserFragment)) {
            MobileUserFragment fragment = (MobileUserFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            fragment.onUserLogDataReceiver(log);
        }
    }

    private void getIntentObject() {
        Constants.TransitionType type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        if (this.viewModel.observedTransitionType().getValue() == null)
            this.viewModel.setTransitionType(type);
    }

    @Override
    public void onDialogDisplay(String content) {
        androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this, R.style.PersonalSafetyAlert_AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_username_layout, null);
        TextView txtContent = view.findViewById(R.id.dialog_content);
        txtContent.setText(content);
        builder.setCancelable(false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        new WaitResultManager(2000, 1000, () -> {
            dialog.dismiss();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) FirebaseAuth.getInstance().signOut();
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //permission to use fade transition
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);
        viewModel = new ViewModelProvider(SettingsActivity.this, providerFactory).get(SettingsViewModel.class);
        setupFirebaseAuth();

        getIntentObject();
        subscribeObservers();
        initController();
    }

    private void subscribeObservers() {
        viewModel.observedTransitionType().observe(this, transitionType -> {
            if (transitionType == Constants.TransitionType.Fade) {
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

        AppBarConfiguration configuration = new AppBarConfiguration.Builder().build();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
    }

    @Override
    public boolean onSupportNavigateUp() {
//        finishAfterTransition();


        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof DashboardLogFragment)) {
            Log.e(TAG, "onSupportNavigateUp: TRIGGERED");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this, R.style.PersonalSafetyAlert_AlertDialogTheme);
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
                    onBackPressed();
                    dialog.dismiss();
                });
            }
        } else if (!(navController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressed();
        }
        return true;
    }

    private void setupFirebaseAuth() {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
//                    Toast.makeText(SettingsActivity.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onInflate(View view, String screen) {

        NavDirections directions;

        switch (screen) {
            case "tag_fragment_settings_to_dashboardlog":
                directions = SettingsFragmentDirections.actionNavSettingsToDashboardLog();
                break;
//            case "tag_fragment_dashboard_to_settings":
////                directions = DashboardLogFragmentDirections.actionNavDashboardLogToNavSettings();
//                break;
//            case "tag_fragment_mobileuser_to_settings":
//                directions = MobileUserFragmentDirections.actionNavMobileuserToNavSettings();
//                break;
//            case "tag_fragment_log_to_settings":
//                directions = LogFragmentDirections.actionLogFragmentToNavSettings();
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        Navigation.findNavController(view).navigate(directions);

    }

    @Override
    public void onInflate(View view, String screen, Object object) {
        //not supported
        NavDirections directions = null;

        switch (screen) {
            case "tag_fragment_dashboard_to_mobileuser":
                if (object instanceof MobileUser)
                    directions = DashboardLogFragmentDirections.actionNavDashboardLogToMobileUserFragment((MobileUser) object);
                break;
            case "tag_fragment_mobileuser_to_log":
                if (object instanceof Logs)
                    directions = MobileUserFragmentDirections.actionNavMobileuserToLogFragment((Logs)object);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        assert directions != null;
        Navigation.findNavController(view).navigate(directions);

    }
}