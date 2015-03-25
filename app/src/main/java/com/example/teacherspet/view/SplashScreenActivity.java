package com.example.teacherspet.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.teacherspet.R;
import com.example.teacherspet.control.LoginActivity;
import com.example.teacherspet.model.AppCSTR;

/**
 * This is the splash screen, which will preface the login screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 03/21/2015
 */
public class SplashScreenActivity extends Activity {

    /**
     * When screen is created, pause for five seconds before going to Login Activity.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_splash_screen);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menu = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(menu);

                // Close activity
                finish();
            }
            // Delay for 5 seconds
        }, AppCSTR.PAUSE);
    }
}
