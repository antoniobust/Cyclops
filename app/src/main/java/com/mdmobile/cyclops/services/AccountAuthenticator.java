package com.mdmobile.cyclops.services;


import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.VolleyError;
import com.mdmobile.cyclops.apiManager.ApiRequestManager;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataModel.api.Token;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.sec.ServerNotFound;
import com.mdmobile.cyclops.ui.logIn.LoginActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    //Authenticator intent keys
    public final static String ACCOUNT_TYPE_KEY = "AccountTypeKey";
    public final static String AUTH_TOKEN_TYPE_KEY = "AuthTokenTypeKey";
    public final static String AUTH_TOKEN_EXPIRATION_KEY = "AuthTokenExpirationKey";
    public final static String REFRESH_AUTH_TOKEN_KEY = "RefreshAuthTokenKey";
    public final static String ADDING_NEW_ACCOUNT_KEY = "AddingNewAccountIntentKey";
    private Context mContext;
    private String LOG_TAG = AccountAuthenticator.class.getSimpleName();

    AccountAuthenticator(Context context) {
        super(context);
        mContext = context.getApplicationContext();
    }


    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        throw new UnsupportedOperationException("Confirm credentials is not a supported operation");

    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options){

        return promptLoginActivity(response, accountType, authTokenType, true);
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                     Account account, Bundle bundle) {
        throw new UnsupportedOperationException("Confirm credentials is not a supported operation");
    }

    @Override
    public Bundle getAuthToken(final AccountAuthenticatorResponse authenticatorResponse, final Account account,
                               final String authTokenType, Bundle bundle) {


        final AccountManager accountManager = AccountManager.get(mContext);
        final Bundle userInfo = UserUtility.getUserInfo(account);
        final Server serverInfo;
        try {
            serverInfo = ServerUtility.getActiveServer();
        } catch (ServerNotFound e) {
            e.printStackTrace();
            return null;
        }

        if (userInfo == null) {
            return promptLoginActivity(authenticatorResponse, account.type, authTokenType, null);
        }

        final String password = accountManager.getPassword(account);

        //If we have all necessary details let's attempt a token request
        if (password != null) {
            Logger.log(LOG_TAG, "Requesting new token...", Log.VERBOSE);

            ApiRequestManager.getInstance()
                    .getToken(serverInfo, account.name, password,
                            new NetworkCallBack() {
                                @Override
                                public void tokenReceived(Bundle userInfo, Token JsonToken) {
                                    //Credentials still valid, token received
                                    //Returning data back to authenticatorResponse
                                    Bundle result = new Bundle();
                                    result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                                    result.putString(AUTH_TOKEN_TYPE_KEY, JsonToken.getToken_type());
                                    result.putString(AccountManager.KEY_AUTHTOKEN, JsonToken.getAccess_token());

                                    authenticatorResponse.onResult(result);
                                }

                                @Override
                                public void errorReceivingToken(VolleyError errorResponse) {
                                    //If we are here with error 400 it only means credentials have changed
                                    //Return the error to authenticatorResponse
                                    if (errorResponse.networkResponse.statusCode == 400) {
                                        authenticatorResponse.onError(errorResponse.networkResponse.statusCode, "AuthenticationException");
                                    }
                                }
                            });
            return null;
        }


        //If we are here then means either we do not have an account signed
        //or credentials are no longer valid -> prompt login procedure again
        return promptLoginActivity(authenticatorResponse, account.type, authTokenType, null);
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                    Account account, String s, Bundle bundle) {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
                              Account account, String[] strings) {
        return null;
    }


    //Utility method to return a bundle with KEY_INTENT key and the intent to show login activity
    private Bundle promptLoginActivity(AccountAuthenticatorResponse response, String accountType,
                                       String authTokenType, @Nullable Boolean addingNewAccount) {
        final Intent intent = new Intent(mContext, LoginActivity.class);

        //Insert the parameter required from LoginActivity to create a new account
        intent.putExtra(ACCOUNT_TYPE_KEY, accountType);
        intent.putExtra(AUTH_TOKEN_TYPE_KEY, authTokenType);
        if (addingNewAccount != null) {
            intent.putExtra(ADDING_NEW_ACCOUNT_KEY, addingNewAccount);
        }
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
}
