package com.mdmobile.pocketconsole.networkRequests;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.github.mikephil.charting.utils.Utils;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.interfaces.OnTokenAcquired;
import com.mdmobile.pocketconsole.utils.Logger;
import com.mdmobile.pocketconsole.utils.UserUtility;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * Extend this class in Custom volley request.
 * This insert MobiControl authentication token in the headers
 * And provides standard procedure for a refused request
 */

abstract class BasicRequest<T> extends Request<T> {

    private final String LOG_TAG = BasicRequest.class.getSimpleName();
    private int lastError, retryCount;


    public BasicRequest(int method, String url, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
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

        //TODO:if we attempted already and error message is the same log out the user
        if (retryCount > 1 && lastError == response.statusCode) {
            //CHECK ALL ERRORS CODE
            parseErrorCode(response.statusCode);
            return super.parseNetworkError(volleyError);
        } else {
            //RERUN REQUEST
            lastError = response.statusCode;
            retryCount++;
            getUrl();
        }

        return super.parseNetworkError(volleyError);
    }

    private void parseErrorCode(int errorCode) {

        if (errorCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
        } else if (errorCode == HttpsURLConnection.HTTP_UNAUTHORIZED ||
                errorCode == HttpsURLConnection.HTTP_FORBIDDEN) {
            //Allow max 1 attempt to get the token -> this will avoid recursive loop of requests
            Logger.log(LOG_TAG, "Attempt requesting a new Token, retry sequence: " + retryCount, Log.VERBOSE);


            AccountManager manager = AccountManager.get(applicationContext);
            Account[] accounts = manager.getAccountsByType(applicationContext.getString(R.string.account_type));

            if (accounts.length == 1) {
                String tokenType = manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY);
                String token = manager.peekAuthToken(accounts[0], tokenType);
                manager.invalidateAuthToken(applicationContext.getString(R.string.account_type), token);

                manager.getAuthToken(accounts[0],
                        manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                        null, false, new OnTokenAcquired(), null);
            }
        } else if (errorCode == HttpsURLConnection.HTTP_NOT_FOUND) {

        } else if (errorCode == 422) {
            //TODO: show error message in toast
        } else if (errorCode == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
            //TODO:sho internal server error
        }
    }
}

