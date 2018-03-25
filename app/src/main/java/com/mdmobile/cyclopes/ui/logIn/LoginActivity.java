package com.mdmobile.cyclopes.ui.logIn;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mdmobile.cyclopes.BuildConfig;
import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.dataModels.api.Token;
import com.mdmobile.cyclopes.interfaces.NetworkCallBack;
import com.mdmobile.cyclopes.sync.DevicesSyncAdapter;
import com.mdmobile.cyclopes.ui.main.MainActivity;
import com.mdmobile.cyclopes.utils.GeneralUtility;
import com.mdmobile.cyclopes.utils.ServerUtility;
import com.mdmobile.cyclopes.utils.UserUtility;

import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.cyclopes.services.AccountAuthenticator.ACCOUNT_TYPE_KEY;
import static com.mdmobile.cyclopes.services.AccountAuthenticator.ADDING_NEW_ACCOUNT_KEY;
import static com.mdmobile.cyclopes.services.AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY;
import static com.mdmobile.cyclopes.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.cyclopes.services.AccountAuthenticator.REFRESH_AUTH_TOKEN_KEY;
import static com.mdmobile.cyclopes.utils.UserUtility.PASSWORD_KEY;
import static com.mdmobile.cyclopes.utils.UserUtility.USER_NAME_KEY;

public class LoginActivity extends com.mdmobile.cyclopes.utils.AccountAuthenticatorActivity
        implements NetworkCallBack {


    private static final String ATTACHED_FRAGMENT_KEY = "FRAGMENT_ATTACHED";
    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    private final String SERVER_FRAG_TAG = "SERVER_FRAG_TAG";
    private final String USER_FRAG_TAG = "USER_FRAG_TAG";
    private AccountAuthenticatorResponse authenticatorResponse;

    // -- Interface methods
    @Override
    public void tokenReceived(Bundle userInput, Token response) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }
        finishLogin(userInput, response);
    }

    @Override
    public void errorReceivingToken(VolleyError error) {

        String message;
        if (error == null || error.networkResponse == null) {
            message = "Login failed...Please try again";
        } else {
            switch (error.networkResponse.statusCode) {
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    message = "Login failed... Check your credentials";
                    break;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    message = "Login failed... Check your credentials";
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    message = "Internal server error";
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    message = "Server not found";
                    break;
                default:
                    message = "Login failed";
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    // Login activity will check if there is any server already saved. If yes it will prompt add new user interface.
    // If not will give the opportunity to create new server

    // -- Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainActivity.TABLET_MODE = GeneralUtility.isTabletMode(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        if (savedInstanceState == null) {
            if (!ServerUtility.anyActiveServer()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_activity_container, AddServerFragment.newInstance(), SERVER_FRAG_TAG).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_activity_container, AddNewUserFragment.newInstance(), USER_FRAG_TAG).commit();
            }
        }

        //If activity was launched from authenticator get the intent with the auth response
        authenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);


        if (authenticatorResponse == null) {
            if (UserUtility.checkAnyUserLoggedIn()) {
                startMainActivity();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ATTACHED_FRAGMENT_KEY, getAttachedFragmentTag());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (authenticatorResponse == null) {
            if (UserUtility.checkAnyUserLoggedIn()) {
                startMainActivity();
            }
        }

    }

    // Actions OnClick method -> change between server and user fragment
    public void changeSection(View v) {
        if (v.getId() == R.id.add_server_button) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_activity_container, AddServerFragment.newInstance(), SERVER_FRAG_TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_activity_container, AddNewUserFragment.newInstance(), USER_FRAG_TAG).commit();
        }
    }


    private String getAttachedFragmentTag() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded() && fragment.isVisible()) {
                return fragment.getTag();
            }
        }
        return SERVER_FRAG_TAG;
    }

    //Create accountsUpdateListener and return info to accountsUpdateListener authenticator
    private void finishLogin(Bundle userInput, Token response) {
        //TODO: support multiple account
        String userName, tokenType = null, accountType = null, psw;
        Boolean newAccount = true;

        //Save new user in Account, if we are here is because there is no accountsUpdateListener saved so
        //we will add it explicitly
        Bundle userInfo = new Bundle();

        userName = userInput.getString(USER_NAME_KEY);
        psw = userInput.getString(PASSWORD_KEY);


        userInfo.putInt(AUTH_TOKEN_EXPIRATION_KEY, response.getTokenExpiration());
        userInfo.putString(REFRESH_AUTH_TOKEN_KEY, response.getRefreshToken());

        if (getIntent().getExtras() != null && getIntent().hasExtra(AUTH_TOKEN_TYPE_KEY)) {
            tokenType = getIntent().getStringExtra(AUTH_TOKEN_TYPE_KEY);
            userInfo.putString(AUTH_TOKEN_TYPE_KEY, tokenType);
        }

        if (getIntent().getExtras() != null && getIntent().hasExtra(ADDING_NEW_ACCOUNT_KEY)) {
            newAccount = getIntent().getBooleanExtra(ADDING_NEW_ACCOUNT_KEY, false);
        }
        if (getIntent().getExtras() != null && getIntent().hasExtra(ACCOUNT_TYPE_KEY)) {
            accountType = getIntent().getStringExtra(ACCOUNT_TYPE_KEY);
        }

        if (tokenType == null || tokenType.equals("")) {
            tokenType = response.getToken_type();
            userInfo.putString(AUTH_TOKEN_TYPE_KEY, tokenType);
        }
        if (accountType == null || tokenType.equals("")) {
            accountType = getString(R.string.account_type);
        }


        Account account;
        AccountManager accountManager = AccountManager.get(getApplicationContext());

        if (newAccount) {
            account = new Account(userName, accountType);
            //Create the accountsUpdateListener
            accountManager.addAccountExplicitly(account, psw, userInfo);
            accountManager.setPassword(account, psw);
        } else {
            account = accountManager.getAccountsByType(getString(R.string.account_type))[0];
            //Update accountsUpdateListener with new info
            accountManager.setPassword(account, psw);
            //Update account with new user data (token type would be teh same, the others may have changed)
            UserUtility.updateUserData(userInfo);
        }

        //Set the token we have for this accountsUpdateListener
        accountManager.setAuthToken(account, tokenType, response.getAccess_token());

        //Initialize SyncAdapter for periodic devices checks
        DevicesSyncAdapter.initializeSync(account, getApplicationContext());

        //If activity was launched from accountsUpdateListener authenticator return data back
        if (authenticatorResponse != null) {
            setAccountAuthenticatorResult(getIntent().getExtras());

            // Tell the accountsUpdateListener manager settings page that all went well
            setResult(RESULT_OK, getIntent());
            finish();
        } else {
            //Launch MainActivity
            startMainActivity();
        }

    }

    private void startMainActivity() {
        //Login done launch main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

