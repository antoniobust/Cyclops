package com.mdmobile.pocketconsole.networkRequests;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.utils.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * Extend this class in Custom volley request.
 * This insert MobiControl authentication token in the headers
 * And provides standard procedure for a refused request
 */

public abstract class BasicRequest<T> extends Request<T> {

    private final Context mContext;
    private final String LOG_TAG = BasicRequest.class.getSimpleName();
    private AccountManagerCallback<Bundle> managerCallback = new AccountManagerCallback<Bundle>() {
        @Override
        public void run(AccountManagerFuture accountManagerFuture) {
            try {
                if (accountManagerFuture.isDone() && !accountManagerFuture.isCancelled()) {
                    Bundle newInfo = (Bundle) accountManagerFuture.getResult();

                    //If result contains KEY INTENT from account manager it means it failed to get
                    //a new token as we cannot log in -> prompt login activity
                    if (newInfo.containsKey(AccountManager.KEY_INTENT)) {
                        //TODO:after getting new credentials if we get token we need to save new password
                        //which is not getting saved -> stays null after clearPassword
                        Intent intent = (Intent) newInfo.get(AccountManager.KEY_INTENT);
                        if (intent != null) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(intent);
                        }
                    }
                }

            } catch (AuthenticatorException e) {
                if (e.getMessage().equals("AuthenticationException")) {
                    //Launch login activity
                    //TODO:Launch log in activity
                    Logger.log(LOG_TAG, "Authentication exception ...\n No connection was possible with account authenticator\nRedirecting to login ", Log.ERROR);
                    e.printStackTrace();

                    //Clearing user credential and let the user input new ones
                    AccountManager accountManager = AccountManager.get(mContext);
                    Account[] accounts = accountManager.getAccountsByType(mContext.getString(R.string.account_type));
                    accountManager.clearPassword(accounts[0]);
                    accountManager.getAuthToken(accounts[0],
                            accountManager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY)
                            , null, null, this, null);
                }
            } catch (IOException | OperationCanceledException e) {
                e.printStackTrace();
            }
        }
    };

    public BasicRequest(int method, String url, Response.ErrorListener errorListener, Context context) {
        super(method, url, errorListener);
        mContext = context.getApplicationContext();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        AccountManager accountManager = AccountManager.get(mContext);
        Account[] accountAvailable = accountManager.getAccountsByType(mContext.getString(R.string.account_type));

        String tokenType = accountManager.getUserData(accountAvailable[0], AUTH_TOKEN_TYPE_KEY);
        //Assuming we only have 1 account
        //TODO: support multiple account
        String token = accountManager.peekAuthToken(accountAvailable[0], tokenType);

        headers.put("Authorization", tokenType.concat(" ").concat(token));

        headers.put("Accept", "application/json");

        return headers;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {

        //TODO:support Multiple account

        NetworkResponse response = volleyError.networkResponse;
        if (response.data == null) {
            return super.parseNetworkError(volleyError);
        }

        try {
            String errorResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.log(LOG_TAG, errorResponse, Log.ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //If request returns 401 or 400 there could be a problem with the authentication token which
        //may be expired
        if (response.statusCode == 400 || response.statusCode == 401) {
            //Allow max 1 attempt to get the token -> this will avoid recursive loop of requests
            Logger.log(LOG_TAG, "Attempt requesting a new Token", Log.VERBOSE);

            //Get current user data we have stored
            AccountManager manager = AccountManager.get(mContext);

            Account[] accounts = manager.getAccountsByType(mContext.getString(R.string.account_type));
            if (accounts.length == 1) {
                manager.getAuthToken(accounts[0],
                        manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                        null, false, managerCallback, null);

            }
        }
        return super.parseNetworkError(volleyError);
    }

}

