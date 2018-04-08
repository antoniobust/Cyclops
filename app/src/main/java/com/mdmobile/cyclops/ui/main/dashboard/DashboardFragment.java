package com.mdmobile.cyclops.ui.main.dashboard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModels.api.sharedPref.ChartSharedPref;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.ui.main.dashboard.statistics.CounterStat;
import com.mdmobile.cyclops.ui.main.dashboard.statistics.Statistic;
import com.mdmobile.cyclops.ui.main.dashboard.statistics.StatisticFactory;
import com.mdmobile.cyclops.utils.RecyclerEmptyView;
import com.mdmobile.cyclops.utils.UserUtility;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DashboardFragment extends Fragment implements Statistic.IStatisticReady,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private CounterStat counterStat;
    private ChartsAdapter recyclerAdapter;
    private SharedPreferences preferences;
    private RecyclerEmptyView chartsRecycler;
//    private ArrayList<ChartSharedPref> currentCharts;


    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    // --  Interfaces methods
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.charts_preference))) {
            String prefJson = preferences.getString(key, null);
            if (prefJson == null) {
                return;
            }
            createCharts();
        }
    }

    @Override
    public void getData(int statId, Bundle values) {
        //Poll finished update charts list
        recyclerAdapter.addNewStat(values);
        chartsRecycler.swapAdapter(recyclerAdapter, true);
    }


    // -- Lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = getActivity().getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        inflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        chartsRecycler = rootView.findViewById(R.id.dashboard_recycler_view);


        if (MainActivity.TABLET_MODE) {
            chartsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        } else {
            chartsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        recyclerAdapter = new ChartsAdapter(null, null);
        chartsRecycler.setAdapter(recyclerAdapter);
        chartsRecycler.setEmptyView(rootView.findViewById(R.id.dashboard_recycler_empty_view));


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_fragment_menu, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createCharts();
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
        if (counterStat != null) {
            counterStat.unRegisterListener();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_chart) {
            AddChartDialog dialog = AddChartDialog.Companion.createDialog();
            dialog.show(getChildFragmentManager(), null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createCharts() {
        String jsonPref = preferences.getString(getString(R.string.charts_preference), "");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ChartSharedPref>>() {
        }.getType();

        ArrayList<ChartSharedPref> chartList = gson.fromJson(jsonPref, listType);

        ArrayList<String> properties = new ArrayList<>(chartList.size());
        for (ChartSharedPref chart : chartList) {
            properties.add(chart.property1);
        }


        //TODO this only creates counter stat type implement other stat type
        counterStat = (CounterStat)
                StatisticFactory.createStatistic(getContext(), 1, properties);
        counterStat.registerListener(this);
        counterStat.initPoll();
    }

}