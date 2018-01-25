package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.Context;

/**
 * Factory that creates statistic classes
 */
public class StatisticFactory {

    public StatisticFactory() {
    }

    public static Statistic createStatistic(Context c, int statisticType, String... properties) {
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

