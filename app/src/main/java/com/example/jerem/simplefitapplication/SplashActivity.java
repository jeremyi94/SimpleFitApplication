package com.example.jerem.simplefitapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.auth.core.StartupAuthResult;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context appContext = getApplicationContext();
        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        IdentityManager identityManager = new IdentityManager(appContext, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);
        identityManager.doStartupAuth(this, new StartupAuthResultHandler() {
            @Override
            public void onComplete(StartupAuthResult startupAuthResult) {
                // User identity is ready as unauthenticated user or previously signed-in user.
            }
        });

        // Go to the main activity
        final Intent intent = new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();

    }
}
