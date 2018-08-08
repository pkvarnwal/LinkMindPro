package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;


import constraint.com.linkmindpro.R;

import static com.linkmindpro.utils.AppConstant.SPLASH_TIME;

public class SplashActivity extends AppCompatActivity implements AppConstant {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openActivity();
    }

    private void openActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getAppPreference(SplashActivity.this).getBoolean(INTRO_SEEN)) {

                    LoginData loginData = AppPreference.getAppPreference(SplashActivity.this).getObject(PREF_LOGINDATA, LoginData.class);
                    if(loginData != null) {
                        if (loginData.getRole().equals(PATIENT)) {
                            Intent intent = new Intent(SplashActivity.this, ChatActivity.class);
                            intent.putExtra(IS_PATIENT, true);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(SplashActivity.this, DoctorListActivity.class));
                        }

                    } else {
                        startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, ViewPagerActivity.class));
                }
                finish();
            }
            
        }, SPLASH_TIME);
    }
}
