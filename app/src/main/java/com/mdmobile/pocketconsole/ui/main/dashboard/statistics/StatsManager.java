package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Manager for statistics jobs
 */

public class StatsManager extends ContentObserver {

    public StatsManager(Handler handler) {
        super(handler);
    }

    public static IStatisticFactory createFactory(int statisticType, String element) {
        if (statisticType == Statistic.DEVICE_STAT) {
            return new DeviceStat(element);
        }
        return null;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if (!uri.equals(McContract.Device.CONTENT_URI)) {
            return;
        }
        //TODO: start stat calc service
    }

}
