package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mdmobile.pocketconsole.R;

import java.util.ArrayList;
import java.util.List;

import static com.mdmobile.pocketconsole.ui.main.dashboard.ChartFactory.BAR_CHART;
import static com.mdmobile.pocketconsole.ui.main.dashboard.ChartFactory.PIE_CHART;

/**
 * Chart recycler nameView adapter
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartViewHolder> implements OnChartValueSelectedListener {

    private final int PLATFORM_CHART = 0;
    private final int ONLINE_CHART = 1;
    private final int BATTERY_CHART = 2;
    private final int OUT_OF_CONTACT_CHART = 3;
    private final int[] CHART_SET = {PLATFORM_CHART, ONLINE_CHART, OUT_OF_CONTACT_CHART, BATTERY_CHART};
    private IChartFactory chartFactory;

    private Bundle statistics;
    private String[] enabledCharts;

    public ChartsAdapter(Context c, @Nullable Bundle data) {
        if (data != null) {
            statistics = data;
        }
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case PLATFORM_CHART:
                return PIE_CHART;
            case ONLINE_CHART:
                return PIE_CHART;
            case OUT_OF_CONTACT_CHART:
                return BAR_CHART;
            case BATTERY_CHART:
                return BAR_CHART;
            default:
                return super.getItemViewType(position);
        }

    }

    public int getItemCount() {
        return CHART_SET.length;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_recycler_item, parent, false);
        ChartViewHolder vh = new ChartViewHolder(item);
        return vh;
    }


    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {

        if (statistics == null || statistics.size() == 0) {
            holder.emptyView.setVisibility(View.VISIBLE);
            return;
        }

        chartFactory = ChartFactory.instantiate(holder.chartContainer.getContext(),
                holder.getItemViewType());

        Chart chart = chartFactory.createChart(holder.chartContainer.getContext());

        switch (position) {
            case ONLINE_CHART: {
                List<PieEntry> pieEntries;
                PieDataSet pieDataSet;
                PieData pieData = new PieData();

                pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(statistics.getInt("OnlineDevs"), "Online"));
                pieEntries.add(new PieEntry(statistics.getInt("OfflineDevs"), "Offline"));

                pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(new int[]{R.color.colorPrimaryDark, R.color.colorPrimary}, holder.chartContainer.getContext());
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(0f);

                chart = createPieChart(holder.chartContainer.getContext(), (PieChart) chart, pieData, position);
                holder.chartContainer.addView(chart);
                holder.chartContainer.invalidate();
                break;
            }
            case PLATFORM_CHART: {
                List<PieEntry> pieEntries;
                PieDataSet pieDataSet;
                pieEntries = new ArrayList<>();
                PieData pieData = new PieData();

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
                pieData.addDataSet(pieDataSet);
                pieData.setValueTextSize(0f);

                chart = createPieChart(holder.chartContainer.getContext(), (PieChart) chart, pieData, position);
                holder.chartContainer.addView(chart);
                chart.invalidate();
                break;
            }
            case OUT_OF_CONTACT_CHART:
                List<BarEntry> entries = new ArrayList<>();
                break;
            case BATTERY_CHART:
                break;
        }
    }

    private PieChart createPieChart(Context mContext, PieChart pieChart, PieData data, int position) {
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

    private HorizontalBarChart createBarsChart() {
return null;

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
