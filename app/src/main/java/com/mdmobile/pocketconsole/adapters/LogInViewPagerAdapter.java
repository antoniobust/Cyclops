package com.mdmobile.pocketconsole.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.ui.logIn.LoginConfigureSecretIdFragment;
import com.mdmobile.pocketconsole.ui.logIn.LoginConfigureServerFragment;
import com.mdmobile.pocketconsole.ui.logIn.LoginUserNamePasswordFragment;

import java.util.ArrayList;

/**
 * This adapter inflates fragments in the login activity viewPager
 * in order to guide the user to a step by step configuration procedure
 */

public class LogInViewPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentManager fm;

    public LogInViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return LoginConfigureServerFragment.newInstance();
            case 1:
                return LoginConfigureSecretIdFragment.newInstance();
            case 2:
                return LoginUserNamePasswordFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        if (fragments.contains(object)) {
            return fragments.indexOf(object);
        } else return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position < getCount()) {
            FragmentTransaction trans = fm.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }
}
