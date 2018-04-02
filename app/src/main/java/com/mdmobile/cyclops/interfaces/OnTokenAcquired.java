package com.mdmobile.cyclops.interfaces;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.mdmobile.cyclops.ApplicationLoader;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.networkRequests.BasicRequest;
import com.mdmobile.cyclops.utils.Logger;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * This class implements AccountManagerCallback in order to process the API new Token request
 */

public class OnTokenAcquired implements AccountManagerCallback<Bundle> {

    private static final String LOG_TAG = OnTokenAcquired.class.getSimpleName();
    WeakReference<BasicRequest> request;

    public OnTokenAcquired(WeakReference<BasicRequest> request) {
        this.request = request;
    }

    @Override
    public void run(AccountManagerFuture<Bundle> future) {
        try {
            if (future.isDone() && !future.isCancelled()) {
                Bundle newInfo = future.getResult();

                //If result contains KEY INTENT from account manager it means it failed to get
                //a new token as we cannot log in -> prompt login activity
                if (newInfo.containsKey(AccountManager.KEY_INTENT)) {
                    //TODO:after getting new credentials if we get token we need to save new password
                    //which is not getting saved -> stays null after clearPassword
                    Intent intent = newInfo.getParcelable(AccountManager.KEY_INTENT);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        applicationContext.startActivity(intent);
                    }
                } else {
//                        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.Name);
//                        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
//                        result.putString(AUTH_TOKEN_TYPE_KEY, JsonToken.getToken_type());
//                        result.putString(AccountManager.KEY_AUTHTOKEN, JsonToken.getAccess_token());
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
            if (e.getMessage().equals("AuthenticationException")) {
                //Launch login activity
                //TODO:Launch log in activity
                Logger.log(LOG_TAG, "Authentication exception ...\n" +
                        " No connection was possible with account authenticator\nRedirecting to login ", Log.ERROR);
                e.printStackTrace();

                //Clearing user credential and let the user input new ones
                AccountManager accountManager = AccountManager.get(applicationContext);
                Account[] accounts = accountManager.getAccountsByType(applicationContext.getString(R.string.account_type));
                accountManager.clearPassword(accounts[0]);
                accountManager.getAuthToken(accounts[0],
                        accountManager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY)
                        , null, true, this, null);
            }
        } catch (IOException | OperationCanceledException e) {
            e.printStackTrace();
        }
    }
}
