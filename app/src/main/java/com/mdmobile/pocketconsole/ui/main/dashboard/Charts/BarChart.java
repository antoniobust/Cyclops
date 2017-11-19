package com.mdmobile.pocketconsole.ui.main.dashboard.Charts;

import android.content.Context;

import com.github.mikephil.charting.charts.Chart;
import com.mdmobile.pocketconsole.ui.main.dashboard.IChartFactory;


public class BarChart extends com.github.mikephil.charting.charts.BarChart implements IChartFactory {

    //-- Constructor
    public BarChart(Context context) {
        super(context);
    }

    //-- Interface method
    @Override
    public Chart createChart(Context context) {
        return null;
    }

    @Override
    public void setData(Chart data) {

    }

}
