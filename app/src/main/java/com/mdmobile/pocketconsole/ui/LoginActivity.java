package com.mdmobile.pocketconsole.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
    AccountAuthenticatorResponse authenticatorResponse;

    //Token received network callback
    @Override
    public void tokenReceived(Token response) {
        //TODO:launch mainActivity
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }

        finishLogin(response);
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

        //If activity was launched from authenticator get the intent with the auth response
        authenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

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
        ApiRequestManager.getInstance(getApplicationContext()).getToken(
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

    //Create account and return info to account authenticator
    private void finishLogin(Token response) {

        String userName, tokenType = null, accountType = null, psw;
        Boolean newAccount = true;

        //Save new user in Account, if we are here is because there is no account saved so
        //we will add it explicitly
        Bundle userInfo = new Bundle();
        //if debug discard input and use debugging info
        if (BuildConfig.DEBUG) {
            userInfo.putString(CLIENT_ID_KEY, getString(R.string.mc_clientID));
            userInfo.putString(API_SECRET_KEY, getString(R.string.mc_client_secret));
            userInfo.putString(SERVER_ADDRESS_KEY, getString(R.string.mc_server_url));
            userName = getString(R.string.mc_user_name);
            psw = getString(R.string.mc_password);
        } else {
            userInfo.putString(CLIENT_ID_KEY, userInfo.getString(CLIENT_ID_KEY));
            userInfo.putString(API_SECRET_KEY, userInfo.getString(API_SECRET_KEY));
            userInfo.putString(SERVER_ADDRESS_KEY, userInfo.getString(SERVER_ADDRESS_KEY));
            userName = userInputBundle.getString(USER_NAME_KEY);
            psw = userInputBundle.getString(PASSWORD_KEY);
        }

        if (getIntent().getExtras() != null && getIntent().hasExtra(AUTH_TOKEN_TYPE_KEY)) {
            tokenType = getIntent().getStringExtra(AUTH_TOKEN_TYPE_KEY);
        }
        if (getIntent().getExtras() != null && getIntent().hasExtra(ADDING_NEW_ACCOUNT_KEY)) {
            newAccount = getIntent().getBooleanExtra(ADDING_NEW_ACCOUNT_KEY, false);
        }
        if (getIntent().getExtras() != null && getIntent().hasExtra(ACCOUNT_TYPE_KEY)) {
            accountType = getIntent().getStringExtra(ACCOUNT_TYPE_KEY);
        }

        if (tokenType == null || tokenType.equals("")) {
            tokenType = response.getToken_type();
        }
        if (accountType == null || tokenType.equals("")) {
            accountType = getString(R.string.account_type);
        }


        Account account = new Account(userName, accountType);
        AccountManager accountManager = AccountManager.get(getApplicationContext());

        if (newAccount) {
            //Create the account
            accountManager.addAccountExplicitly(account, psw, userInfo);
        } else {
            //Update account with new info
            accountManager.setPassword(account, psw);
        }

        //Set the token we have for this account
        accountManager.setAuthToken(account, tokenType, response.getAccess_token());

        //If activity was launched from account authenticator return data back
        if (authenticatorResponse != null) {
            setAccountAuthenticatorResult(getIntent().getExtras());

            // Tell the account manager settings page that all went well
            setResult(RESULT_OK, getIntent());
            finish();
        }

    }
}

