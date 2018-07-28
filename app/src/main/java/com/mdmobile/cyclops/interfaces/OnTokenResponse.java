package com.mdmobile.cyclops.interfaces;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.mdmobile.cyclops.ApplicationLoader;
import com.mdmobile.cyclops.networkRequests.BasicRequest;
import com.mdmobile.cyclops.utils.Logger;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * This class implements AccountManagerCallback in order to process the API new Token request
 */

public class OnTokenResponse implements AccountManagerCallback<Bundle> {

    private static final String LOG_TAG = OnTokenResponse.class.getSimpleName();
    private WeakReference<BasicRequest> request;

    public OnTokenResponse(WeakReference<BasicRequest> request) {
        this.request = request;
    }

    @Override
    public void run(AccountManagerFuture<Bundle> future) {
        Bundle newInfo = new Bundle();
        try {
            if (future.isDone() && !future.isCancelled()) {
                newInfo = future.getResult();
                //If result contains KEY INTENT than we require new credentials
                if (!newInfo.containsKey(AccountManager.KEY_INTENT)) {
                    String accountName = newInfo.getString(AccountManager.KEY_ACCOUNT_NAME);
                    String accountType = newInfo.getString(AccountManager.KEY_ACCOUNT_TYPE);
                    String authTokenType = newInfo.getString(AUTH_TOKEN_TYPE_KEY);
                    String authToken = newInfo.getString(AccountManager.KEY_AUTHTOKEN);

                    AccountManager accountManager = AccountManager.get(applicationContext);
                    Account[] accounts = accountManager.getAccountsByType(accountType);
                    if (accounts[0].name.equals(accountName)) {
                        accountManager.setAuthToken(accounts[0], authTokenType, authToken);
                        Logger.log(LOG_TAG, "Account " + accountName + " new token saved: " + authToken, Log.VERBOSE);
                        if (request != null && request.get() != null) {
                            Logger.log(LOG_TAG, "Resending request ", Log.VERBOSE);
                            Volley.newRequestQueue(ApplicationLoader.applicationContext).add(request.get());
                        }
                    }
                }
            }

        } catch (AuthenticatorException e) {
            //TODO:Launch log in activity
            Logger.log(LOG_TAG, "Authentication exception ...\n" +
                    " No connection was possible with account authenticator\nRedirecting to login ", Log.ERROR);
            e.printStackTrace();
            newInfo.describeContents();
        } catch (IOException | OperationCanceledException e) {
            e.printStackTrace();
        }
    }
}
