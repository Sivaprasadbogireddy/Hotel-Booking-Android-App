package com.example.surface.ramhotelapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();

        // If saved instance state is null, replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, new LogInFragment(),
                            Utils.LogInFragment).commit();
        }

    }

    //animation
    @SuppressLint("ResourceType")
    protected void loginFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame, new LogInFragment(), Utils.LogInFragment).commit();
    }

    @Override
    public void  onBackPressed() {
        Fragment SignUpFragment = fragmentManager.findFragmentByTag(Utils.SignUpFragment);

        if (SignUpFragment != null) {
            loginFragment();
        }
        else {
            super.onBackPressed();
        }

    }
}
