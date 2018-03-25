package com.mdmobile.cyclopes.ui.main.dashboard.statistics;

import android.content.Context;

import java.util.List;

/**
 * Factory that creates statistic classes
 */
public class StatisticFactory {

    public StatisticFactory() {
    }

    public static Statistic createStatistic(Context c, int statisticType, List<String> properties) {
        switch (statisticType) {
            case Statistic.COUNTER_STAT:
                return new CounterStat(c.getContentResolver(), properties);
            case Statistic.COUNTER_RANGE:
                return new CounterStat(c.getContentResolver(), properties);
            default:
                throw new UnsupportedOperationException("Statistic type:" + statisticType + " not supported");
        }
    }
}

