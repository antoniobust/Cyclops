package com.mdmobile.pocketconsole.stetistics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.CounterStat;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatValue;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.Statistic;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatisticFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

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
//        assertTrue("Statistic returned 0 platforms", stat.getData().length > 0);
//
//    }

}
