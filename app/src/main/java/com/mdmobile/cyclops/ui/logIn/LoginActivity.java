package com.mdmobile.cyclops.ui.logIn;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mdmobile.cyclops.BuildConfig;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.api.Token;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.sync.SyncService;
import com.mdmobile.cyclops.ui.dialogs.HintDialog;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

import java.net.HttpURLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.services.AccountAuthenticator.ACCOUNT_TYPE_KEY;
import static com.mdmobile.cyclops.services.AccountAuthenticator.ADDING_NEW_ACCOUNT_KEY;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.cyclops.services.AccountAuthenticator.REFRESH_AUTH_TOKEN_KEY;
import static com.mdmobile.cyclops.utils.UserUtility.PASSWORD_KEY;
import static com.mdmobile.cyclops.utils.UserUtility.USER_NAME_KEY;
import static com.mdmobile.cyclops.utils.UserUtility.checkAnyUserLogged;

public class LoginActivity extends com.mdmobile.cyclops.utils.AccountAuthenticatorActivity
        implements NetworkCallBack, View.OnClickListener {


    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    private final String SERVER_FRAG_TAG = "SERVER_FRAG_TAG";
    private final String USER_FRAG_TAG = "USER_FRAG_TAG";
    public boolean activityForResult = false;
    private TextView serverButton, userButton;
    private AccountAuthenticatorResponse authenticatorResponse;
    private ImageView hintView;

    public static void LaunchActivity() {
        Intent intent = new Intent(applicationContext, LoginActivity.class);
        applicationContext.startActivity(intent);
    }

    // -- Interface methods
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.light_bulb_view:
                HintDialog.Companion.newInstance(getString(R.string.api_console_hint)).show(getSupportFragmentManager(), null);
                break;
        }
    }

    @Override
    public void tokenReceived(Bundle userInput, Token response) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }
        finishLogin(userInput, response);
    }

    @Override
    public void errorReceivingToken(VolleyError error) {
        findViewById(R.id.login_progress_view).setVisibility(View.GONE);
        findViewById(R.id.login_button).setVisibility(View.VISIBLE);
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

    // -- Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainActivity.TABLET_MODE = GeneralUtility.isTabletMode(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (getCallingActivity() != null) {
            activityForResult = true;
        }

        serverButton = findViewById(R.id.add_server_button);
        userButton = findViewById(R.id.add_user_button);
        hintView = findViewById(R.id.light_bulb_view);

        if (savedInstanceState == null) {
            if (!UserUtility.checkAnyUserLogged() || getCallingActivity() != null) {
                serverButton.setVisibility(View.GONE);
                userButton.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_activity_container, AddServerFragment.newInstance(), SERVER_FRAG_TAG).commit();
            } else {
                serverButton.setVisibility(View.VISIBLE);
                userButton.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_activity_container, AddNewUserFragment.newInstance(), USER_FRAG_TAG).commit();
            }
        }
        authenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        hintView.setOnClickListener(this);
    }

    public void changeSection(View v) {
        if (v.getId() == R.id.add_server_button) {
            serverButton.setVisibility(View.GONE);
            if (!checkAnyUserLogged()) {
                userButton.setVisibility(View.GONE);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.login_activity_container, AddServerFragment.newInstance(), SERVER_FRAG_TAG).commit();
        } else {
            userButton.setVisibility(View.GONE);
            if (ServerUtility.anyActiveServer()) {
                serverButton.setVisibility(View.GONE);
            }
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

    private void finishLogin(Bundle userInput, Token response) {
        //TODO: support multiple account
        String userName, tokenType = null, accountType = null, psw;
        Boolean newAccount = true;
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
            accountManager.addAccountExplicitly(account, psw, userInfo);
            accountManager.setPassword(account, psw);
        } else {
            account = accountManager.getAccountsByType(getString(R.string.account_type))[0];
            accountManager.setPassword(account, psw);
            UserUtility.updateUserData(userInfo);
        }
        accountManager.setAuthToken(account, tokenType, response.getAccess_token());
        SyncService.initializeSync(account);

        if (authenticatorResponse != null) {
            setAccountAuthenticatorResult(getIntent().getExtras());
            setResult(RESULT_OK, getIntent());
            finish();
        } else {
            startMainActivity();
        }

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

