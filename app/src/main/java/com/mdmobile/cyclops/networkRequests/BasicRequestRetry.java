package com.mdmobile.cyclops.networkRequests;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.interfaces.OnTokenResponse;
import com.mdmobile.cyclops.utils.Logger;

import java.lang.ref.WeakReference;

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;

/**
 * Retry policy for API requests
 */

public class BasicRequestRetry extends DefaultRetryPolicy {
    private final String LOG_TAG = BasicRequestRetry.class.getSimpleName();
    private BasicRequest originalReq;

    public BasicRequestRetry(BasicRequest originalReq) {
        super(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        this.originalReq = originalReq;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        try {
            super.retry(error);
        } catch (VolleyError e) {
            if (!originalReq.isCanceled() && (error.networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED ||
                    error.networkResponse.statusCode == HttpsURLConnection.HTTP_FORBIDDEN)) {
                Logger.log(LOG_TAG, "Auth failed cancelling all pending requests...", Log.VERBOSE);
                ApiRequestManager.getInstance().cancelAllPendingRequest();

                Logger.log(LOG_TAG, "Attempting new token request\n", Log.VERBOSE);
                AccountManager manager = AccountManager.get(applicationContext);
                Account[] accounts = manager.getAccountsByType(applicationContext.getString(R.string.account_type));

                if (accounts.length == 1) {
                    String tokenType = manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY);
                    String token = manager.peekAuthToken(accounts[0], tokenType);
                    manager.invalidateAuthToken(applicationContext.getString(R.string.account_type), token);
                    manager.getAuthToken(accounts[0], manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY),
                            null, true,
                            new OnTokenResponse(new WeakReference<>(originalReq)), null);
                }
            } else {
                throw new VolleyError();
            }

        }
    }
}
