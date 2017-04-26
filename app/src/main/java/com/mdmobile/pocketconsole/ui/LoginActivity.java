package com.mdmobile.pocketconsole.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements NetworkCallBack {

    //Authenticator intent keys
    public final static String ACCOUNT_TYPE_KEY = "AccountTypeIntentKey";
    public final static String AUTH_TOKEN_TYPE_KEY = "AuthTokenTypeIntentKey";
    public final static String ADDING_NEW_ACCOUNT_KEY = "AddingNewAccountIntentKey";
    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    public final String USER_TOKEN_KEY = "tokenKey", TOKEN_TYPE_KEY = "tokenTypeKey", TOKEN_EXPIRATION = "tokenExpirationKey";
    private final String SERVER_ADDRESS_KEY = "serverAddressKey", CLIENT_ID_KEY = "clientIdKey", API_SECRET_KEY = "apiSecretKey",
            USER_NAME_KEY = "userNameKey", PASSWORD_KEY = "passwordKey";
    ViewPager viewPager;
    TabLayout dotsIndicator;
    Bundle userInputBundle;

    //Interface network callback
    @Override
    public void tokenReceived(Token response) {
        //TODO: register new user with info,launch mainActivity
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }

        //Save new user in Account, if we are here is because there is no account saved so
        //we will add it explicitly
        Bundle userInfo = new Bundle();
        userInfo.putString(USER_TOKEN_KEY, response.getAccess_token());
        userInfo.putString(TOKEN_TYPE_KEY, response.getToken_type());
        userInfo.putInt(TOKEN_EXPIRATION, response.getExpires_in());
        Account account = new Account(userInputBundle.getString(USER_NAME_KEY), getString(R.string.account_type));
        AccountManager ac = AccountManager.get(getApplicationContext());
        if (ac.addAccountExplicitly(
                account, userInputBundle.getString(PASSWORD_KEY), userInfo)) {
            Log.i(LOG_TAG, "Account created successfully: " + account.name + " type: " + account.type);
            //TODO:If we are using android M or above notify account authenticate

        } else {
            Log.e(LOG_TAG, "Error creating Account: " + account.name + " type: " + account.type);
        }

    }

    @Override
    public void errorReceivingToken(String error) {
        //TODO: based on the error message prompt the related error message to user
        Snackbar.make(viewPager, "error receiving token", Snackbar.LENGTH_SHORT).show();
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
        ApiRequestManager.getInstance(getApplicationContext()).getApiToken(
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

