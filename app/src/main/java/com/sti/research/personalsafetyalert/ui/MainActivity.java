package com.sti.research.personalsafetyalert.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.databinding.ActivityMainBinding;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

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

        //toolbar setup temporary
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}