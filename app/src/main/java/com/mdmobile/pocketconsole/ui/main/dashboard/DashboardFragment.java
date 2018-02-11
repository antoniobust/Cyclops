package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.dataModels.api.sharedPref.ChartSharedPref;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.CounterStat;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.Statistic;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatisticFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DashboardFragment extends Fragment implements Statistic.IStatisticReady,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private CounterStat counterStat;
    private ChartsAdapter recyclerAdapter;
    private SharedPreferences preferences;
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
            recyclerAdapter.resetCharts();
            createCharts();
        }
    }


    @Override
    public void getData(int statId, Bundle values) {
        recyclerAdapter.addNewStat(values);
    }


    // -- Lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = getActivity().getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_PRIVATE);
//        String currentPreference = preferences.getString(getString(R.string.charts_preference), "");
//        Type listType = new TypeToken<ArrayList<ChartSharedPref>>() {
//        }.getType();
//        Gson gson = new Gson();
//        if (!currentPreference.isEmpty()) {
//            currentCharts = gson.fromJson(currentPreference, listType);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        inflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.dashboard_recycler_view);

        if (MainActivity.TABLET_MODE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

//        ChartsAdapter recyclerAdapter = new ChartsAdapter(getContext(), null);
        recyclerAdapter = new ChartsAdapter(null, null);
        recyclerView.setAdapter(recyclerAdapter);

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
        if (chartList != null) {
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

}