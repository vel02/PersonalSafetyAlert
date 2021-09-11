package com.sti.research.personalsafetyalert.ui;

import static com.sti.research.personalsafetyalert.util.Utility.*;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityMainBinding;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements Hostable {

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

        initController();

    }

    private void initController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
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
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_not_working) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, NotWorkingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.KEY_ANIM_TYPE, Constants.TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInflate(View view, String screen) {

    }
}