package com.mdmobile.pocketconsole.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mdmobile.pocketconsole.R;

import layout.DevicesFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    //Bottom navigation bar, navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    ft.replace(R.id.main_activity_fragment_container, DevicesFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_profiles:
                    ft.replace(R.id.main_activity_fragment_container, ProfilesFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_server:
                    ft.replace(R.id.main_activity_fragment_container, ServerFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_users:
                    ft.replace(R.id.main_activity_fragment_container, UsersFragment.newInstance());
                    ft.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_devices);

        //Set action bar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

}
