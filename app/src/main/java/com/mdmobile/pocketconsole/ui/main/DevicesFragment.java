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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.DevicesListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.ViewHolder.ImageTextImageViewHolder;


        public class DevicesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String LOG_TAG = DevicesFragment.class.getSimpleName();
    private DevicesListAdapter mAdapter;
    RecyclerView recyclerView;

    public DevicesFragment() {
        // Required empty public constructor
    }

    public static DevicesFragment newInstance() {
        return new DevicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, null, null, null, null);

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
