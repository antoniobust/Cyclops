package com.mdmobile.pocketconsole.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mdmobile.pocketconsole.ApplicationLoader;
import com.mdmobile.pocketconsole.R;

import java.util.HashMap;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.API_SECRET_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.CLIENT_ID_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_ADDRESS_KEY;

/**
 * Helper methods to deal with users
 */

public class UserUtility {

    private static final String LOG_TAG = UserUtility.class.getSimpleName();

    public static Account checkUserExists(Context context, String userName) {
        Logger.log(LOG_TAG, "Checking any users exists", Log.VERBOSE);
        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));

        for (Account account : accounts) {
            if (account.name.equals(userName)) {
                Logger.log(
                        LOG_TAG,
                        "User Found\nUser name:" + account.name + " Account type: " + account.type,
                        Log.INFO);
                return account;
            }
        }

        //If we are out the cycle there is no user already registered with same userName
        return null;
    }

    public static Account getUser(Context context){
       return AccountManager.get(ApplicationLoader.applicationContext)
               .getAccountsByType(ApplicationLoader.applicationContext.getString(R.string.account_type))[0];
    }

    public static Boolean checkAnyUserLoggedIn(Context context) {
        Logger.log(LOG_TAG, "Checking any users Logged", Log.VERBOSE);

        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));
        if (accounts.length > 0) {
            Logger.log(LOG_TAG, "found:" + accounts.length + " logged", Log.VERBOSE);
            return true;
        }
        return false;
    }

    public static HashMap<String, String> getUserInfo(Context context, Account account) {
        AccountManager accountManager = AccountManager.get(context);
        HashMap<String, String> userInfo = new HashMap<>();
        //TODO:support multiple account
        userInfo.put(SERVER_ADDRESS_KEY,
                accountManager.getUserData(account, SERVER_ADDRESS_KEY));
        userInfo.put(CLIENT_ID_KEY,
                accountManager.getUserData(account, CLIENT_ID_KEY));
        userInfo.put(API_SECRET_KEY,
                accountManager.getUserData(account, API_SECRET_KEY));
        userInfo.put(AUTH_TOKEN_TYPE_KEY,
                accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY));
        userInfo.put(AUTH_TOKEN_EXPIRATION_KEY,
                accountManager.getUserData(account, AUTH_TOKEN_EXPIRATION_KEY));

        return userInfo;
    }

    public static void updateUserData(@NonNull Context context, @NonNull Bundle userInfo, @NonNull Account account) {
        //TODO:support multiple account

        AccountManager manager = AccountManager.get(context);

        Account[] accounts = manager.getAccountsByType(context.getString(R.string.account_type));

        //Update accountsUpdateListener with new user data (token type would be teh same, the others may have changed)
        if (userInfo.containsKey(CLIENT_ID_KEY)) {
            manager.setUserData(account, CLIENT_ID_KEY, userInfo.getString(CLIENT_ID_KEY));
        }
        if (userInfo.containsKey(API_SECRET_KEY)) {
            manager.setUserData(account, API_SECRET_KEY, userInfo.getString(API_SECRET_KEY));
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
}
