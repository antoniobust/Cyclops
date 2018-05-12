package com.mdmobile.cyclops.sync;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;

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
        ContentResolver.setSyncAutomatically(account, McContract.CONTENT_AUTHORITY, true);

        DevicesSyncAdapter.configurePeriodicSync(c.getApplicationContext(), account);

        //As the account was just created we should launch the first sync
        syncImmediately(account);
    }

    //Helper method to set up a period sync interval
    private static void configurePeriodicSync(Context context, Account account) {
        String authority = McContract.CONTENT_AUTHORITY;
        Logger.log(LOG_TAG, "Configuring periodic sync: " + UPDATE_SCHEDULE, Log.VERBOSE);

        // we can enable inexact timers in our periodic sync
        SyncRequest request = new SyncRequest.Builder().
                syncPeriodic(UPDATE_SCHEDULE, SYNC_FLEXTIME).
                setSyncAdapter(account, authority).
                setExtras(new Bundle()).build();

        ContentResolver.requestSync(request);
    }

    public static void syncImmediately(Account account) {
        Logger.log(LOG_TAG, "Immediate Sync requested", Log.VERBOSE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

//        ContentResolver
//                .setSyncAutomatically(account, McContract.CONTENT_AUTHORITY, true);
//        ContentResolver.setIsSyncable(account, McContract.CONTENT_AUTHORITY, 1);

        if (ContentResolver.isSyncActive(account, McContract.CONTENT_AUTHORITY)) {
            Logger.log(LOG_TAG, "Sync already active for: " + account.name + " cancelling and requesting new", Log.VERBOSE);
            ContentResolver.cancelSync(account, McContract.CONTENT_AUTHORITY);
        }
        ContentResolver.requestSync(account, McContract.CONTENT_AUTHORITY, bundle);
    }

    @Override
    public void onPerformSync(final Account account, Bundle bundle, String authority,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Server activeServer = ServerUtility.getActiveServer();
        Logger.log(LOG_TAG,"Syncing " + activeServer.getServerName(),Log.VERBOSE);
        ApiRequestManager.getInstance().getServicesInfo(activeServer);
        ApiRequestManager.getInstance().getDeviceInfo(activeServer);
        ApiRequestManager.getInstance().getUsers(activeServer);

    }
}
