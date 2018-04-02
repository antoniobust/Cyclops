package com.mdmobile.cyclops.networkRequests;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.interfaces.OnTokenAcquired;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * Extend this class in Custom volley request.
 * This insert MobiControl authentication token in the headers
 * And provides standard procedure for a refused request
 */

abstract public class BasicRequest<T> extends Request<T> {

    private final String LOG_TAG = BasicRequest.class.getSimpleName();

    public BasicRequest(int method, String url, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        setRetryPolicy(new BasicRequestRetry(this));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        AccountManager accountManager = AccountManager.get(applicationContext);

        //TODO: support multiple account
        Account account = UserUtility.getUser();
        String tokenType = UserUtility.getUserInfo(UserUtility.getUser()).getString(AUTH_TOKEN_TYPE_KEY);

        String token = accountManager.peekAuthToken(account, tokenType);

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
            Logger.log(LOG_TAG, "Network request Error: error response: NULL\n", Log.ERROR);
            return super.parseNetworkError(volleyError);
        }

        try {
            String errorResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.log(LOG_TAG, errorResponse, Log.ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //CHECK ALL ERRORS CODE
        parseErrorCode(response.statusCode);
        return super.parseNetworkError(volleyError);
    }

    private void parseErrorCode(int errorCode) {

        if (errorCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
            Logger.log(LOG_TAG, "Error " + errorCode + " HTTP BAD REQUEST", Log.ERROR);
        } else if (errorCode == HttpsURLConnection.HTTP_UNAUTHORIZED ||
                errorCode == HttpsURLConnection.HTTP_FORBIDDEN) {
            Logger.log(LOG_TAG, "Attempt to request new token\n", Log.VERBOSE);

            AccountManager manager = AccountManager.get(applicationContext);
            Account[] accounts = manager.getAccountsByType(applicationContext.getString(R.string.account_type));

            if (accounts.length == 1) {
                String tokenType = manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY);
                String token = manager.peekAuthToken(accounts[0], tokenType);
                manager.invalidateAuthToken(applicationContext.getString(R.string.account_type), token);

                manager.getAuthToken(accounts[0],
                        manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                        null, false, new OnTokenAcquired(new WeakReference<BasicRequest>(this)), null);
            }
        } else if (errorCode == HttpsURLConnection.HTTP_NOT_FOUND) {
            Bundle server = ServerUtility.getServer();
            String serverName;
            if (server != null) {
                serverName = server.getString(McContract.ServerInfo.NAME);
            } else {
                serverName = getUrl();
            }
            ServerUtility.notifyServerStatus(serverName, ServerUtility.SERVER_OFFLINE);
        } else if (errorCode == 422) {
            //TODO: show error message in toast
        } else if (errorCode == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
        }
    }
}

