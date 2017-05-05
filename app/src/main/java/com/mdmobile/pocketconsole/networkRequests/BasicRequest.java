package com.mdmobile.pocketconsole.networkRequests;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.mdmobile.pocketconsole.R;

import java.util.HashMap;
import java.util.Map;

import static com.mdmobile.pocketconsole.ui.LoginActivity.AUTH_TOKEN_TYPE_KEY;

public abstract class BasicRequest extends Request {

    private final Context mContext;

    public BasicRequest(int method, String url, Response.ErrorListener listener, Context context) {
        super(method, url, listener);
        mContext = context;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> headers = new HashMap<>();
        AccountManager accountManager = AccountManager.get(mContext);
        Account[] accountAvailable = accountManager.getAccountsByType(mContext.getString(R.string.account_type));

        String tokenType = accountManager.getUserData(accountAvailable[0], AUTH_TOKEN_TYPE_KEY);
        //Assuming we only have 1 account
        //TODO: support multiple account
        String token = accountManager.peekAuthToken(accountAvailable[0],tokenType);

        headers.put("Authorization", tokenType.concat(" ").concat(token));
        return headers;
    }

}

