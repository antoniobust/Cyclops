package com.mdmobile.pocketconsole.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdmobile.pocketconsole.ui.logIn.LoginConfigureSecretIdFragment;
import com.mdmobile.pocketconsole.ui.logIn.LoginConfigureServerFragment;
import com.mdmobile.pocketconsole.ui.logIn.LoginUserNamePasswordFragment;

/**
 * This adapter inflates fragments in the login activity viewPager
 * in order to guide the user to a step by step configuration procedure
 */

public class LogInViewPagerAdapter extends FragmentStatePagerAdapter {

    private int FRAGMENT_COUNT;

    public LogInViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        FRAGMENT_COUNT = 3;
    }

    @Override
    public Fragment getItem(int position) {

        if (FRAGMENT_COUNT == 1) {
            return LoginUserNamePasswordFragment.newInstance();
        } else {
            switch (position) {
                case 1:
                    return LoginConfigureServerFragment.newInstance();
                case 2:
                    return LoginConfigureSecretIdFragment.newInstance();
                case 3:
                    return LoginUserNamePasswordFragment.newInstance();
                    default: return null;
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    public void removeSererFragments() {
        FRAGMENT_COUNT = 1;
        notifyDataSetChanged();
    }
}
