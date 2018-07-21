package com.mdmobile.cyclops.ui.logIn;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.LogInViewPagerAdapter;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.ConfigureServerAsyncTask;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerXmlConfigParser;
import com.mdmobile.cyclops.utils.UserUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Fragment displayed to add a new server
 */

public class AddServerFragment extends Fragment implements ServerXmlConfigParser.ServerXmlParse, View.OnClickListener {

    public static final int EXTERNAL_STORAGE_READ_PREMISSION = 100;
    private final static String LOG_TAG = AddServerFragment.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout dotsIndicator;
    private LogInViewPagerAdapter viewPagerAdapter;
    private View rootView;
    private Button addServerButton;
    private ArrayList<Server> servers = new ArrayList<>();
    private EditText serverNameEditText, apiSecretEditText, clientIdEditText, serverAddressEditText;

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

//        ((TextView) rootView.findViewById(R.id.server_name_text_view)).setText(serverInfo.getServerName());
//        ((TextView) rootView.findViewById(R.id.api_secret_text_view)).setText(serverInfo.getApiSecret());
//        ((TextView) rootView.findViewById(R.id.client_id_text_view)).setText(serverInfo.getClientId());
//        ((TextView) rootView.findViewById(R.id.server_address_text_view)).setText(serverInfo.getServerAddress());

        saveServer(allServerInfo);
    }

    //"Save server" Onclick listener
    @Override
    public void onClick(View view) {
        String serverName = ((TextView) rootView.findViewById(R.id.server_name_text_view)).getText().toString();
        String secret = ((TextView) rootView.findViewById(R.id.api_secret_text_view)).getText().toString();
        String clientId = ((TextView) rootView.findViewById(R.id.client_id_text_view)).getText().toString();
        String address = ((TextView) rootView.findViewById(R.id.server_address_text_view)).getText().toString();

        if (!address.startsWith("https://")) {
            address = "https://" + address;
        }
        servers.add(new Server(serverName, secret, clientId, address, -1, -1));

        saveServer(servers);
        if (((LoginActivity) Objects.requireNonNull(getActivity())).activityForResult) {
            Intent returnIntent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_server, container, false);

        //Instantiate views
        viewPager = rootView.findViewById(R.id.login_add_server_view_pager);
        dotsIndicator = rootView.findViewById(R.id.login_view_pager_dots_indicator);
        addServerButton = rootView.findViewById(R.id.add_server_button);
        serverNameEditText = viewPager.findViewById(R.id.server_name_text_view);
        apiSecretEditText = viewPager.findViewById(R.id.api_secret_text_view);
        clientIdEditText = viewPager.findViewById(R.id.client_id_text_view);
        serverAddressEditText = rootView.findViewById(R.id.server_address_text_view);

        addServerButton.setOnClickListener(this);

        setViewPager();
//        if (checkStoragePermission()) {
            parseServerConfigFile();
//        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!UserUtility.checkAnyUserLogged()) {
            getActivity().findViewById(R.id.add_user_button).setVisibility(View.VISIBLE);
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

    public boolean checkStoragePermission() {
        if (!GeneralUtility.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                return false;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_READ_PREMISSION);
                return false;
            }
        } else {
            return true;
        }
    }

    void parseServerConfigFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            File serverSetupFile = new File(Environment.getExternalStorageDirectory(), getString(R.string.server_ini_file_name));
            ConfigureServerAsyncTask configureServerAsyncTask = new ConfigureServerAsyncTask(this);
            configureServerAsyncTask.execute(serverSetupFile);
        } else {
            Logger.log(LOG_TAG, "Storage not available at the moment", Log.INFO);
            Toast.makeText(getContext(), "Storage not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveServer(ArrayList<Server> servers) {
        ArrayList<ContentValues> values = new ArrayList<>(servers.size());
        for (Server s : servers) {
            values.add(s.toContentValues());
        }
        servers.get(0).setActive();
        ContentValues[] vals = values.toArray(new ContentValues[servers.size()]);
        getContext().getContentResolver().bulkInsert(McContract.ServerInfo.CONTENT_URI, vals);
    }
}
