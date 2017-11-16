package com.mdmobile.pocketconsole.ui.main.dashboard.Charts;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.Chart;
import com.mdmobile.pocketconsole.ui.main.dashboard.IChartFactory;


public class PieChart extends com.github.mikephil.charting.charts.PieChart implements IChartFactory {

    //-- Constructor
    public PieChart(Context context) {
        super(context);
    }

    //-- Interface method
    @Override
    public Chart createChart(Context context) {
        PieChart pieChart = new PieChart(context);
        pieChart.setDrawEntryLabels(false);
        pieChart.setMaxAngle(360f);
        pieChart.setHoleRadius(60f);
        pieChart.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return pieChart;
    }

}
