package com.mdmobile.pocketconsole.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RefreshDataBinder extends Service {
    public RefreshDataBinder() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new DevicesSyncAdapter(getApplicationContext(), true).getSyncAdapterBinder();
    }
}
