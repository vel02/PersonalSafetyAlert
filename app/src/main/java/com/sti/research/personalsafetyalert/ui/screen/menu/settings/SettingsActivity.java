package com.sti.research.personalsafetyalert.ui.screen.menu.settings;


import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sti.research.personalsafetyalert.BaseActivity;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivitySettingsBinding;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SettingsActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivitySettingsBinding binding;
    private SettingsViewModel viewModel;

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
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);
        viewModel = new ViewModelProvider(SettingsActivity.this, providerFactory).get(SettingsViewModel.class);
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
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        AppBarConfiguration configuration = new AppBarConfiguration.Builder().build();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        if (!(navController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressed();
        }
        return true;
    }

}