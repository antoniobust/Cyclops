package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.ContentResolver;

import com.mdmobile.pocketconsole.provider.McContract;

import java.util.List;

/**
 * Counter statistic, given a property groups the device per property value and counts
 * elements
 */

public class CounterStat extends Statistic {

    CounterStat(ContentResolver cr, List<String> properties) {
        super(cr, properties);
    }

    @Override
    public void initPoll() {
        final String count = "COUNT(" + McContract.Device._ID + ")";
        final String orderBy = "COUNT(?) DESC";

        for (int i = 0; i < mProperties.size(); i++) {
            startQuery(i,
                    mProperties.get(i),
                    McContract.Device.buildUriWithGroup(mProperties.get(i)),
                    new String[]{count, mProperties.get(i)},
                    null,
                    null,
                    orderBy.replace("?", mProperties.get(i)));
        }
    }
}

