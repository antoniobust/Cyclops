package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

/**
 * Factory that creates statistic classes
 */

public interface IStatisticFactory {
    Statistic createStatistic(int statisticType, String element);
}
