package com.sti.research.personalsafetyalert.ui.splash;

import static com.sti.research.personalsafetyalert.util.Constants.KEY_ANIM_TYPE;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivitySplashBinding;
import com.sti.research.personalsafetyalert.ui.MainActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.welcome.WelcomeActivity;
import com.sti.research.personalsafetyalert.util.Constants;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {

    private ActivitySplashBinding binding;
    /**
     * Duration of wait
     **/
    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
//            Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
//            SplashActivity.this.startActivity(mainIntent);
//            SplashActivity.this.finish();

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, Constants.TransitionType.Slide);
            startActivity(intent, options.toBundle());
            finish();

        }, SPLASH_DISPLAY_LENGTH);

    }
}