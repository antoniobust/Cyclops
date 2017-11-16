package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.BarChart;
import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.LineChart;
import com.mdmobile.pocketconsole.ui.main.dashboard.Charts.PieChart;

import java.util.HashMap;

/**
 * Class responsible for managing charts creation
 */

public class ChartFactoryManager {

    public static final int PIE_CHART = 1;
    public static final int BAR_CHART = 2;
    public static final int LINE_CHART = 3;
    private static SparseArray<IChartFactory> factories = new SparseArray<>();

    public static IChartFactory instantiate(@NonNull Context context, @NonNull int id, @NonNull int type) {
        IChartFactory mFactory = createChart(context, type);
        factories.put(id, mFactory);
        return mFactory;
    }

    public static void getFactory(@NonNull int id) {
        factories.get(id);
    }

    private static IChartFactory createChart(Context context, int type) {
        switch (type) {
            case PIE_CHART:
                return new PieChart(context);
            case BAR_CHART:
                return new BarChart(context);
            case LINE_CHART:
                return new LineChart(context);
            default:
                throw new UnsupportedOperationException("Non supported chart type:" + type);
        }
    }
}
