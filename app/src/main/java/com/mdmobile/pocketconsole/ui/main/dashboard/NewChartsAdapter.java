package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatValue;
import com.mdmobile.pocketconsole.utils.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Chart recycler nameView adapter
 */

public class NewChartsAdapter extends RecyclerView.Adapter<NewChartsAdapter.ChartViewHolder> {

    private final String LOG_TAG = NewChartsAdapter.class.getSimpleName();
    private ArrayList<ArrayList<StatValue>> chartValues;
    private ArrayList<String> chartsProperties;

    public NewChartsAdapter(@Nullable ArrayList<StatValue> data, @Nullable List<String> chartsProperties) {
        if (data != null && chartsProperties != null) {
            chartValues.add(data);
            this.chartsProperties.addAll(chartsProperties);
        }
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public int getItemCount() {
        return chartValues == null || chartValues.isEmpty() ? 0 : chartValues.size();
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
        if (getItemCount() == 0) {
            return;
        }
        //TODO: this will always create a pie chart -> fix this
        IChartFactory chartFactory = ChartFactory.instantiate(holder.chartContainer.getContext(),
                holder.getItemViewType());
        PieChart chart = (PieChart) chartFactory.createChart(holder.chartContainer.getContext());
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet pieDataSet;
        PieData pieData = new PieData();
        ArrayList<StatValue> statValues = chartValues.get(position);

        for (StatValue value : statValues) {
            pieEntries.add(new PieEntry(value.getValue(), value.getLabel()));
        }

        pieDataSet = new PieDataSet(pieEntries,null);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieData.addDataSet(pieDataSet);
        pieData.setValueTextSize(0f);

        chart = createPieChart(chart, pieData, position);
        holder.chartContainer.addView(chart);
        holder.chartContainer.invalidate();

    }


    public void resetCharts(){
        Logger.log(LOG_TAG, "Scrapping old chartValues, replacing with new ones", Log.VERBOSE);
        this.chartsProperties = new ArrayList<>();
        this.chartValues = new ArrayList<>();
    }

    public void addNewStat(@NonNull Bundle val) {
        Set<String> keySet = val.keySet();
        ArrayList<StatValue> valueList;
        for (String key : keySet) {
            Logger.log(LOG_TAG, "Current chartValues size: " + getItemCount() + " adding: " + key + " chart to adapter", Log.VERBOSE);
            valueList = val.getParcelableArrayList(key);

            if (getItemCount() == 0) {
                chartValues = new ArrayList<>();
                chartsProperties = new ArrayList<>();
            } else if(chartsProperties.contains(key)){
                continue;
            }
            chartValues.add(valueList);
            chartsProperties.add(key);
        }
        this.notifyDataSetChanged();
    }

    private PieChart createPieChart(PieChart pieChart, PieData data, int position) {
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
        descriptionLabel.setText(chartsProperties.get(position));

        pieChart.setDescription(descriptionLabel);

        return pieChart;
    }

    static class ChartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView refreshButton;
        FrameLayout chartContainer;
        TextView emptyView;

        ChartViewHolder(View itemView) {
            super(itemView);
            chartContainer = itemView.findViewById(R.id.chart_container);
            refreshButton = itemView.findViewById(R.id.chart_refresh_button);
            emptyView = itemView.findViewById(R.id.empty_view);
            refreshButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

//switch (position) {
//        case ONLINE_CHART: {
//        List<PieEntry> pieEntries;
//        PieDataSet pieDataSet;
//        PieData pieData = new PieData();
//
//        pieEntries = new ArrayList<>();
//        pieEntries.add(new PieEntry(chartValues.getInt("OnlineDevs"), "Online"));
//        pieEntries.add(new PieEntry(chartValues.getInt("OfflineDevs"), "Offline"));
//
//        pieDataSet = new PieDataSet(pieEntries, null);
//        pieDataSet.setColors(new int[]{R.color.colorPrimaryDark, R.color.colorPrimary}, holder.chartContainer.getContext());
//        pieData.addDataSet(pieDataSet);
//        pieData.setValueTextSize(0f);
//
//        chart = createPieChart(holder.chartContainer.getContext(), (PieChart) chart, pieData, position);
//        holder.chartContainer.addView(chart);
//        holder.chartContainer.invalidate();
//        break;
//        }
//        case PLATFORM_CHART: {
//        List<PieEntry> pieEntries;
//        PieDataSet pieDataSet;
//        pieEntries = new ArrayList<>();
//        PieData pieData = new PieData();
//
//        pieEntries.add(new PieEntry(chartValues.getInt("Android"), "Android"));
//        pieEntries.add(new PieEntry(chartValues.getInt("Apple"), "iOS"));
//        pieEntries.add(new PieEntry(chartValues.getInt("WindowsDesktop"), "Desktop"));
//        pieEntries.add(new PieEntry(chartValues.getInt("WindowsCE"), "Win CE / Mobile"));
//        pieEntries.add(new PieEntry(chartValues.getInt("WindowsModern"), "Win Modern"));
//        pieEntries.add(new PieEntry(chartValues.getInt("Printer"), "Printers"));
//
//        pieDataSet = new PieDataSet(pieEntries, null);
//        pieDataSet.setColors(
//        new int[]{R.color.androidColor, R.color.iosSpaceGrey, R.color.windowsColor, R.color.windowsColor,
//        R.color.darkGreen, R.color.printerColor}, holder.chartContainer.getContext());
//        pieData.addDataSet(pieDataSet);
//        pieData.setValueTextSize(0f);
//
//        chart = createPieChart(holder.chartContainer.getContext(), (PieChart) chart, pieData, position);
//        holder.chartContainer.addView(chart);
//        chart.invalidate();
//        break;
//        }
//        case OUT_OF_CONTACT_CHART:
//        List<BarEntry> entries = new ArrayList<>();
//        break;
//        case BATTERY_CHART:
//        break;
//        }
