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
        final String count = "COUNT(" + McContract.DEVICE_TABLE_NAME+"."+McContract.Device._ID + ")";
        final String orderBy = "COUNT(?) DESC";

        Server s = ServerUtility.getActiveServer();
        for (int i = 0; i < mProperties.size(); i++) {
            startQuery(i,
                    mProperties.get(i),
                    McContract.Device.buildUriWithGroup(mProperties.get(i)),
                    new String[]{count, mProperties.get(i)},
                    McContract.SERVER_INFO_TABLE_NAME+"."+McContract.ServerInfo.NAME + " = ? ",
                    new String[]{s.getServerName()},
                    orderBy.replace("?", mProperties.get(i)));
        }
    }
}

