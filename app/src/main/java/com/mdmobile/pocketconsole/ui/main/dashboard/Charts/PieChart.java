package com.mdmobile.pocketconsole.ui.main.dashboard.Charts;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.Chart;
import com.mdmobile.pocketconsole.ui.main.dashboard.IChartFactory;


public class PieChart extends com.github.mikephil.charting.charts.PieChart implements IChartFactory {

    private PieChart chart;

    //-- Constructor
    public PieChart(Context context) {
        super(context);
    }

    //-- Interface method
    @Override
    public Chart createChart(Context context) {
        chart = new PieChart(context);
        chart.setDrawEntryLabels(false);
        chart.setMaxAngle(360f);
        chart.setHoleRadius(60f);
        chart.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return chart;
    }

    @Override
    public void setData(Chart data) {
        chart.setData(data);
    }

}
