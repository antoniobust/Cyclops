package com.mdmobile.pocketconsole.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RefreshInfo extends Service {
    public RefreshInfo() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
