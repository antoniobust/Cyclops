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

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * Extend this class in Custom volley request.
 * This insert MobiControl authentication token in the headers
 * And provides standard procedure for a refused request
 */

abstract class BasicRequest<T> extends Request<T> {

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
                        Intent intent = newInfo.getParcelable(AccountManager.KEY_INTENT);
                        if (intent != null) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(intent);
                        }
                    } else {
//                        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
//                        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
//                        result.putString(AUTH_TOKEN_TYPE_KEY, JsonToken.getToken_type());
//                        result.putString(AccountManager.KEY_AUTHTOKEN, JsonToken.getAccess_token());
                        String accountName = newInfo.getString(AccountManager.KEY_ACCOUNT_NAME);
                        String accountType = newInfo.getString(AccountManager.KEY_ACCOUNT_TYPE);
                        String authTokenType = newInfo.getString(AUTH_TOKEN_TYPE_KEY);
                        String authToken = newInfo.getString(AccountManager.KEY_AUTHTOKEN);

                        AccountManager accountManager = AccountManager.get(mContext);
                        Account[] accounts = accountManager.getAccountsByType(accountType);
                        if (accounts[0].name.equals(accountName)) {
                            accountManager.setAuthToken(accounts[0], authTokenType, authToken);
                            Logger.log(LOG_TAG, "Account " + accountName + " new token saved: " + authToken
                                    + "\n Resending request...", Log.VERBOSE);
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

        if (token == null) {
            //TODO: stop request and launch account manager for a new token
            //Instead of sending this request out which is gonna fail because of the token cancel this request
            //and start the new token request procedure
            return super.getHeaders();
        }

        headers.put("Authorization", tokenType.concat(" ").concat(token));
        headers.put("Accept", "application/json");

        return headers;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {

        //TODO:support Multiple account

        NetworkResponse response = volleyError.networkResponse;
        if (response == null) {
            return super.parseNetworkError(volleyError);
        }

        //Log error message
        try {
            String errorResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.log(LOG_TAG, errorResponse, Log.ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //If error is in 400 range give it another try as it could be the token expired
        if (response.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED ||
                response.statusCode == HttpsURLConnection.HTTP_FORBIDDEN) {
            //Allow max 1 attempt to get the token -> this will avoid recursive loop of requests
            Logger.log(LOG_TAG, "Attempt requesting a new Token", Log.VERBOSE);

            //Get current user data we have stored
            AccountManager manager = AccountManager.get(mContext);
            Account[] accounts = manager.getAccountsByType(mContext.getString(R.string.account_type));

            if (accounts.length == 1) {
                String tokenType = manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY);
                String token = manager.peekAuthToken(accounts[0], tokenType);
                manager.invalidateAuthToken(mContext.getString(R.string.account_type), token);

                manager.getAuthToken(accounts[0],
                        manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                        null, false, managerCallback, null);
            }
        }
        return super.parseNetworkError(volleyError);
    }

}

