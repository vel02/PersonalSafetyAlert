package com.sti.research.personalsafetyalert.ui.screen.menu.help;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityHelpBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.HelpFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.contactus.ContactUsFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.screen.howto.HowToFragmentDirections;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class HelpActivity extends DaggerAppCompatActivity implements HostScreen {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityHelpBinding binding;
    private HelpViewModel viewModel;

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
        binding = DataBindingUtil.setContentView(HelpActivity.this, R.layout.activity_help);
        viewModel = new ViewModelProvider(HelpActivity.this, providerFactory).get(HelpViewModel.class);
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
        binding.appBarLayout.setTargetElevation(0);

        AppBarConfiguration configuration = new AppBarConfiguration.Builder().build();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!(navController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onInflate(View view, String screen) {

        NavDirections directions;

        switch (screen) {
            case "tag_fragment_help_to_how_to":
                directions = HelpFragmentDirections.actionNavHelpToNavHowTo();
                break;
            case "tag_fragment_help_to_not_working":
                directions = HelpFragmentDirections.actionNavHelpToNavNotWorking();
                break;
            case "tag_fragment_help_to_contact_us":
                directions = HelpFragmentDirections.actionNavHelpToNavContactUs();
                break;
            case "tag_fragment_how_to_to_help":
                directions = HowToFragmentDirections.actionNavHowToToNavHelp();
                break;
            case "tag_fragment_contact_us_to_help":
                directions = ContactUsFragmentDirections.actionNavContactUsToNavHelp();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        Navigation.findNavController(view).navigate(directions);
    }
}