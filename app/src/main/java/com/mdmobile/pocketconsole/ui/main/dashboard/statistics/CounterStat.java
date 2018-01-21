package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Counter statistic, given a property groups the device per property value and counts
 * elements
 */

public class CounterStat extends Statistic {

    public CounterStat(String property) {
        super(property);
    }

    @Override
    public void initPoll(Context context) {
        Cursor c;
        Uri uri = McContract.Device.buildUriWithGroup(mProperty);
        String count = "COUNT(" + McContract.Device._ID + ")";
        c = context.getContentResolver()
                .query(uri, new String[]{count,mProperty}, null, null, null);

        entries = statValuesFromCursor(c);
        if (c != null) {
            c.close();
        }
    }
}

