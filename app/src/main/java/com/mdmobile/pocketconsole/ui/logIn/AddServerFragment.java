package com.mdmobile.pocketconsole.ui.logIn;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.LogInViewPagerAdapter;
import com.mdmobile.pocketconsole.utils.ConfigureServerAsyncTask;
import com.mdmobile.pocketconsole.utils.GeneralUtility;
import com.mdmobile.pocketconsole.utils.Logger;
import com.mdmobile.pocketconsole.utils.ServerXmlConfigParser;

import java.io.File;

/**
 * Fragment displayed to add a new server
 */

public class AddServerFragment extends Fragment implements ServerXmlConfigParser.ServerXmlParse,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private final static String LOG_TAG = AddServerFragment.class.getSimpleName();
    private int permissionReqID = 100;
    private ViewPager viewPager;
    private TabLayout dotsIndicator;
    private LogInViewPagerAdapter viewPagerAdapter;


    public AddServerFragment() {
        //Empty constructor
    }

    public static AddServerFragment newInstance() {

        AddServerFragment fragment = new AddServerFragment();
        return fragment;
    }

    //Interface methods
    @Override
    public void xmlParseComplete() {
        //TODO: update UI file parsed
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionReqID) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                parseServerConfFile();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_server, container, false);

        //Instantiate views
        viewPager = rootView.findViewById(R.id.login_add_server_view_pager);
        dotsIndicator = rootView.findViewById(R.id.login_view_pager_dots_indicator);

        setViewPager();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.add_server_button).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.add_user_button).setVisibility(View.VISIBLE);

        if (checkConfigurationFile()) {
            parseServerConfFile();
        }
    }

    //Convenience method to set up the view pager
    private void setViewPager() {
        viewPagerAdapter = new LogInViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageMargin(80);

        dotsIndicator.setupWithViewPager(viewPager, true);

        View dot;
        ViewGroup dotsContainer = (ViewGroup) dotsIndicator.getChildAt(0);
        ViewGroup.MarginLayoutParams p;

        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            dot = dotsContainer.getChildAt(i);
            p = (ViewGroup.MarginLayoutParams) dot.getLayoutParams();
            p.setMargins(5, 0, 5, 0);
            dot.requestLayout();
        }
    }

    private boolean checkConfigurationFile() {
        if (!new File(Environment.getExternalStorageDirectory() + File.separator + getString(R.string.server_ini_file_name)).exists()) {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file not found", Log.VERBOSE);
            return false;
        } else {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file found", Log.INFO);
            return true;
        }
    }

    private void parseServerConfFile() {
        File serverSetupFile = new File(Environment.getExternalStorageDirectory() + File.separator + getString(R.string.server_ini_file_name));
        if (!GeneralUtility.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            GeneralUtility.requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, permissionReqID);
        } else {
            ConfigureServerAsyncTask configureServerAsyncTask = new ConfigureServerAsyncTask(this);
            configureServerAsyncTask.execute(serverSetupFile);
        }
    }
}
