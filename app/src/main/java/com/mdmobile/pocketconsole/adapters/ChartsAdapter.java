package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.ViewHolder.ChartViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Chart recycler view adapter
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    private Cursor mCursor;

    public ChartsAdapter(@Nullable Cursor cursor) {
        if (cursor != null) {
            mCursor = cursor;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_recycler_item, parent,false);
        ChartViewHolder holder = new ChartViewHolder(item);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {

        if (mCursor == null || !mCursor.moveToFirst()) {
            return; //TODO: set empty states
        }

        ArrayList<Object> result = fetchDeviceData();
        List<PieEntry> pieEntries;
        PieDataSet pieDataSet;
        View chart;

        switch (position) {
            case 0:
                //Online vs offline devices
                pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry((int) result.get(0), "OnLine"));
                pieEntries.add(new PieEntry(mCursor.getCount() - (int) result.get(0), "OffLine"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(new int[]{R.color.colorPrimaryDark, R.color.colorPrimary}, holder.chartContainer.getContext());
                PieData pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                createPieChart(holder.chartContainer.getContext(), pieData);

                chart = createPieChart(holder.chartContainer.getContext(), pieData);
                holder.chartContainer.addView(chart);
                chart.invalidate();
                break;

            case 1:
                //Device platforms
                pieEntries = new ArrayList<>();

                ArrayMap<String, Integer> platforms = (ArrayMap<String, Integer>) result.get(1);
                pieEntries.add(new PieEntry(platforms.get("Android"), "Android"));
                pieEntries.add(new PieEntry(platforms.get("iOS"), "iOS"));
                pieEntries.add(new PieEntry(platforms.get("WindowsCE"), "WindowsCE"));
                pieEntries.add(new PieEntry(platforms.get("Desktop"), "Desktop"));
                pieEntries.add(new PieEntry(platforms.get("Windows CE / Mobile"), "Windows CE / Mobile"));
                pieEntries.add(new PieEntry(platforms.get("Zebra Printers"), "Zebra Printers"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(
                        new int[]{R.color.androidColor, R.color.iosSpaceGrey, R.color.windowsColor, R.color.windowsColor,
                                R.color.darkGreen, R.color.printerColor}, holder.chartContainer.getContext());
                pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                createPieChart(holder.chartContainer.getContext(), pieData);

                chart = createPieChart(holder.chartContainer.getContext(), pieData);
                holder.chartContainer.addView(chart);
                chart.invalidate();
                break;
            case 2:
                //Out of contact
                break;
            case 3:
                //Average memory
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;
        if (mCursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    private ArrayList<Object> fetchDeviceData() {
        int onlineDeviceCounter = 0, totMemoryCounter = 0;
        int androidCounter = 0;
        int iosCounter = 0;
        int windowsMobileCounter = 0;
        int windowsDesktopCounter = 0;
        int windowsModernCounter = 0;
        int printersCounter = 0;
        String deviceFamily;
        ArrayList<Object> results = new ArrayList<>();

        mCursor.moveToFirst();
        do {
            if (mCursor.getInt(mCursor.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1) {
                onlineDeviceCounter++;
            }

            deviceFamily = mCursor.getString(mCursor.getColumnIndex(McContract.Device.COLUMN_FAMILY));
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
        } while (mCursor.moveToNext());
        results.add(0, onlineDeviceCounter);
        ArrayMap<String, Integer> platformCounter = new ArrayMap<>();
        platformCounter.put("Android", androidCounter);
        platformCounter.put("iOS", iosCounter);
        platformCounter.put("WindowsCE", windowsMobileCounter);
        platformCounter.put("Desktop", windowsDesktopCounter);
        platformCounter.put("Windows CE / Mobile", windowsMobileCounter);
        platformCounter.put("Zebra Printers", printersCounter);
        results.add(1, platformCounter);

        return results;
    }

    private PieChart createPieChart(Context mContext, PieData data) {

        PieChart pieChart = new PieChart(mContext);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(true);
        pieChart.setMaxAngle(360f);
        pieChart.setHoleRadius(50f);
        pieChart.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(8f);
        legend.setXEntrySpace(14f);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setMaxSizePercent(0.1f);

        return pieChart;
    }

    private void createBarsChart() {

    }
}
