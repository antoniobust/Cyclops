package com.mdmobile.pocketconsole.services;


import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mdmobile.pocketconsole.ui.LoginActivity;

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
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
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account,
                               String authTokenType, Bundle bundle) throws NetworkErrorException {

        //Get the account manager to access the account details
        AccountManager accountManager = AccountManager.get(mContext);
        String authToken = accountManager.peekAuthToken(account, authTokenType);

        //If auth token is null then try to log in the user with the stored credentials
        if (authToken.equals("")) {
            final String password = accountManager.getPassword(account);
            if (password != null) {
                //TODO: attempt login returning the authToken in case of success
            }
        }

        //If we got an authToken now return the account and login info in a bundle
        if(!authToken.equals("")){
            Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);

            return result;
        }

        //If we are here then means either we do not have an account signed
        //or credentials are no longer valid -> prompt login procedure again
        return promptLoginActivity(accountAuthenticatorResponse, account.type, authTokenType, null);
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
        intent.putExtra(LoginActivity.ACCOUNT_TYPE_KEY, accountType);
        intent.putExtra(LoginActivity.AUTH_TOKEN_TYPE_KEY, authTokenType);
        if (addingNewAccount != null) {
            intent.putExtra(LoginActivity.ADDING_NEW_ACCOUNT_KEY, addingNewAccount);
        }
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        //Create a bundle with key KEY_INTENT as per documentation
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
}
