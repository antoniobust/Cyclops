package com.mdmobile.pocketconsole.ui.main.dashboard;

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
import com.mdmobile.pocketconsole.utils.LabelHelper;
import com.mdmobile.pocketconsole.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Chart recycler nameView adapter
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartViewHolder> {

    private final String LOG_TAG = ChartsAdapter.class.getSimpleName();
    private ArrayList<ArrayList<StatValue>> chartValues;
    private ArrayList<String> chartsProperties;

    public ChartsAdapter(@Nullable ArrayList<StatValue> data, @Nullable List<String> chartsProperties) {
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
        createPieChart(chart, position);

        holder.chartContainer.addView(chart);
    }


    public void resetCharts() {
        Logger.log(LOG_TAG, "Scrapping old chartValues, replacing with new ones", Log.VERBOSE);
        this.chartsProperties = new ArrayList<>();
        this.chartValues = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addNewStat(@NonNull Bundle val) {
        Set<String> keySet = val.keySet();
        ArrayList<StatValue> valueList;
        resetCharts();
        for (String key : keySet) {
            Logger.log(LOG_TAG, "Current chartValues size: " + getItemCount() + " adding: " + key + " chart to adapter", Log.VERBOSE);
            valueList = val.getParcelableArrayList(key);
            chartValues.add(valueList);
            chartsProperties.add(key);
        }
        notifyDataSetChanged();
    }

    private void createPieChart(PieChart pieChart, int position) {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet pieDataSet;
        PieData pieData = new PieData();
        ArrayList<StatValue> statValues = chartValues.get(position);

        for (StatValue value : statValues) {
            pieEntries.add(new PieEntry(value.getValue(), value.getLabel()));
        }


        pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieData.addDataSet(pieDataSet);
        pieData.setValueTextSize(0f);

        pieChart.setData(pieData);

        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(8f);
        legend.setXEntrySpace(14f);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setMaxSizePercent(0.1f);

        String chartProperty = chartsProperties.get(position);
        chartProperty = LabelHelper.Companion.getUiLabelFor(chartProperty);
        Description descriptionLabel = new Description();
        descriptionLabel.setEnabled(true);
        descriptionLabel.setText(chartProperty);

        pieChart.setDescription(descriptionLabel);
    }

    static class ChartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView refreshButton;
        FrameLayout chartContainer;
        TextView emptyView;

        ChartViewHolder(View itemView) {
            super(itemView);
            chartContainer = itemView.findViewById(R.id.chart_container);
            refreshButton = itemView.findViewById(R.id.chart_option_button);
            emptyView = itemView.findViewById(R.id.empty_view);
            refreshButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}