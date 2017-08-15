package com.mdmobile.pocketconsole.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdmobile.pocketconsole.ApplicationLoader;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
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
        Account account = AccountManager.get(getApplicationContext())
                .getAccountsByType(getApplicationContext().getString(R.string.account_type))[0];

        ApiRequestManager.getInstance().getDevices(account);
    }
}
