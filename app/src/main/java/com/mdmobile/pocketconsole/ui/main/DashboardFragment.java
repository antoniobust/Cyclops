package com.mdmobile.pocketconsole.ui.main;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;


public class DashboardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private PieChart devicesChart;
    private PieChart onlineDevicesChart;


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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //Get devices pie chart
        devicesChart = (PieChart) rootView.findViewById(R.id.devices_chart);
        //Get Online devices chart
        onlineDevicesChart = (PieChart) rootView.findViewById(R.id.online_devices_chart);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Initialize loader
        getLoaderManager().initLoader(20, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {McContract.Device.COLUMN_AGENT_ONLINE, McContract.Device.COLUMN_FAMILY,
                McContract.Device.COLUMN_KIND};

        return new CursorLoader(getContext(), McContract.Device.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        getDevicesCounters(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        getDevicesCounters(null);

    }


    private void getDevicesCounters(Cursor data) {
        int deviceCount = data== null ? 0 : data.getCount();
        int onlineDeviceCounter = 0;
        int androidCounter = 0;
        int iosCounter = 0;
        int windowsMobileCounter = 0;
        int windowsDesktopCounter = 0;
        int windowsModernCounter = 0;
        int printersCounter = 0;
        String deviceFamily;

        if(data!= null && data.moveToFirst()) {
            //Get online device count
            do {
                if (data.getInt(0) == 1) {
                    onlineDeviceCounter++;
                }
                deviceFamily = data.getString(1);
                if (deviceFamily.startsWith("Android")) {
                    androidCounter++;
                } else if (deviceFamily.equals("Apple")) {
                    iosCounter++;
                } else if (deviceFamily.equals("WindowsCE")) {
                    windowsMobileCounter++;
                } else if (deviceFamily.equals("WindowsDesktop")) {
                    windowsDesktopCounter++;
                } else if (deviceFamily.equals("WindowsPhone") || deviceFamily.equals("WindowsRuntime")) {
                    windowsModernCounter++;
                } else if (deviceFamily.equals("Printer")) {
                    printersCounter++;
                }
            } while (data.moveToNext());
        }
        setDeviceCounterCharts(androidCounter, iosCounter, windowsDesktopCounter, windowsMobileCounter, windowsModernCounter, printersCounter);
        setOnlineDevicesChart();
    }

    private void setDeviceCounterCharts(int androidCounter, int iosDevices, int windowsDesktop, int windowsMobile,
                                        int windowsModern, int printers) {
        //Set chart data
        List<PieEntry> pieEntries = new ArrayList<>();

        if (androidCounter > 0) {
            pieEntries.add(new PieEntry(androidCounter, "Android"));
        }
        if (iosDevices > 0) {
            pieEntries.add(new PieEntry(iosDevices, "iOS"));
        }
        if (windowsDesktop > 0) {
            pieEntries.add(new PieEntry(windowsDesktop, "Windows Desktop"));
        }
        if (windowsMobile > 0) {
            pieEntries.add(new PieEntry(windowsMobile, "Windows Mobile"));
        }
        if (windowsModern > 0) {
            pieEntries.add(new PieEntry(windowsModern, "Windows Modern"));
        }
        if (printers > 0) {
            pieEntries.add(new PieEntry(printers, "Printers"));
        }

        //Style chart
        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setColors(new int[]{R.color.androidColor, R.color.iosColor, R.color.windowsDesktopColor,
                        R.color.windowsMobileColor, R.color.printerColor},
                getContext());

        pieDataSet.setSelectionShift(12.0f);

        PieData pieData = new PieData();
        pieData.addDataSet(pieDataSet);

        Legend legend = devicesChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(8f);
        legend.setXEntrySpace(14f);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend.setMaxSizePercent(0.05f);



        devicesChart.setData(pieData);
        devicesChart.setDescription(null);
        devicesChart.setNoDataText(getString(R.string.no_data_found_chart));
        devicesChart.setNoDataTextColor(getResources().getColor(R.color.colorAccent));
        devicesChart.setCenterText(getString(R.string.device_count_chart_description));
        devicesChart.setTransparentCircleRadius(60f);
        devicesChart.animateX(1500, Easing.EasingOption.EaseInOutCirc);
        devicesChart.invalidate();

    }

    private void setOnlineDevicesChart() {
//        onlineDevicesChart.setData(pieData);
//        onlineDevicesChart.invalidate();
    }
}
