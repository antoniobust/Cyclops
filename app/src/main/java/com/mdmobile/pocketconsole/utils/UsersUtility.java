package com.mdmobile.pocketconsole.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.mdmobile.pocketconsole.R;

/**
 *  Helper methods to deal with users
 */

public class UsersUtility {

    private static final String LOG_TAG = UsersUtility.class.getSimpleName();

    public static Account checkUserExists(Context context,String userName){
        Account[] accounts =
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type));

        for(Account account : accounts){
            if(account.name.equals(userName)){
                Log.v(LOG_TAG,"User already exists\nUser name:"+account.name+" Account type: " + account.type);
                return account;
            }
        }

        //If we are out the cycle there is no user already registered with same userName
        return null;
    }

}
