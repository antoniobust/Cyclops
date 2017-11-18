package com.mdmobile.pocketconsole.stetistics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.DeviceStat;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.Statistic;
import com.mdmobile.pocketconsole.ui.main.dashboard.statistics.StatsManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for statistic that populates  charts in dashboard
 */

public class StatisticTests {

    Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();

    }

    @Test
    public void manufacturerTest() {
        DeviceStat stat = (DeviceStat) StatsManager.createFactory(Statistic.DEVICE_STAT, McContract.Device.COLUMN_MANUFACTURER);
        stat.initPoll(mContext);

        assertTrue("Statistic returned 0 manufacturer", stat.getData().length > 0);

        int counter = stat.getPopulationSize();
        int deviceCount = mContext.getContentResolver().query(McContract.Device.CONTENT_URI, null, null, null).getCount();
        assertTrue("Devices counter:" + deviceCount + " devices from statistic: " + counter, counter == deviceCount);

    }


}
