package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.ContentResolver;
import android.net.Uri;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Counter statistic, given a property groups the device per property value and counts
 * elements
 */

public class CounterStat extends Statistic {

    CounterStat(ContentResolver cr, String property) {
        super(cr, property);
    }

    @Override
    public void initPoll() {
        final Uri uri = McContract.Device.buildUriWithGroup(mProperty);
        final String count = "COUNT(" + McContract.Device._ID + ")";
        final String orderBy = "COUNT(" + mProperty + ")" + " DESC";
        startQuery(1, null, uri, new String[]{count, mProperty}, null, null, orderBy);
    }
}

