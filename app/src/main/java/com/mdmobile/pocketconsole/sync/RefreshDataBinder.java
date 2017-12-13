package com.mdmobile.pocketconsole.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mdmobile.pocketconsole.sync.DevicesSyncAdapter;

public class RefreshDataBinder extends Service {
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();
    // Storage for an instance of the sync adapter
    private static DevicesSyncAdapter sSyncAdapter = null;

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
