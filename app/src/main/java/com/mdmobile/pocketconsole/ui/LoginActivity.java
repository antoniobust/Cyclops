package com.mdmobile.pocketconsole.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.NetworkCallBack;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.LogInViewPagerAdapter;
import com.mdmobile.pocketconsole.apiHandler.ApiRequestManager;
import com.mdmobile.pocketconsole.gson.Token;

public class LoginActivity extends com.mdmobile.pocketconsole.utils.AccountAuthenticatorActivity implements NetworkCallBack {

    //Authenticator intent keys
    public final static String ACCOUNT_TYPE_KEY = "AccountTypeIntentKey";
    public final static String AUTH_TOKEN_TYPE_KEY = "AuthTokenTypeIntentKey";
    public final static String ADDING_NEW_ACCOUNT_KEY = "AddingNewAccountIntentKey";
    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    private final String SERVER_ADDRESS_KEY = "serverAddressKey", CLIENT_ID_KEY = "clientIdKey", API_SECRET_KEY = "apiSecretKey",
            USER_NAME_KEY = "userNameKey", PASSWORD_KEY = "passwordKey";
    ViewPager viewPager;
    TabLayout dotsIndicator;
    Bundle userInputBundle;

    //Token received network callback
    @Override
    public void tokenReceived(Token response) {
        String userName;
        //TODO:launch mainActivity
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }

        //Save new user in Account, if we are here is because there is no account saved so
        //we will add it explicitly
        Bundle userInfo = new Bundle();
        //if debug discard input and use debugging info
        if (BuildConfig.DEBUG) {
            userInfo.putString(CLIENT_ID_KEY, getString(R.string.mc_clientID));
            userInfo.putString(API_SECRET_KEY, getString(R.string.mc_client_secret));
            userInfo.putString(SERVER_ADDRESS_KEY, getString(R.string.mc_server_url));
            userName = getString(R.string.mc_user_name);
        } else {
            userInfo.putString(CLIENT_ID_KEY, userInfo.getString(CLIENT_ID_KEY));
            userInfo.putString(API_SECRET_KEY, userInfo.getString(API_SECRET_KEY));
            userInfo.putString(SERVER_ADDRESS_KEY, userInfo.getString(SERVER_ADDRESS_KEY));
            userName = userInfo.getString(USER_NAME_KEY);
        }

        Account account = new Account(userName, getString(R.string.account_type));
        AccountManager ac = AccountManager.get(getApplicationContext());

        //Register account
        if (ac.addAccountExplicitly(
                account, userInputBundle.getString(PASSWORD_KEY), userInfo)) {
            //TODO:If we are using android M or above notify account authenticate

            Log.i(LOG_TAG, "Account created successfully: " + account.name + " type: " + account.type);
            //Add token to this account
            ac.setAuthToken(account, response.getToken_type(), response.getAccess_token());

        } else {
            //TODO: error saving account check if there is any account with same name
            Log.e(LOG_TAG, "Error creating Account: " + account.name + " type: " + account.type);
        }

    }

    //Error receiving token Network callback
    @Override
    public void errorReceivingToken(String error) {
        //TODO: based on the error message prompt the related error message to user
        Toast.makeText(getApplicationContext(), "error receiving token", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instantiate views
        viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        dotsIndicator = (TabLayout) findViewById(R.id.login_view_pager_dots_indicator);

        //Configure viewPager
        setViewPager();
    }

    //Set up the view pager
    private void setViewPager() {
        //Set adapter to viewPager
        viewPager.setAdapter(new LogInViewPagerAdapter(getSupportFragmentManager()));
        //Adjust margin between pages
        viewPager.setPageMargin(80);

        //Attach custom indicator to view pager
        dotsIndicator.setupWithViewPager(viewPager, true);

        //Adjust margin between tabs in tabLayout
        View dot;
        //Get dots container
        ViewGroup dotsContainer = (ViewGroup) dotsIndicator.getChildAt(0);
        //Instantiate margin to apply
        ViewGroup.MarginLayoutParams p;

        //Navigate through all the indicator dots and apply margins
        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            dot = dotsContainer.getChildAt(i);
            p = (ViewGroup.MarginLayoutParams) dot.getLayoutParams();
            p.setMargins(5, 0, 5, 0);
            dot.requestLayout();
        }
    }


    //LogIn Button OnClick function
    public void logIn(View v) {

        //Get user input
        Bundle userInfo = getUserInput();

        //Request token through ApiRequestManager
        ApiRequestManager.getInstance(getApplicationContext()).addNewAccount(
                userInfo.getString(SERVER_ADDRESS_KEY),
                userInfo.getString(CLIENT_ID_KEY),
                userInfo.getString(API_SECRET_KEY),
                userInfo.getString(USER_NAME_KEY),
                userInfo.getString(PASSWORD_KEY),
                this);

    }


    private Bundle getUserInput() {
        userInputBundle = new Bundle();

        //temp strings to store user input
        String tempString, tempString1;

        for (int i = 0; i < viewPager.getChildCount(); i++) {

            //resetting temp strings from previous cycle
            tempString = "";
            tempString1 = "";

            //According the current position get the relative data
            switch (i) {
                case 0:
                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.server_address_text_view)).getText().toString();

                    if (tempString.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        userInputBundle
                                .putString(SERVER_ADDRESS_KEY, tempString);
                        continue;
                    }
                case 1:
                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.client_id_text_view)).getText().toString();
                    tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.api_secret_text_view)).getText().toString();
                    if (tempString.equals("") || tempString1.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        userInputBundle.putString(CLIENT_ID_KEY, tempString);
                        userInputBundle.putString(API_SECRET_KEY, tempString);
                        continue;
                    }
                case 2:
                    tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.user_name_text_view)).getText().toString();
                    tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.password_text_view)).getText().toString();
                    if (tempString.equals("") || tempString1.equals("")) {
                        showInvalidInputSneakBar();
                        break;
                    } else {
                        userInputBundle.putString(USER_NAME_KEY, tempString);
                        userInputBundle.putString(PASSWORD_KEY, tempString);
                    }
            }
        }

        //If we are out the cycle means that no field is empty return the info bundle
        return userInputBundle;
    }

    //Showed if any input field is empty
    private void showInvalidInputSneakBar() {
        Snackbar.make(viewPager, "Please check your data", Snackbar.LENGTH_SHORT).show();
    }
}

