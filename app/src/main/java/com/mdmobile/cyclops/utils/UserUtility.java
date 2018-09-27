package com.mdmobile.cyclops.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.provider.McContract;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.cyclops.utils.ServerUtility.SERVER_ADDRESS_KEY;

/**
 * Helper methods to deal with users
 */

public class UserUtility {

    public final static String USER_NAME_KEY = "userNameKey", PASSWORD_KEY = "passwordKey";
    private static final String LOG_TAG = UserUtility.class.getSimpleName();

    public static Account checkUserExists(Context context, String userName) {
        Logger.log(LOG_TAG, "Checking any users exists", Log.VERBOSE);
        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));

        for (Account account : accounts) {
            if (account.name.equals(userName)) {
                Logger.log(
                        LOG_TAG,
                        "User Found\nUser Name:" + account.name + " Account type: " + account.type,
                        Log.INFO);
                return account;
            }
        }

        //If we are out the cycle there is no user already registered with same userName
        return null;
    }

    public static Account getUser() {
        return AccountManager.get(applicationContext)
                .getAccountsByType(applicationContext.getString(R.string.account_type))[0];
    }

    public static Boolean checkAnyUserLogged() {
        Logger.log(LOG_TAG, "Checking any users Logged", Log.VERBOSE);

        Account[] accounts =
                AccountManager.get(applicationContext).getAccountsByType(applicationContext.getString(R.string.account_type));
        if (accounts.length > 0) {
            Logger.log(LOG_TAG, "found:" + accounts.length + " logged", Log.VERBOSE);
            return true;
        }
        return false;
    }

    public static Bundle getUserInfo(Account account) {

        AccountManager accountManager = AccountManager.get(applicationContext);
        Bundle userInfo = new Bundle();
        //TODO:support multiple account
        userInfo.putString(AUTH_TOKEN_TYPE_KEY,
                accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY));
        userInfo.putString(AUTH_TOKEN_EXPIRATION_KEY,
                accountManager.getUserData(account, AUTH_TOKEN_EXPIRATION_KEY));

        return userInfo;
    }

    public static void updateUserData(@NonNull Bundle userInfo) {
        //TODO:support multiple account

        AccountManager manager = AccountManager.get(applicationContext);

        Account account = getUser();

        //Update accountsUpdateListener with new user data (token type would be teh same, the others may have changed)
        if (userInfo.containsKey(McContract.ServerInfo.CLIENT_ID)) {
            manager.setUserData(account, McContract.ServerInfo.CLIENT_ID, userInfo.getString(McContract.ServerInfo.CLIENT_ID));
        }
        if (userInfo.containsKey(McContract.ServerInfo.CLIENT_SECRET)) {
            manager.setUserData(account, McContract.ServerInfo.CLIENT_SECRET, userInfo.getString(McContract.ServerInfo.CLIENT_SECRET));
        }
        if (userInfo.containsKey(SERVER_ADDRESS_KEY)) {
            manager.setUserData(account, SERVER_ADDRESS_KEY, userInfo.getString(SERVER_ADDRESS_KEY));
        }
        if (userInfo.containsKey(AUTH_TOKEN_EXPIRATION_KEY)) {
            manager.setUserData(account, AUTH_TOKEN_EXPIRATION_KEY, userInfo.getString(AUTH_TOKEN_EXPIRATION_KEY));
        }
        if (userInfo.containsKey(AUTH_TOKEN_TYPE_KEY)) {
            manager.setUserData(account, AUTH_TOKEN_TYPE_KEY, userInfo.getString(AUTH_TOKEN_TYPE_KEY));
        }
    }

    public static void clearUserPreferences(String userName){
        String preferenceKey = applicationContext.getString(R.string.user_shared_preference,userName);
        SharedPreferences.Editor editor =
                applicationContext.getSharedPreferences(preferenceKey,Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public static Drawable getUserLogo() {
        SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.user_shared_preference),Context.MODE_PRIVATE);
        int drawableId = preferences.getInt(applicationContext.getString(R.string.user_logo_preference),R.drawable.ic_android);
        return applicationContext.getResources().getDrawable(drawableId);
    }
}
