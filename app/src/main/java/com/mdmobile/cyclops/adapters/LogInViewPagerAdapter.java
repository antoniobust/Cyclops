package com.mdmobile.cyclops.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mdmobile.cyclops.ui.logIn.LoginConfigureSecretIdFragment;
import com.mdmobile.cyclops.ui.logIn.LoginConfigureServerFragment;

/**
 * This adapter inflates fragments in the login activity viewPager
 * in order to guide the user to a step by step configuration procedure
 */

public class LogInViewPagerAdapter extends FragmentPagerAdapter {

    private int FRAGMENT_COUNT;

    public LogInViewPagerAdapter(FragmentManager fm) {
        super(fm);
        FRAGMENT_COUNT = 2;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return LoginConfigureServerFragment.newInstance();
            case 1:
                return LoginConfigureSecretIdFragment.Companion.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

}
