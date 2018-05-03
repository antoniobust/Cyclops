package com.mdmobile.cyclops.ui.main.dashboard.statistics;

import android.content.ContentResolver;

import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.ServerUtility;

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
        final String orderBy = "GROUP BY ?";

        Server s = ServerUtility.getActiveServer();

        for (int i = 0; i < mProperties.size(); i++) {
            startQuery(i,
                    mProperties.get(i),
                    McContract.Device.buildUriWithServerName(s.getServerName()),
                    new String[]{count, mProperties.get(i)},
                    null,
                    null,
                    orderBy.replace("?", mProperties.get(i)));
        }
    }
}

