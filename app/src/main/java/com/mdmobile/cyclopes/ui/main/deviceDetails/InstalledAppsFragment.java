package com.mdmobile.cyclopes.ui.main.deviceDetails;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.dataModels.api.InstalledApp;

import java.util.ArrayList;

import static com.mdmobile.cyclopes.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_APPLICATIONS_EXTRA_KEY;
import static com.mdmobile.cyclopes.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;

public class InstalledAppsFragment extends Fragment {
    private String deviceId;
    private RecyclerView appsRecycler;
    private ArrayList<InstalledApp> applications;

    public InstalledAppsFragment() {

    }

    public static InstalledAppsFragment newInstance(@NonNull String devId, @NonNull ArrayList<InstalledApp> appList) {
        Bundle arg = new Bundle(1);
        arg.putString(DEVICE_ID_EXTRA_KEY, devId);
        arg.putParcelableArrayList(DEVICE_APPLICATIONS_EXTRA_KEY, appList);
        InstalledAppsFragment frag = new InstalledAppsFragment();
        frag.setArguments(arg);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            deviceId = getArguments().getString(DEVICE_ID_EXTRA_KEY, "");
            applications = getArguments().getParcelableArrayList(DEVICE_APPLICATIONS_EXTRA_KEY);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_installed_apps, container, false);
        appsRecycler = rootView.findViewById(R.id.installed_apps_recycler);
        appsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<String[]> info = new ArrayList<>();
        for (InstalledApp app : applications) {
            info.add(new String[]{app.getName(), app.getApplicationId(), app.getStatus()});
        }
        InfoAdapter mAdapter = new InfoAdapter(info, false);
        appsRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.installed_apps_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.show_info_action || super.onOptionsItemSelected(item);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
