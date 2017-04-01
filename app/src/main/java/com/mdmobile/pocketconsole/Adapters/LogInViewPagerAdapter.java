package com.mdmobile.pocketconsole.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.mdmobile.pocketconsole.UI.LoginConfigureSecretIdFragment;
import com.mdmobile.pocketconsole.UI.LoginConfigureServerFragment;
import com.mdmobile.pocketconsole.UI.LoginUserNamePasswordFragment;

/**
 * This adapter inflates fragments in the login activity viewPager
 * in order to guide the user to a step by step configuring procedure
 */

public class LogInViewPagerAdapter extends FragmentPagerAdapter {

    public LogInViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        //Check which fragment to return based on position
        switch (position) {
            case 0:
                return LoginConfigureServerFragment.newInstance();
            case 1:
                return LoginConfigureSecretIdFragment.newInstance();
            case 2:
                return LoginUserNamePasswordFragment.newInstance();
            default:
                return LoginConfigureServerFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
