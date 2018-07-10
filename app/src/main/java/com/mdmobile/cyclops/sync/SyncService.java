package com.mdmobile.cyclops.sync;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.util.ArrayList;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Sync adapter to get info refreshed in the DB.
 */

public class SyncService extends AbstractThreadedSyncAdapter {
    public static String SYNC_DEVICES = "syncDevices";
    public static String SYNC_SERVER = "syncServer";
    public static String SYNC_USERS = "syncUsers";
    private static int UPDATE_SCHEDULE = 60 * 60;
    private static int SYNC_FLEXTIME = UPDATE_SCHEDULE / 3;


    SyncService(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public static void initializeSync(Account account) {
        onNewAccountCreated(account);
    }

    //When adding an account call this method to schedule device information updates
    private static void onNewAccountCreated(Account account) {

        //Without calling setSyncAutomatically, periodic sync will not be enabled.
        ContentResolver.setSyncAutomatically(account, McContract.CONTENT_AUTHORITY, true);

        SyncService.configurePeriodicSync(account);

        //As the account was just created we should launch the first sync
        Bundle b = new Bundle();
        b.putBoolean(SYNC_USERS, true);
        b.putBoolean(SYNC_DEVICES, true);
        b.putBoolean(SYNC_SERVER, true);
        syncImmediately(account, b);
    }

    //Helper method to set up a period sync interval
    private static void configurePeriodicSync(Account account) {
        String authority = McContract.CONTENT_AUTHORITY;
        Logger.log(LOG_TAG, "Configuring periodic sync: " + UPDATE_SCHEDULE, Log.VERBOSE);

        // we can enable inexact timers in our periodic sync
        SyncRequest request = new SyncRequest.Builder().
                syncPeriodic(UPDATE_SCHEDULE, SYNC_FLEXTIME).
                setSyncAdapter(account, authority).
                setExtras(new Bundle()).build();

        ContentResolver.requestSync(request);
    }

    public static void syncImmediately(Account account, Bundle bundle) {
        Logger.log(LOG_TAG, "Immediate Sync requested", Log.VERBOSE);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

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
        ArrayList<String> actions = new ArrayList<>();
        actions.add(SYNC_SERVER);
        if (bundle.containsKey(SYNC_USERS) && bundle.getBoolean(SYNC_USERS)) {
            actions.add(SYNC_USERS);
        }
        if (bundle.containsKey(SYNC_DEVICES) && bundle.getBoolean(SYNC_DEVICES)) {
            actions.add(SYNC_DEVICES);
        }
        if (actions.size() == 0) {
            actions.add(SYNC_USERS);
            actions.add(SYNC_DEVICES);
            actions.add(SYNC_SERVER);
        }

        Logger.log(LOG_TAG, "Syncing " + activeServer.getServerName() +
                "\n Action to perform:" + actions.toString(), Log.VERBOSE);

        Intent intent = new Intent(MainActivity.UPDATE_LOADING_BAR_ACTION);
        intent.putExtra(MainActivity.UPDATE_LOADING_BAR_ACTION_COUNT, actions.size());
        intent.setPackage(getContext().getPackageName());

        for (String action : actions) {
            if (action.equals(SYNC_DEVICES)) {
                this.getContext().sendBroadcast(intent);
                ApiRequestManager.getInstance().getDeviceInfo(activeServer);
            }
            if (action.equals(SYNC_SERVER)) {
                this.getContext().sendBroadcast(intent);
                ApiRequestManager.getInstance().getServicesInfo(activeServer);
            }
            if (action.equals(SYNC_USERS)) {
                this.getContext().sendBroadcast(intent);
                ApiRequestManager.getInstance().getUsers(activeServer);
            }
        }
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
        ApiRequestManager.getInstance().cancelRequest();
    }
}
