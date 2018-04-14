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
import com.mdmobile.cyclops.dataModels.api.Token;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.logIn.LoginActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

import static com.mdmobile.cyclops.utils.ServerUtility.SERVER_ADDRESS_KEY;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    //Authenticator intent keys
    public final static String ACCOUNT_TYPE_KEY = "AccountTypeKey";
    public final static String AUTH_TOKEN_TYPE_KEY = "AuthTokenTypeKey";
    public final static String AUTH_TOKEN_EXPIRATION_KEY = "AuthTokenExpirationKey";
    public final static String REFRESH_AUTH_TOKEN_KEY = "RefreshAuthTokenKey";
    public final static String ADDING_NEW_ACCOUNT_KEY = "AddingNewAccountIntentKey";
    private Context mContext;
    private String LOG_TAG = AccountAuthenticator.class.getSimpleName();

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context.getApplicationContext();
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options) throws NetworkErrorException {

        return promptLoginActivity(response, accountType, authTokenType, true);
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                     Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(final AccountAuthenticatorResponse authenticatorResponse, final Account account,
                               final String authTokenType, Bundle bundle) throws NetworkErrorException {


        final AccountManager accountManager = AccountManager.get(mContext);
        final Bundle userInfo = UserUtility.getUserInfo(account);
        final Bundle serverInfo = ServerUtility.getActiveServer();

        final String password = accountManager.getPassword(account);
        final String clientID = serverInfo.getString(McContract.ServerInfo.CLIENT_ID);
        final String apiSecret = serverInfo.getString(McContract.ServerInfo.CLIENT_SECRET);
        final String serverUrl = serverInfo.getString(SERVER_ADDRESS_KEY);


        //If we have all necessary details let's attempt a token request
        if (password != null && clientID != null && apiSecret != null && serverUrl != null) {
            Logger.log(LOG_TAG, "Requesting new token...", Log.VERBOSE);
            ApiRequestManager.getInstance()
                    .getToken(serverUrl, clientID, apiSecret, account.name, password,
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
                                    Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
                              Account account, String[] strings) throws NetworkErrorException {
        return null;
    }


    //Utility method to return a bundle with KEY_INTENT key and the intent to show login activity
    private Bundle promptLoginActivity(AccountAuthenticatorResponse response, String accountType,
                                       String authTokenType, @Nullable Boolean addingNewAccount) {
        //Create a new intent to launch the log in activity
        final Intent intent = new Intent(mContext, LoginActivity.class);

        //Insert the parameter required from LoginActivity to create a new account
        intent.putExtra(ACCOUNT_TYPE_KEY, accountType);
        intent.putExtra(AUTH_TOKEN_TYPE_KEY, authTokenType);
        if (addingNewAccount != null) {
            intent.putExtra(ADDING_NEW_ACCOUNT_KEY, addingNewAccount);
        }
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        //Create a bundle with key KEY_INTENT as per documentation
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
}
