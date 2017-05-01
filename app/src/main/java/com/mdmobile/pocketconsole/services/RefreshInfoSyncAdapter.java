package com.mdmobile.pocketconsole.services;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Sync adapter to get info refreshed in the DB.
 */

public class RefreshInfoSyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver mContentProvider;


    public RefreshInfoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContentProvider = context.getContentResolver();
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {

    }

}
