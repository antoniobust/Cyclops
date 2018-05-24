package com.mdmobile.cyclops.ui.main.myDevices;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.DevicesListAdapter;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.sync.DevicesSyncAdapter;
import com.mdmobile.cyclops.ui.BasicFragment;
import com.mdmobile.cyclops.ui.dialogs.PinFolderDialog;
import com.mdmobile.cyclops.ui.dialogs.SortingDeviceDialog;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.RecyclerEmptyView;
import com.mdmobile.cyclops.utils.ServerUtility;

import static android.content.Context.SEARCH_SERVICE;


public class DevicesFragment extends BasicFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener,
        SwipeRefreshLayout.OnRefreshListener {

    private final static String LOG_TAG = DevicesFragment.class.getSimpleName();
    private final static String SEARCH_QUERY_KEY = "searchQueryKey";
    private final String sortingOptionKey = "SortingOptionKey";
    private final String pinnedFolderOptionKey = "PinnedFolderOptionKey";
    RecyclerEmptyView recyclerView;
    TextView emptyView;
    private DevicesListAdapter mAdapter;
    private SharedPreferences preferences;
    private int currentSortingOption;
    private String currentPinnedPath;
    private TextView filtersView;
    private SwipeRefreshLayout mSwipeToRefresh;

    public DevicesFragment() {
        // Required empty public constructor
    }

    public static DevicesFragment newInstance() {
        return new DevicesFragment();
    }

    // -- Interface methods
    //********************* SearchView Interface methods **********************************************************
    @Override
    public boolean onQueryTextSubmit(String query) {
        //On text changed send an intent to devices list fragment so it refreshes the listView with results
        Bundle args = new Bundle(1);
        args.putString(SEARCH_QUERY_KEY, query);

        if (isAdded()) {
            getLoaderManager().restartLoader(1, args, this);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }
    //************************************************************************************************************

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        Bundle args = new Bundle();
        args.putInt(sortingOptionKey, currentSortingOption);
        args.putString(pinnedFolderOptionKey, currentPinnedPath);
        getLoaderManager().restartLoader(1, args, this);
        return true;
    }

    @Override
    public void changeServerContent() {
        initializeLoader();
    }

    @Override
    public void onRefresh() {
        Logger.log(LOG_TAG, "Devices refresh manually requested... ", Log.VERBOSE);
        Account account = AccountManager.get(getContext()).getAccountsByType(getString(R.string.account_type))[0];
        ProgressBar progressBar = getActivity().findViewById(R.id.loading_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        Bundle b = new Bundle();
        b.putBoolean(DevicesSyncAdapter.SYNC_DEVICES,true);
        DevicesSyncAdapter.syncImmediately(account,b);
        mSwipeToRefresh.setRefreshing(false);
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Get sorting option from arguments and create query
        String sortingParameter, pathSelection = "", searchQuery = "";

        Server server = ServerUtility.getActiveServer();

        if (args.containsKey(SEARCH_QUERY_KEY)) {
            searchQuery = args.getString(SEARCH_QUERY_KEY);
        }

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

        if (searchQuery != null && !searchQuery.equals("")) {
            //TODO: search for devices properties Name ecc
//            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? " + " OR "
//                    + McContract.Device.COLUMN_EXTRA_INFO + " LIKE ? ";
//            String[] arguments = {"%" + searchQuery + "%", "%" + searchQuery + "%"};

            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? OR " + McContract.Device.COLUMN_PATH
                    + " LIKE ? OR " + McContract.Device.COLUMN_DEVICE_ID + " LIKE ?";

            String[] arguments = {"%" + searchQuery + "%", "%" + searchQuery, "%" + searchQuery + "%"};

            return new CursorLoader(getContext(), McContract.Device.buildUriWithServerName(server.getServerName()), null,
                    selection, arguments, sortingParameter);

        } else {
            String selection;

            String[] projection = {McContract.Device.COLUMN_DEVICE_ID, McContract.Device.COLUMN_DEVICE_NAME,
                    McContract.Device.COLUMN_PLATFORM, McContract.Device.COLUMN_AGENT_ONLINE, McContract.Device.COLUMN_PATH};

            if (pathSelection != null && !pathSelection.equals("")) {
                if (!pathSelection.equals("")) {
                    selection = McContract.Device.COLUMN_PATH + " = ?";
                    String[] arguments = {pathSelection};
//                    return new CursorLoader(getContext(), McContract.Device.buildUriWithServerName(server.getServerName()), projection, selection, arguments, sortingParameter);
                    return new CursorLoader(getContext(), McContract.Device.buildUriWithServerName(server.getServerName()), projection, selection, arguments, sortingParameter);
                }
            }
            return new CursorLoader(getContext(), McContract.Device.buildUriWithServerName(server.getServerName()), projection, null, null, sortingParameter);
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
//        getActivity().supportStartPostponedEnterTransition();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        filtersView = (TextView) getActivity().findViewById(R.id.device_filters_view);
//        filtersView.setVisibility(View.VISIBLE);

        preferences = getActivity().getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_PRIVATE);
        currentSortingOption = preferences.getInt(getString(R.string.sorting_shared_preference), 0);
        currentPinnedPath = preferences.getString(getString(R.string.folder_preference), "");
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Apply style to fragment
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        //Clone inflater using the contextTHemeWrapper
        inflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = inflater.inflate(R.layout.fragment_devices, container, false);

        recyclerView = rootView.findViewById(R.id.devices_recycler);
        recyclerView.setHasFixedSize(true);
        emptyView = rootView.findViewById(R.id.devices_recycler_empty_view);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setEmptyView(emptyView);

        //Set Swipe to refresh layout
        mSwipeToRefresh = rootView.findViewById(R.id.devices_swipe_refresh);
        mSwipeToRefresh.setOnRefreshListener(this);

        //Create an adapter for recycler
        mAdapter = new DevicesListAdapter(getContext(), null, (DevicesListAdapter.DeviceSelected) getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set the adapter to the recycler
        recyclerView.setAdapter(mAdapter);
        initializeLoader();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.devices_action_menu, menu);
        //Get search view and set searchable conf
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.main_activity_search_button).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.main_activity_search_button), this);

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
    public void onPause() {
        super.onPause();
        //Workaround for fragments overlapping while swipe for update is loading new data
        if (mSwipeToRefresh != null) {
            mSwipeToRefresh.setRefreshing(false);
            mSwipeToRefresh.destroyDrawingCache();
            mSwipeToRefresh.clearAnimation();
        }
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        preferences.unregisterOnSharedPreferenceChangeListener(this);
//        filtersView.setVisibility(View.GONE);
    }

    public void initializeLoader() {
        Bundle args = new Bundle();
        args.putInt(sortingOptionKey, currentSortingOption);
        args.putString(pinnedFolderOptionKey, currentPinnedPath);
        if (getLoaderManager().getLoader(1) == null) {
            getLoaderManager().initLoader(1, args, this);
        } else {
            getLoaderManager().restartLoader(1, args, this);
        }
    }
}
