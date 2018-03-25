package com.mdmobile.cyclopes.sync;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.apiManager.ApiRequestManager;
import com.mdmobile.cyclopes.utils.Logger;
import com.mdmobile.cyclopes.utils.ServerUtility;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Sync adapter to get info refreshed in the DB.
 */

public class DevicesSyncAdapter extends AbstractThreadedSyncAdapter {
    // Update schedule is 2h by default
    private static int UPDATE_SCHEDULE = 60 * 60;
    private static int SYNC_FLEXTIME = UPDATE_SCHEDULE / 3;


    DevicesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public static void initializeSync(Account account, Context mContext) {
        onNewAccountCreated(mContext, account);
    }

    //When adding an account call this method to schedule device information updates
    private static void onNewAccountCreated(Context c, Account account) {

        //Without calling setSyncAutomatically, periodic sync will not be enabled.
        ContentResolver.setSyncAutomatically(account, c.getString(R.string.content_authority), true);

        DevicesSyncAdapter.configurePeriodicSync(c.getApplicationContext(), account);

        //As the account was just created we should launch the first sync
        syncImmediately(c, account);
    }

    //Helper method to set up a period sync interval
    private static void configurePeriodicSync(Context context, Account account) {
        String authority = context.getString(R.string.content_authority);
        Logger.log(LOG_TAG, "Configuring periodic sync: " + UPDATE_SCHEDULE, Log.VERBOSE);

        // we can enable inexact timers in our periodic sync
        SyncRequest request = new SyncRequest.Builder().
                syncPeriodic(UPDATE_SCHEDULE, SYNC_FLEXTIME).
                setSyncAdapter(account, authority).
                setExtras(new Bundle()).build();

        ContentResolver.requestSync(request);
    }

    public static void syncImmediately(Context context, Account account) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        Logger.log(LOG_TAG, "Immediate Sync requested", Log.VERBOSE);
        if (!ContentResolver.isSyncActive(account, context.getString(R.string.content_authority))) {
            ContentResolver.requestSync(account, context.getString(R.string.content_authority), bundle);
        }
    }

    @Override
    public void onPerformSync(final Account account, Bundle bundle, String authority,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {

        Bundle serverInfo = ServerUtility.getServer();
        if (serverInfo == null) {
            Logger.log(LOG_TAG, "No Server Found...\nSkipping Sync", Log.ERROR);
            return;
        }

        ApiRequestManager.getInstance().getDeviceInfo();
        ApiRequestManager.getInstance().getUsers();
        ApiRequestManager.getInstance().getServerInfo();
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
    }
}
