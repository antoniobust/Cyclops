package com.mdmobile.cyclops.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclops.api.ApiRequestManager;
import com.mdmobile.cyclops.networkRequests.BasicRequest;
import com.mdmobile.cyclops.util.Logger;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.mdmobile.cyclops.CyclopsApplication.Companion;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * This class implements AccountManagerCallback in order to process the API new Token request
 */

public class OnTokenResponse implements AccountManagerCallback<Bundle> {

    private static final String LOG_TAG = OnTokenResponse.class.getSimpleName();
    private WeakReference<? extends BasicRequest> request;

    public OnTokenResponse(WeakReference<? extends BasicRequest> request) {
        this.request = request;
    }

    @Override
    public void run(AccountManagerFuture<Bundle> future) {
        try {
            if (future.isDone() && !future.isCancelled()) {
                Bundle newInfo = future.getResult();
                //If result contains KEY INTENT then we require new credential otherwise resend req
                if (newInfo.containsKey(AccountManager.KEY_INTENT)) {
                    Intent intent = new Intent(ApiRequestManager.API_AUTH_ERROR);
                    intent.setPackage(Companion.getApplicationContext().getPackageName());
                    intent.putExtra("srvName", request.get().getServerUrl());
                    Companion.getApplicationContext().sendBroadcast(intent);
                } else{
                    String accountName = newInfo.getString(AccountManager.KEY_ACCOUNT_NAME);
                    String accountType = newInfo.getString(AccountManager.KEY_ACCOUNT_TYPE);
                    String authTokenType = newInfo.getString(AUTH_TOKEN_TYPE_KEY);
                    String authToken = newInfo.getString(AccountManager.KEY_AUTHTOKEN);
                    AccountManager accountManager = AccountManager.get(Companion.getApplicationContext());
                    Account[] accounts = accountManager.getAccountsByType(accountType);
                    if (accounts[0].name.equals(accountName)) {
                        accountManager.setAuthToken(accounts[0], authTokenType, authToken);
                        Logger.log(LOG_TAG, "Account " + accountName + " new token saved: " + authToken, Log.VERBOSE);
                        if (request != null && request.get() != null) {
                            Logger.log(LOG_TAG, "Resending request ", Log.VERBOSE);
                            ApiRequestManager.getInstance().queueUp(
                                    ApiRequestManager.getInstance().rebuildRequest(request.get()));
                        }
                    }
                }
            }
        } catch (AuthenticatorException e) {
            Logger.log(LOG_TAG, "Authentication exception ...\n" +
                    " No connection was possible with account authenticator\nRedirecting to login ", Log.ERROR);
            e.printStackTrace();
        } catch (IOException | OperationCanceledException e) {
            e.printStackTrace();
        }
    }
}
