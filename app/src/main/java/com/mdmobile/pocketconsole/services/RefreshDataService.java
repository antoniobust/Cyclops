package com.mdmobile.pocketconsole.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiHandler.ApiRequestManager;
import com.mdmobile.pocketconsole.utils.Logger;

/**
 * Refresh data service
 */

public class RefreshDataService extends IntentService {
    private final static String LOG_TAG = RefreshDataService.class.getSimpleName();

    //Default constructor
    public RefreshDataService() {
        super(RefreshDataService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(getApplicationContext().getString(R.string.download_devices_action))) {
                Logger.log(LOG_TAG, "Initializing devices download", Log.INFO);
                downloadDevices();
            }

        }
    }

    private void downloadDevices() {
        ApiRequestManager.getInstance(getApplicationContext()).getDevices();
    }
}
