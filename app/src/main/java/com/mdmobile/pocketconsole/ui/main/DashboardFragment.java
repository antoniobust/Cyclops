package com.mdmobile.pocketconsole.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.ChartsAdapter;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DevicesStatsCalculator;

import java.util.ArrayList;


public class DashboardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, DevicesStatsCalculator.Listener {

    private RecyclerView recyclerView;


    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        inflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.dashboard_recycler_view);

        if (MainActivity.TABLET_MODE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        ChartsAdapter recyclerAdapter = new ChartsAdapter(null);
        recyclerView.setAdapter(recyclerAdapter);

//        //Get devices pie chart
//        devicesChart = (PieChart) rootView.findViewById(R.id.devices_chart);
//        //Get Online devices chart
//        onlineDevicesChart = (PieChart) rootView.findViewById(R.id.online_devices_chart);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Initialize loader
        getLoaderManager().initLoader(20, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

//        String[] projection = {McContract.Device.COLUMN_AGENT_ONLINE, McContract.Device.COLUMN_FAMILY,
//                McContract.Device.COLUMN_KIND};

        return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        DevicesStatsCalculator statsTask = new DevicesStatsCalculator();
        statsTask.registerListener(this);
        statsTask.execute(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void OnFinished(ArrayList<Object> result) {
        recyclerView.swapAdapter(new ChartsAdapter(result), true);
    }
}