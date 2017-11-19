package com.mdmobile.pocketconsole.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.ui.main.dashboard.ChartFactoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart recycler nameView adapter
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartViewHolder> implements OnChartValueSelectedListener {

    private static int ONLINE_CHART = 0;
    private static int MANUFACTURERS_CHART = 1;
    private static int OUT_OF_CONTACT_CHART = 2;
    private static int BATTERY_CHART = 3;

    private Bundle statistics;
    private String[] enabledCharts;

    public ChartsAdapter(Context c, @Nullable Bundle data) {
        if (data != null) {
            statistics = data;
        }
        setHasStableIds(true);
//        enabledCharts = c.getApplicationContext().getSharedPreferences
//                (c.getString(R.string.shared_preference), Context.MODE_PRIVATE)
//                .getStringSet(c.getString(R.string.charts_preference),);
//        )

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
        return new ChartViewHolder(item);
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
                pieEntries.add(new PieEntry(statistics.getInt("OnlineDevs"), "Online"));
                pieEntries.add(new PieEntry(statistics.getInt("OfflineDevs"), "Offline"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(new int[]{R.color.colorPrimaryDark, R.color.colorPrimary}, holder.chartContainer.getContext());
                PieData pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(0f);

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
                pieEntries.add(new PieEntry(statistics.getInt("WindowsCE"), "Win CE / Mobile"));
                pieEntries.add(new PieEntry(statistics.getInt("WindowsModern"), "Win Modern"));
                pieEntries.add(new PieEntry(statistics.getInt("Printer"), "Printers"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(
                        new int[]{R.color.androidColor, R.color.iosSpaceGrey, R.color.windowsColor, R.color.windowsColor,
                                R.color.darkGreen, R.color.printerColor}, holder.chartContainer.getContext());
                pieData = new PieData();
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(0f);

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
        return 6;
    }

    private PieChart createPieChart(Context mContext, PieData data, int position) {

        PieChart pieChart =
                (PieChart) ChartFactoryManager.instantiate(mContext, position, ChartFactoryManager.PIE_CHART).createChart(mContext);
        pieChart.setData(data);


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


        switch (position) {
            case 0:
                descriptionLabel.setText(mContext.getString(R.string.offline_chart_label));
                break;
            case 1:
                descriptionLabel.setText(mContext.getString(R.string.platform_chart_label));
                break;
        }
        pieChart.setDescription(descriptionLabel);
        pieChart.setOnChartValueSelectedListener(this);

        return pieChart;
    }

    private void createBarsChart() {

    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {
//        highlight
    }

    @Override
    public void onNothingSelected() {

    }

    static class ChartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView refreshButton;
        FrameLayout chartContainer;

        ChartViewHolder(View itemView) {
            super(itemView);
            chartContainer = itemView.findViewById(R.id.chart_container);
            refreshButton = itemView.findViewById(R.id.chart_refresh_button);
            refreshButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
