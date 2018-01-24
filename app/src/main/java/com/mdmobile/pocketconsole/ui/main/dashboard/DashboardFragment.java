package com.mdmobile.pocketconsole.ui.main.dashboard;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.CounterStat;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.Statistic;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatisticFactory;
import com.mdmobile.pocketconsole.utils.DevicesStatsCalculator;


public class DashboardFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, DevicesStatsCalculator.Listener, Statistic.IStatisticReady {

    private RecyclerView recyclerView;
    private CounterStat counterStat;
    private NewChartsAdapter recyclerAdapter;


    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    // --  Interfaces methods
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
    public void OnFinished(Bundle result) {
//        recyclerView.swapAdapter(new NewChartsAdapter(getContext(), result), true);
    }

    @Override
    public void getData(Bundle value) {
        recyclerAdapter.addNewStat(value);
    }


    // -- Lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        inflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = rootView.findViewById(R.id.dashboard_recycler_view);

        if (MainActivity.TABLET_MODE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

//        ChartsAdapter recyclerAdapter = new ChartsAdapter(getContext(), null);
        recyclerAdapter = new NewChartsAdapter(getContext(), null, null);
        recyclerView.setAdapter(recyclerAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_fragment_menu, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Initialize loader
//        getLoaderManager().initLoader(20, null, this);
        counterStat = (CounterStat)
                StatisticFactory.createStatistic(getContext(), Statistic.COUNTER_STAT, McContract.Device.COLUMN_MANUFACTURER);
        counterStat.registerListener(this);
        counterStat.initPoll();
    }

    @Override
    public void onPause() {
        super.onPause();
        counterStat.unRegisterListener();
    }
}