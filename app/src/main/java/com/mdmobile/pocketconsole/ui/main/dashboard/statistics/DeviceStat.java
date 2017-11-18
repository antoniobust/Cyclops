package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Responsible for carrying oit a statistic on Device properties
 */

public class DeviceStat extends Statistic {

    public DeviceStat(String element) {
        super(element);
    }

    @Override
    public void initPoll(Context context) {
        Uri uri = McContract.Device.buildUriWithGroup(element);
        Cursor c = context.getContentResolver()
                .query(uri, new String[]{element}, null, null, null);
        this.entries = this.statValuesFromCursor(c);
        if (c != null) {
            c.close();
        }
    }
}

