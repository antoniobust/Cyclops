package com.mdmobile.pocketconsole.services;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiHandler.ApiRequestManager;
import com.mdmobile.pocketconsole.utils.Logger;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Sync adapter to get info refreshed in the DB.
 */

public class DevicesSyncAdapter extends AbstractThreadedSyncAdapter {
    // Update schedule is 3h by default
    private static int UPDATE_SCHEDULE = 60 * 180;
    private static int SYNC_FLEXTIME = UPDATE_SCHEDULE / 3;
    private ContentResolver mContentProvider;
    private Context mContext;


    public DevicesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContext = context.getApplicationContext();
        mContentProvider = context.getContentResolver();
    }

    public static void initializeSync(Account account, Context mContext) {
        onNewAccountCreated(mContext, account);
    }

    //When adding an account call this method to schedule device information updates
    private static void onNewAccountCreated(Context c, Account account) {
        //Configure periodic sync
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
    public void onPerformSync(Account account, Bundle bundle, String authority,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {

        ApiRequestManager.getInstance(mContext).getDevices(account);
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
    }
}
