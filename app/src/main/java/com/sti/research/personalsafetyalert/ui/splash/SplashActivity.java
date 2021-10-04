package com.sti.research.personalsafetyalert.ui.splash;

import static com.sti.research.personalsafetyalert.util.Constants.KEY_ANIM_TYPE;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;

import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivitySplashBinding;
import com.sti.research.personalsafetyalert.ui.MainActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.welcome.WelcomeActivity;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.screen.splash.SplashNavigationPreference;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {

    private ActivitySplashBinding binding;

    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);

        if (SplashNavigationPreference.getInstance().getSplashNavigationStateState(this)) {
            new Handler().postDelayed(() -> {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(KEY_ANIM_TYPE, Constants.TransitionType.Fade);
                startActivity(intent, options.toBundle());

            }, SPLASH_DISPLAY_LENGTH);

        } else {
            new Handler().postDelayed(() -> {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(KEY_ANIM_TYPE, Constants.TransitionType.Fade);
                startActivity(intent, options.toBundle());

            }, SPLASH_DISPLAY_LENGTH);
        }
    }

}