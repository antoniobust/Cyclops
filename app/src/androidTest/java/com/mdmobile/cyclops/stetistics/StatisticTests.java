package com.mdmobile.cyclops.stetistics;

import android.content.Context;

import org.junit.Before;

import androidx.test.InstrumentationRegistry;

/**
 * Test class for statistic that populates  charts in dashboard
 */

public class StatisticTests {

    Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getContext();

    }

//    @Test
//    public void deviceOsTest() {
//        CounterStatistic stat = (CounterStatistic) StatsManager.createFactory(Statistic.SUM_STAT, McContract.Device.COLUMN_FAMILY);
//        stat.initPoll(mContext);
//        assertTrue("Statistic returned 0 platforms", stat.getStatsData().length > 0);
//
//    }

}
