package com.mdmobile.pocketconsole.ui.main.dashboard;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Class managing statistic jobs for charts
 */

public class StatsManager extends ContentObserver {

    private ChartDataSet mCallback;

    public StatsManager(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if(!uri.equals(McContract.Device.CONTENT_URI)){
            return;
        }
        //TODO: start stat calc service
    }

    public void regiseterChartReloadListener(ChartDataSet callback){
        mCallback = callback;
    }

    public void unRegiseterChartReloadListener(ChartDataSet callback){
        mCallback = null;
    }

}
