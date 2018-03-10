package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.BarChart;
import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.LineChart;
import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.PieChart;

/**
 * Class responsible for managing charts creation
 */

public class ChartFactory {

    public static final int PIE_CHART = 0;
    public static final int BAR_CHART = 1;
    public static final int HORIZONTAL_BAR_CHART = 2;

    public static IChartFactory instantiate(@NonNull Context context, int type) {
        return createChart(context, type);
    }

    private static IChartFactory createChart(Context context, int type) {
        switch (type) {
            case PIE_CHART:
                return new PieChart(context);
            case BAR_CHART:
                return new BarChart(context);
            case HORIZONTAL_BAR_CHART:
                return new BarChart(context);
            default:
                throw new UnsupportedOperationException("Non supported chart type:" + type);
        }
    }
}
