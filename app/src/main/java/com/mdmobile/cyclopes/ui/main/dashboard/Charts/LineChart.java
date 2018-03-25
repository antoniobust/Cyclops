package com.mdmobile.cyclopes.ui.main.dashboard.Charts;

import android.content.Context;

import com.github.mikephil.charting.charts.Chart;
import com.mdmobile.cyclopes.ui.main.dashboard.IChartFactory;


public class LineChart extends com.github.mikephil.charting.charts.LineChart implements IChartFactory {

    // - Constructor
    public LineChart(Context context) {
        super(context);
    }



    // - Interface method
    @Override
    public void setData(Chart data) {

    }
    @Override
    public Chart createChart(Context context) {
        return null;
    }
}
