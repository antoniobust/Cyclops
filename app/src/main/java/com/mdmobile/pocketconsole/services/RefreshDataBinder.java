package com.mdmobile.pocketconsole.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RefreshDataBinder extends Service {
    // Storage for an instance of the sync adapter
    private static DevicesSyncAdapter sSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    public RefreshDataBinder() {
    }

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new DevicesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
