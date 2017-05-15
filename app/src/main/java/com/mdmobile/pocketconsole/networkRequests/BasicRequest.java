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
import com.mdmobile.pocketconsole.services.AccountAuthenticator;
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

    private static int attempts = 0;
    private final Context mContext;
    private final String LOG_TAG = BasicRequest.class.getSimpleName();
    private AccountManagerCallback<Bundle> managerCallback = new AccountManagerCallback<Bundle>() {
        @Override
        public void run(AccountManagerFuture accountManagerFuture) {
            if (accountManagerFuture.isDone()) {
                Bundle accountFutureResult;
                try {
                    accountFutureResult = (Bundle) accountManagerFuture.getResult();
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AuthenticatorException e) {
                    //TODO: support multi user
                }
            }
        }
    };

    public BasicRequest(int method, String url, Response.ErrorListener errorListener, Context context) {
        super(method, url, errorListener);
        mContext = context;
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
        try {
            String errorResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.log(LOG_TAG, errorResponse, Log.ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //If request returns 401 there could be a problem with the authentication token which
        //may be expired
        if (response.statusCode == 400) {
            //Allow max 1 attempt to get the token -> this will avoid recursive loop of requests
            if (attempts == 0) {
                attempts++;
                Logger.log(LOG_TAG, "Attempt requesting a new Token", Log.VERBOSE);

                //Get current user data we have stored
                AccountManager manager = AccountManager.get(mContext);

                Account[] accounts = manager.getAccountsByType(mContext.getString(R.string.account_type));
                if (accounts.length == 1) {
                    manager.getAuthToken(accounts[0],
                            manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                            null, true, managerCallback, null);
                }

            }
        }
        return super.parseNetworkError(volleyError);
    }

}

