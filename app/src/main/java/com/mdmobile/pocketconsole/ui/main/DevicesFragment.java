package com.mdmobile.pocketconsole.ui.main;


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
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.DevicesListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;


public class DevicesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String LOG_TAG = DevicesFragment.class.getSimpleName();
    RecyclerView recyclerView;
    private DevicesListAdapter mAdapter;
    private String searchQuery;

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
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.devices_fragment_menu,menu);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (searchQuery != null) {
            //TODO: search for devices properties name ecc
//            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? " + " OR "
//                    + McContract.Device.COLUMN_EXTRA_INFO + " LIKE ? ";
//            String[] arguments = {"%" + searchQuery + "%", "%" + searchQuery + "%"};

            String selection = McContract.Device.COLUMN_DEVICE_NAME + " LIKE ? ";
            String[] arguments = {"%" + searchQuery + "%"};
            return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, null,
                    selection, arguments, McContract.Device.COLUMN_DEVICE_NAME);
        } else {
            String[] projection = {McContract.Device.COLUMN_DEVICE_ID, McContract.Device.COLUMN_DEVICE_NAME,
                    McContract.Device.COLUMN_PLATFORM, McContract.Device.COLUMN_AGENT_ONLINE};
            return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, projection, null, null, null);
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

}
