package com.mdmobile.cyclops.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.services.AccountAuthenticator;

import androidx.annotation.NonNull;

import static com.mdmobile.cyclops.CyclopsApplication.Companion;
import static com.mdmobile.cyclops.util.ServerUtility.SERVER_ADDRESS_KEY;

/**
 * Helper methods to deal with users
 */

//TODO: migrate account utility functions to @AccountManagerExtensions.kt file

public class UserUtility {

    public final static String USER_NAME_KEY = "userNameKey", PASSWORD_KEY = "passwordKey";
    private static final String LOG_TAG = UserUtility.class.getSimpleName();

    public static Account getMcAccount() {
        return AccountManager.get(Companion.getApplicationContext())
                .getAccountsByType(Companion.getApplicationContext().getString(R.string.MC_account_type))[0];
    }

    public static Boolean checkAnyUserLogged() {
        Logger.log(LOG_TAG, "Checking any users Logged", Log.VERBOSE);

        Account[] accounts =
                AccountManager.get(Companion.getApplicationContext()).getAccountsByType(Companion.getApplicationContext().getString(R.string.MC_account_type));
        if (accounts.length > 0) {
            Logger.log(LOG_TAG, "found:" + accounts.length + " logged", Log.VERBOSE);
            return true;
        }
        return false;
    }

    public static Bundle getUserInfo(Account account) {

        AccountManager accountManager = AccountManager.get(Companion.getApplicationContext());
        Bundle userInfo = new Bundle();
        //TODO:support multiple account
        userInfo.putString(AccountAuthenticator.AUTH_TOKEN_TYPE_KEY,
                accountManager.getUserData(account, AccountAuthenticator.AUTH_TOKEN_TYPE_KEY));
        userInfo.putString(AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY,
                accountManager.getUserData(account, AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY));

        return userInfo;
    }

    public static void updateUserData(@NonNull Bundle userInfo) {
        //TODO:support multiple account

        AccountManager manager = AccountManager.get(Companion.getApplicationContext());

        Account account = getMcAccount();

        //Update accountsUpdateListener with new user data (_token type would be teh same, the others may have changed)
        if (userInfo.containsKey(McContract.ServerInfo.CLIENT_ID)) {
            manager.setUserData(account, McContract.ServerInfo.CLIENT_ID, userInfo.getString(McContract.ServerInfo.CLIENT_ID));
        }
        if (userInfo.containsKey(McContract.ServerInfo.CLIENT_SECRET)) {
            manager.setUserData(account, McContract.ServerInfo.CLIENT_SECRET, userInfo.getString(McContract.ServerInfo.CLIENT_SECRET));
        }
        if (userInfo.containsKey(SERVER_ADDRESS_KEY)) {
            manager.setUserData(account, SERVER_ADDRESS_KEY, userInfo.getString(SERVER_ADDRESS_KEY));
        }
        if (userInfo.containsKey(AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY)) {
            manager.setUserData(account, AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY, userInfo.getString(AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY));
        }
        if (userInfo.containsKey(AccountAuthenticator.AUTH_TOKEN_TYPE_KEY)) {
            manager.setUserData(account, AccountAuthenticator.AUTH_TOKEN_TYPE_KEY, userInfo.getString(AccountAuthenticator.AUTH_TOKEN_TYPE_KEY));
        }
    }

    public static void clearUserPreferences(String userName) {
        String preferenceKey = Companion.getApplicationContext().getString(R.string.user_shared_preference, userName);
        SharedPreferences.Editor editor =
                Companion.getApplicationContext().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public static Drawable getUserLogo() {
        SharedPreferences preferences = Companion.getApplicationContext().getSharedPreferences(Companion.getApplicationContext().getString(R.string.user_shared_preference), Context.MODE_PRIVATE);
        int drawableId = preferences.getInt(Companion.getApplicationContext().getString(R.string.user_logo_preference), R.drawable.ic_android);
        return Companion.getApplicationContext().getResources().getDrawable(drawableId);
    }
}
