package com.mdmobile.cyclops.ui.logIn;

import android.Manifest;
import android.content.ContentValues;
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
import android.widget.EditText;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.LogInViewPagerAdapter;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.ConfigureServerAsyncTask;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerXmlConfigParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Fragment displayed to add a new server
 */

public class AddServerFragment extends Fragment implements ServerXmlConfigParser.ServerXmlParse,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private final static String LOG_TAG = AddServerFragment.class.getSimpleName();
    public ArrayList<Server> servers = new ArrayList<>();
    public EditText serverNameEditText, apiSecretEditText, clientIdEditText, serverAddressEditText;
    public boolean xmlParsedFlag = false;
    private int permissionReqID = 100;
    private ViewPager viewPager;
    private TabLayout dotsIndicator;
    private View rootView;

    public AddServerFragment() {
        //Empty constructor
    }

    public static AddServerFragment newInstance() {

        return new AddServerFragment();
    }

    //Interface methods
    @Override
    public void xmlParseComplete(ArrayList<Server> allServerInfo) {
        rootView.findViewById(R.id.server_conf_read_label).setVisibility(View.VISIBLE);
        servers.addAll(allServerInfo);
        saveServer(allServerInfo);
        xmlParsedFlag = true;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_server, container, false);

        //Instantiate views
        viewPager = rootView.findViewById(R.id.login_add_server_view_pager);
        dotsIndicator = rootView.findViewById(R.id.login_view_pager_dots_indicator);
        serverNameEditText = viewPager.findViewById(R.id.server_name_text_view);
        apiSecretEditText = viewPager.findViewById(R.id.api_secret_text_view);
        clientIdEditText = viewPager.findViewById(R.id.client_id_text_view);
        serverAddressEditText = rootView.findViewById(R.id.server_address_text_view);

        setViewPager();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (checkConfigurationFile()) {
            parseServerConfFile();
        }
        LoginActivity hosting = ((LoginActivity) Objects.requireNonNull(getActivity()));
        hosting.actionChip.setText(R.string.add_new_server_label);
        hosting.actionChip
                .setCompoundDrawablesWithIntrinsicBounds(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_add),
                        null, null, null);
    }

    //Convenience method to set up the view pager
    private void setViewPager() {
        LogInViewPagerAdapter viewPagerAdapter = new LogInViewPagerAdapter(getChildFragmentManager());
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, permissionReqID);
            }
        } else {
            ConfigureServerAsyncTask configureServerAsyncTask = new ConfigureServerAsyncTask(this);
            configureServerAsyncTask.execute(serverSetupFile);
        }
    }

    public void saveServer(ArrayList<Server> servers) {
        ArrayList<ContentValues> values = new ArrayList<>(servers.size());
        for (Server s : servers) {
            values.add(s.toContentValues());
        }
        servers.get(0).setActive();
        ContentValues[] vals = values.toArray(new ContentValues[servers.size()]);
        Objects.requireNonNull(getContext()).getContentResolver().bulkInsert(McContract.ServerInfo.CONTENT_URI, vals);
    }
}
