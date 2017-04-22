package com.mdmobile.pocketconsole.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AuthenticationService extends Service {

    private AccountAuthenticator authenticator;

    //When service gets created create an instance to the account authenticator class
    @Override
    public void onCreate() {
        authenticator = new AccountAuthenticator(this);
    }

    //Bind android framework to authenticator service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
