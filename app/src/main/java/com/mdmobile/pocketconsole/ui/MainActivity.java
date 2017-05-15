package com.mdmobile.pocketconsole.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.utils.Logger;

import java.io.IOException;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.ACCOUNT_TYPE_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;


public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    OnAccountsUpdateListener accountsUpdateListener = new OnAccountsUpdateListener() {
        @Override
        public void onAccountsUpdated(Account[] accounts) {
            String token = AccountManager.get(getApplicationContext()).peekAuthToken(accounts[0], getString(R.string.account_type));
            Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
        }
    };

    //Bottom navigation bar, navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    ft.replace(R.id.main_activity_fragment_container, DevicesFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_profiles:
                    ft.replace(R.id.main_activity_fragment_container, ProfilesFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_server:
                    ft.replace(R.id.main_activity_fragment_container, ServerFragment.newInstance());
                    ft.commit();
                    return true;
                case R.id.navigation_users:
                    ft.replace(R.id.main_activity_fragment_container, UsersFragment.newInstance());
                    ft.commit();
                    return true;
            }
            return false;
        }

    };

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_devices);

        //Set action bar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //Set account manager to be used in this activity
        accountManager = AccountManager.get(getApplicationContext());
        accountManager.addOnAccountsUpdatedListener(accountsUpdateListener, null, true);

//        ApiRequestManager.getInstance(getApplicationContext()).getAndroidDevices();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_activity_options, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.debug_refresh_token) {
            refreshToken();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        accountManager.removeOnAccountsUpdatedListener(accountsUpdateListener);
    }

    private void refreshToken() {
        AccountManagerCallback<Bundle> managerCallback = new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture accountManagerFuture) {
                try {
                    if (accountManagerFuture.isDone() && !accountManagerFuture.isCancelled()) {
                        Bundle newInfo = (Bundle) accountManagerFuture.getResult();

                        if (newInfo.containsKey(AccountManager.KEY_INTENT)) {
                            //TODO:after getting new credentials if we get token we need to save new password
                            //which is not getting saved -> stays null after clearPassword
                            Intent intent = (Intent) newInfo.get(AccountManager.KEY_INTENT);
                            startActivity(intent);
                        }
                    }

                } catch (AuthenticatorException e) {
                    if (e.getMessage().equals("AuthenticationException")) {
                        //Launch login activity
                        //TODO:Launch log in activity
                        Logger.log(LOG_TAG, "Authentication exception ... No connection was possible with account authenticator", Log.ERROR);
                        e.printStackTrace();

                        //Clearing user credential and let the user input new ones
                        AccountManager accountManager = AccountManager.get(getApplicationContext());
                        Account[] accounts = accountManager.getAccountsByType(getString(R.string.account_type));
                        accountManager.clearPassword(accounts[0]);
                        AccountManager.get(getApplicationContext()).getAuthToken(accounts[0],
                                accountManager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY)
                                , null, null, this, null);

                    }
                } catch (IOException | OperationCanceledException e) {
                    e.printStackTrace();
                }
            }
        };


        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] account = accountManager.getAccountsByType(getString(R.string.account_type));
        String token = accountManager.peekAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        accountManager.getAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY),
                null, new LoginActivity(), managerCallback, new Handler());


    }
}
