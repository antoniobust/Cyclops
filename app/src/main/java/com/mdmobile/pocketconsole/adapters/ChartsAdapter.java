package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.ui.ViewHolder.ChartViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart recycler view adapter
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    private Bundle statistics;

    public ChartsAdapter(@Nullable Bundle data) {
        if (data != null) {
            statistics = data;
        }
        setHasStableIds(true);
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

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_recycler_item, parent, false);
        ChartViewHolder holder = new ChartViewHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {

        if (statistics == null || statistics.size() == 0) {
            return; //TODO: set empty states
        }

        List<PieEntry> pieEntries;
        PieDataSet pieDataSet;
        View chart;

        switch (position) {
            case 0:
                //Online vs offline devices
                pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(statistics.getInt("OnlineDevs"), "OnLine"));
                pieEntries.add(new PieEntry(statistics.getInt("OfflineDevs"), "OffLine"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(new int[]{R.color.colorPrimaryDark, R.color.colorPrimary}, holder.chartContainer.getContext());
                PieData pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(22f);

                chart = createPieChart(holder.chartContainer.getContext(), pieData, position);
                holder.chartContainer.addView(chart);
                chart.invalidate();
                break;

            case 1:
                //Device platforms
                pieEntries = new ArrayList<>();

                pieEntries.add(new PieEntry(statistics.getInt("Android"), "Android"));
                pieEntries.add(new PieEntry(statistics.getInt("Apple"), "iOS"));
                pieEntries.add(new PieEntry(statistics.getInt("WindowsDesktop"), "Desktop"));
                pieEntries.add(new PieEntry(statistics.getInt("WindowsCE"), "Windows CE / Mobile"));
                pieEntries.add(new PieEntry(statistics.getInt("WindowsModern"), "Windows Modern"));
                pieEntries.add(new PieEntry(statistics.getInt("Printer"), "Zebra Printers"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(
                        new int[]{R.color.androidColor, R.color.iosSpaceGrey, R.color.windowsColor, R.color.windowsColor,
                                R.color.darkGreen, R.color.printerColor}, holder.chartContainer.getContext());
                pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(16f);

                chart = createPieChart(holder.chartContainer.getContext(), pieData, position);
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
        return 4;
    }

    private PieChart createPieChart(Context mContext, PieData data, int position) {

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

        Description descriptionLabel = new Description();
        descriptionLabel.setEnabled(true);
        descriptionLabel.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        descriptionLabel.setTextSize(16f);

        switch (position) {
            case 0:
                descriptionLabel.setText(mContext.getString(R.string.offline_chart_label));
                break;
            case 1:
                descriptionLabel.setText(mContext.getString(R.string.platform_chart_label));
                break;
        }
        pieChart.setDescription(descriptionLabel);

        return pieChart;
    }

    private void createBarsChart() {

    }
}
