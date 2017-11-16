package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;

import com.github.mikephil.charting.charts.Chart;

/**
 * Abstraction for chart creation
 */

public interface IChartFactory {
    Chart createChart(Context context);
}
