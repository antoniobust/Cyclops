package com.mdmobile.pocketconsole.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.mdmobile.pocketconsole.R;

/**
 * Helper methods to deal with users
 */

public class UsersUtility {

    private static final String LOG_TAG = UsersUtility.class.getSimpleName();

    public static Account checkUserExists(Context context, String userName) {
        Logging.log(LOG_TAG, "Checking any users exists", Log.VERBOSE);
        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));

        for (Account account : accounts) {
            if (account.name.equals(userName)) {
                Logging.log(
                        LOG_TAG,
                        "User Found\nUser name:" + account.name + " Account type: " + account.type,
                        Log.INFO);
                return account;
            }
        }

        //If we are out the cycle there is no user already registered with same userName
        return null;
    }

    public static Boolean checkAnyUserLoggedIn(Context context) {
        Logging.log(LOG_TAG, "Checking any users Logged", Log.VERBOSE);

        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));
        if (accounts.length > 0) {
            Logging.log(LOG_TAG, "found:" + accounts.length + " logged", Log.VERBOSE);
            return true;
        }
        return false;
    }

}
