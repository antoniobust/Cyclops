package com.mdmobile.pocketconsole.ui.main;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.DevicesListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.Dialogs.PinFolderDialog;
import com.mdmobile.pocketconsole.ui.Dialogs.SortingDeviceDialog;


public class DevicesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final static String LOG_TAG = DevicesFragment.class.getSimpleName();
    private final String sortingOptionKey = "SortingOptionKey";
    private final String pinnedFolderOptionKey = "PinnedFolderOptionKey";

    RecyclerView recyclerView;
    private DevicesListAdapter mAdapter;
    private String searchQuery;
    private SharedPreferences preferences;
    private int currentSortingOption;
    private String currentPinnedPath;
    private TextView filtersView;

    public DevicesFragment() {
        // Required empty public constructor
    }

    public static DevicesFragment newInstance() {
        return new DevicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null && getArguments().containsKey(MainActivity.SEARCH_QUERY_KEY)) {
            searchQuery = getArguments().getString(MainActivity.SEARCH_QUERY_KEY);
        }

//        filtersView = (TextView) getActivity().findViewById(R.id.device_filters_view);
//        filtersView.setVisibility(View.VISIBLE);

        preferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        currentSortingOption = preferences.getInt(getString(R.string.sorting_shared_preference), 0);
        currentPinnedPath = preferences.getString(getString(R.string.folder_preference), "");
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_devices, container, false);

        //Get recycler instance
        recyclerView = (RecyclerView) rootView.findViewById(R.id.devices_recycler);

        recyclerView.setHasFixedSize(true);
        //Set recycler layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create an adapter
        mAdapter = new DevicesListAdapter(null);
        recyclerView.setAdapter(mAdapter);
        Bundle args = new Bundle();
        args.putInt(sortingOptionKey, currentSortingOption);
        args.putString(pinnedFolderOptionKey, currentPinnedPath);
        getLoaderManager().initLoader(1, args, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.devices_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.devices_menu_sort_action:
                SortingDeviceDialog dialog = new SortingDeviceDialog();
                dialog.show(getFragmentManager(), null);
                return true;

            case R.id.devices_menu_pin_folder_action:
                PinFolderDialog dialog1 = new PinFolderDialog();
                dialog1.show(getFragmentManager(), null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        preferences.unregisterOnSharedPreferenceChangeListener(this);
//        filtersView.setVisibility(View.GONE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Get sorting option from arguments and create query
        String sortingParameter, pathSelection = "";

        switch (args.getInt(sortingOptionKey)) {
            case SortingDeviceDialog.DEVICE_NAME:
                sortingParameter = McContract.Device.COLUMN_DEVICE_NAME + " ASC";
                break;
            case SortingDeviceDialog.DEVICE_NAME_INVERTED:
                sortingParameter = McContract.Device.COLUMN_DEVICE_NAME + " DESC";
                break;
            case SortingDeviceDialog.DEVICE_ONLINE:
                sortingParameter = McContract.Device.COLUMN_AGENT_ONLINE + " DESC";
                break;
            case SortingDeviceDialog.DEVICE_OFFLINE:
                sortingParameter = McContract.Device.COLUMN_AGENT_ONLINE + " ASC";
                break;
            default:
                sortingParameter = null;
        }

        //Get Pinned folder if set
        if (args.containsKey(pinnedFolderOptionKey)) {
            pathSelection = args.getString(pinnedFolderOptionKey);
        }

        if (searchQuery != null) {
            //TODO: search for devices properties name ecc
//            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? " + " OR "
//                    + McContract.Device.COLUMN_EXTRA_INFO + " LIKE ? ";
//            String[] arguments = {"%" + searchQuery + "%", "%" + searchQuery + "%"};

            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? ";

            String[] arguments = {"%" + searchQuery + "%"};

            return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, null,
                    selection, arguments, sortingParameter);

        } else {
            String selection;

            String[] projection = {McContract.Device.COLUMN_DEVICE_ID, McContract.Device.COLUMN_DEVICE_NAME,
                    McContract.Device.COLUMN_PLATFORM, McContract.Device.COLUMN_AGENT_ONLINE, McContract.Device.COLUMN_PATH};

            if (pathSelection != null && !pathSelection.equals("")) {
                if (!pathSelection.equals("")) {
                    selection = McContract.Device.COLUMN_PATH + " = ?";
                    String[] arguments = {pathSelection};
                    return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, projection, selection, arguments, sortingParameter);
                }
            }

            return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, projection, null, null, sortingParameter);
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Check if preference changed is sorting one

        if (isAdded()) {
            if (key.equals(getString(R.string.sorting_shared_preference))) {
                currentSortingOption = sharedPreferences.getInt(key, 0);
                Bundle args = new Bundle();
                args.putInt(sortingOptionKey, currentSortingOption);
                args.putString(pinnedFolderOptionKey, currentPinnedPath);
                //restart loader with new sorting option
                getLoaderManager().restartLoader(1, args, this);

            } else if (key.equals(getString(R.string.folder_preference))) {
                currentPinnedPath = sharedPreferences.getString(key, "");
                Bundle args = new Bundle();
                args.putInt(sortingOptionKey, currentSortingOption);
                args.putString(pinnedFolderOptionKey, currentPinnedPath);
                //restart loader with new sorting option
                getLoaderManager().restartLoader(1, args, this);
            }
        }

    }
}
