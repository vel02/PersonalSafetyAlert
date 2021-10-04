package com.sti.research.personalsafetyalert.ui.screen.menu.notworking;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;

import com.sti.research.personalsafetyalert.BaseActivity;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityNotWorkingBinding;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotWorkingActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityNotWorkingBinding binding;
    private NotWorkingViewModel viewModel;

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
        binding = DataBindingUtil.setContentView(NotWorkingActivity.this, R.layout.activity_not_working);
        viewModel = new ViewModelProvider(NotWorkingActivity.this, providerFactory).get(NotWorkingViewModel.class);
        getIntentObject();
        initController();
        subscribeObservers();
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
        binding.appBarLayout.setTargetElevation(0);

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