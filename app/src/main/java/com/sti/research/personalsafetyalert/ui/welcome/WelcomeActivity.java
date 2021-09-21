package com.sti.research.personalsafetyalert.ui.welcome;

import static com.sti.research.personalsafetyalert.util.Constants.KEY_ANIM_TYPE;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityWelcomeBinding;
import com.sti.research.personalsafetyalert.ui.HostScreen;
import com.sti.research.personalsafetyalert.ui.MainActivity;
import com.sti.research.personalsafetyalert.ui.splash.SplashActivity;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.screen.home.HomeInitialMessage;
import com.sti.research.personalsafetyalert.util.screen.splash.SplashNavigationPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class WelcomeActivity extends DaggerAppCompatActivity implements HostScreen {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityWelcomeBinding binding;
    private WelcomeViewModel viewModel;

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
        binding = DataBindingUtil.setContentView(WelcomeActivity.this, R.layout.activity_welcome);
        viewModel = new ViewModelProvider(WelcomeActivity.this, providerFactory).get(WelcomeViewModel.class);
        getIntentObject();
        initController();
        subscribeObservers();
    }

    @SuppressLint("RtlHardcoded")
    private void subscribeObservers() {
        viewModel.observedTransitionType().observe(this, transitionType -> {
            if (transitionType == Constants.TransitionType.Slide) {
                Slide enterTransition = new Slide();
                enterTransition.setSlideEdge(Gravity.RIGHT);
                enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setEnterTransition(enterTransition);
            } else if (transitionType == Constants.TransitionType.Fade) {
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
    }

    @Override
    public void onInflate(View view, String screen) {
        if (screen.equals(getString(R.string.tag_activity_welcome_to_main))) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, Constants.TransitionType.Fade);
            startActivity(intent, options.toBundle());
            SplashNavigationPreference.getInstance().setSplashNavigationStateState(this, true);
            HomeInitialMessage.getInstance().setInitializeMessagesState(this, true);
        }
    }
}