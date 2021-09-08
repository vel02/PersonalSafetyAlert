package com.sti.research.personalsafetyalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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