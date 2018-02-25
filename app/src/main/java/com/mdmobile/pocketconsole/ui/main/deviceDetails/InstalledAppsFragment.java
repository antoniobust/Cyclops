package com.mdmobile.pocketconsole.ui.main.deviceDetails;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.InstalledAppsAdapter;
import com.mdmobile.pocketconsole.provider.McContract;

import static com.mdmobile.pocketconsole.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY;

public class InstalledAppsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private String deviceId;
    private GridView gridView;
    private InstalledAppsAdapter mAdapter;

    public InstalledAppsFragment() {

    }

    public static InstalledAppsFragment newInstance(@NonNull String devId) {
        Bundle arg = new Bundle(1);
        arg.putString(DEVICE_ID_EXTRA_KEY, devId);
        InstalledAppsFragment frag = new InstalledAppsFragment();
        frag.setArguments(arg);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        deviceId = getArguments().getString(DEVICE_ID_EXTRA_KEY, "");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_installed_apps, container, false);
        gridView = rootView.findViewById(R.id.installed_apps_gridView);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new InstalledAppsAdapter(getActivity(), null, 0);
        gridView.setAdapter(mAdapter);
        getLoaderManager().initLoader(100, null, this);


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


    //********************* LOADERS CALLBACKS ****************************************
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Changing projection elements remember to change data access methods in the adapter
        final String projection[] = {McContract.InstalledApplications._ID, McContract.InstalledApplications.APPLICATION_NAME,
                McContract.InstalledApplications.APPLICATION_STATUS, McContract.InstalledApplications.APPLICATION_ID,
                McContract.InstalledApplications.DEVICE_ID};
        final String selection = McContract.InstalledApplications.DEVICE_ID.concat(" = ?");
        final String[] selectionArgs = {deviceId};
        Uri uri = McContract.InstalledApplications.buildUriWithDevId(deviceId);
        return new CursorLoader(getActivity().getApplicationContext(), uri, projection, selection, selectionArgs,
                McContract.InstalledApplications.APPLICATION_NAME.concat(" ASC "));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    //********************************************************************************

}
