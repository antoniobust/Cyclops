package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

/**
 * Factory that creates statistic classes
 */
public class StatisticFactory {

    public StatisticFactory() {
    }

    public static Statistic createStatistic(int statisticType, String property) {
        switch (statisticType) {
            case Statistic.COUNTER_STAT:
                return new CounterStat(property);
            case Statistic.COUNTER_RANGE:
                return null;
            default:
                throw new UnsupportedOperationException("Statistic type:" + statisticType + " not supported");
        }
    }
}

