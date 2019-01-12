package com.mdmobile.cyclops.ui.logIn;

import android.Manifest;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.LogInViewPagerAdapter;
import com.mdmobile.cyclops.dataModel.Instance;
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

public class AddServerFragment extends Fragment implements ServerXmlConfigParser.ServerXmlParse {

    public static final int EXTERNAL_STORAGE_READ_PERMISSION = 100;
    private final static String LOG_TAG = AddServerFragment.class.getSimpleName();
    public EditText serverNameEditText, apiSecretEditText, clientIdEditText, serverAddressEditText;
    public boolean xmlParsedFlag = false;
    private ViewPager viewPager;
    private TabLayout dotsIndicator;
    private LogInViewPagerAdapter viewPagerAdapter;
    private View rootView;

    public AddServerFragment() {
        //Empty constructor
    }

    public static AddServerFragment newInstance() {

        return new AddServerFragment();
    }

    //Interface methods
    @Override
    public void xmlParseComplete(ArrayList<Instance> allInstanceInfo) {
        rootView.findViewById(R.id.server_conf_read_label).setVisibility(View.VISIBLE);
        ((LoginActivity) Objects.requireNonNull(getActivity())).getInstanceList().addAll(allInstanceInfo);
        xmlParsedFlag = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_server, container, false);

        //Instantiate views
        viewPager = rootView.findViewById(R.id.login_add_server_view_pager);
        dotsIndicator = rootView.findViewById(R.id.login_view_pager_dots_indicator);
        setViewPager();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginActivity hosting = ((LoginActivity) Objects.requireNonNull(getActivity()));
        hosting.getActionChip().setText(R.string.add_instance_label);
        hosting.getActionChip()
                .setCompoundDrawablesWithIntrinsicBounds(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_add),
                        null, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkConfigurationFile()) {
            if (checkStoragePermission()) {
                parseServerConfigFile();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
        if (!GeneralUtility.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                return false;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_READ_PERMISSION);
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

    public Instance grabServerInfo() {
        String serverName = ((TextView) rootView.findViewById(R.id.server_name_text_view)).getText().toString();
        String secret = ((TextView) rootView.findViewById(R.id.api_secret_text_view)).getText().toString();
        String clientId = ((TextView) rootView.findViewById(R.id.client_id_text_view)).getText().toString();
        String address = ((TextView) rootView.findViewById(R.id.server_address_text_view)).getText().toString();


        if (serverName.isEmpty() || secret.isEmpty() || clientId.isEmpty() || address.isEmpty()) {
            return null;
        }
        if (!address.startsWith("https://")) {
            address = "https://" + address;
        }
        return new Instance(serverName, secret, clientId, address, -1, -1);
    }

    void saveServer(ArrayList<Instance> instances) {
        ArrayList<ContentValues> values = new ArrayList<>(instances.size());
        for (Instance s : instances) {
            values.add(s.toContentValues());
        }
        instances.get(0).setActive();
        ContentValues[] vals = values.toArray(new ContentValues[instances.size()]);
        Objects.requireNonNull(getContext()).getContentResolver().bulkInsert(McContract.ServerInfo.CONTENT_URI, vals);
    }
}
