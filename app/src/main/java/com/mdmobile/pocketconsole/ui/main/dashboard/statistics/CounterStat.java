package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.ContentResolver;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Counter statistic, given a property groups the device per property value and counts
 * elements
 */

public class CounterStat extends Statistic {

    CounterStat(ContentResolver cr, String... property) {
        super(cr, property);
    }

    @Override
    public void initPoll() {
        final String count = "COUNT(" + McContract.Device._ID + ")";
        final String orderBy = "COUNT(?) DESC";

        for (int i = 0; i < mProperties.length; i++) {
            startQuery(i,
                    mProperties[i],
                    McContract.Device.buildUriWithGroup(mProperties[i]),
                    new String[]{count, mProperties[i]},
                    null,
                    null,
                    orderBy.replace("?", mProperties[i]));
        }
    }
}

