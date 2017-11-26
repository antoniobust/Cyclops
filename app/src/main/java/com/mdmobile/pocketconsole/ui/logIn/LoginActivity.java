package com.mdmobile.pocketconsole.ui.logIn;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.LogInViewPagerAdapter;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.dataModels.api.Token;
import com.mdmobile.pocketconsole.interfaces.NetworkCallBack;
import com.mdmobile.pocketconsole.services.DevicesSyncAdapter;
import com.mdmobile.pocketconsole.ui.main.MainActivity;
import com.mdmobile.pocketconsole.utils.ConfigureServerAsyncTask;
import com.mdmobile.pocketconsole.utils.GeneralUtility;
import com.mdmobile.pocketconsole.utils.Logger;
import com.mdmobile.pocketconsole.utils.ServerUtility;
import com.mdmobile.pocketconsole.utils.ServerXmlConfigParser;
import com.mdmobile.pocketconsole.utils.UserUtility;

import java.io.File;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.ACCOUNT_TYPE_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.ADDING_NEW_ACCOUNT_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.API_SECRET_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_EXPIRATION_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.CLIENT_ID_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.PASSWORD_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.REFRESH_AUTH_TOKEN_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_ADDRESS_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.USER_NAME_KEY;

public class LoginActivity extends com.mdmobile.pocketconsole.utils.AccountAuthenticatorActivity implements NetworkCallBack, ServerXmlConfigParser.ServerXmlParse,
        ActivityCompat.OnRequestPermissionsResultCallback {


    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout dotsIndicator;
    private Bundle userInputBundle;
    private AccountAuthenticatorResponse authenticatorResponse;
    private LogInViewPagerAdapter viewPagerAdapter;
    private int permissionReqID = 100;

    //Token received network callback
    @Override
    public void tokenReceived(Token response) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(), "token received", Toast.LENGTH_SHORT).show();
        }

        finishLogin(response);
    }


    //Error receiving token Network callback
    @Override
    public void errorReceivingToken(VolleyError error) {
        //TODO: based on the error message prompt the related error message to user
        Toast.makeText(getApplicationContext(), "error receiving token", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MainActivity.TABLET_MODE = GeneralUtility.isTabletMode(getApplicationContext());

        //Instantiate views
        viewPager = findViewById(R.id.login_view_pager);
        dotsIndicator = findViewById(R.id.login_view_pager_dots_indicator);

        //If activity was launched from authenticator get the intent with the auth response
        authenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);


        if (authenticatorResponse == null) {
            if (UserUtility.checkAnyUserLoggedIn()) {
                //user found, launch main activity
                startMainActivity();
            }
        }
        setViewPager();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (checkConfigurationFile()) {
            parseServerConfFile();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionReqID) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                parseServerConfFile();
            }
        }
    }


    //Set up the view pager
    private void setViewPager() {
        viewPagerAdapter = new LogInViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageMargin(80);

        dotsIndicator.setupWithViewPager(viewPager, true);

        View dot;
        ViewGroup dotsContainer = (ViewGroup) dotsIndicator.getChildAt(0);
        ViewGroup.MarginLayoutParams p;

        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            dot = dotsContainer.getChildAt(i);
            p = (ViewGroup.MarginLayoutParams) dot.getLayoutParams();
            p.setMargins(5, 0, 5, 0);
            dot.requestLayout();
        }
    }


    public void logIn(View v) {
        Bundle userInfo = getUserInput();

        Logger.log(LOG_TAG, "Requesting token...", Log.VERBOSE);
        ApiRequestManager.getInstance().getToken(
                userInfo.getString(SERVER_ADDRESS_KEY),
                userInfo.getString(CLIENT_ID_KEY),
                userInfo.getString(API_SECRET_KEY),
                userInfo.getString(USER_NAME_KEY),
                userInfo.getString(PASSWORD_KEY),
                this);

    }


    private Bundle getUserInput() {
        userInputBundle = new Bundle();
        String tempString, tempString1;

        int fragmentCounter = viewPager.getChildCount();
        if (fragmentCounter == 1) {
            userInputBundle.putAll(ServerUtility.getServer());
        }

        for (int i = 0; i < fragmentCounter; i++) {
            tempString = "";
            tempString1 = "";

            Fragment fragment = viewPagerAdapter.getItem(i);

            if (fragment instanceof LoginConfigureServerFragment) {

                tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.server_address_text_view)).getText().toString();
                if (tempString.equals("")) {
                    showInvalidInputSneakBar();
                    break;
                } else {
                    userInputBundle.putString(SERVER_ADDRESS_KEY, tempString);
                }

            } else if (fragment instanceof LoginConfigureSecretIdFragment) {

                tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.client_id_text_view)).getText().toString();
                tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.api_secret_text_view)).getText().toString();
                if (tempString.equals("") || tempString1.equals("")) {
                    showInvalidInputSneakBar();
                    break;
                } else {
                    userInputBundle.putString(CLIENT_ID_KEY, tempString);
                    userInputBundle.putString(API_SECRET_KEY, tempString);
                }

            } else if (fragment instanceof LoginUserNamePasswordFragment) {

                tempString = ((TextView) viewPager.getChildAt(i).findViewById(R.id.user_name_text_view)).getText().toString();
                tempString1 = ((TextView) viewPager.getChildAt(i).findViewById(R.id.password_text_view)).getText().toString();
                if (tempString.equals("") || tempString1.equals("")) {
                    showInvalidInputSneakBar();
                    break;
                } else {
                    userInputBundle.putString(USER_NAME_KEY, tempString);
                    userInputBundle.putString(PASSWORD_KEY, tempString1);
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

    //Create accountsUpdateListener and return info to accountsUpdateListener authenticator
    private void finishLogin(Token response) {
        //TODO: support multiple account
        String userName, tokenType = null, accountType = null, psw;
        Boolean newAccount = true;

        //Save new user in Account, if we are here is because there is no accountsUpdateListener saved so
        //we will add it explicitly
        Bundle userInfo = new Bundle();
        //if debug discard input and use debugging info
//        if (BuildConfig.DEBUG) {
//            userInfo.putString(CLIENT_ID_KEY, getString(R.string.mc_clientID));
//            userInfo.putString(API_SECRET_KEY, getString(R.string.mc_client_secret));
//            userInfo.putString(SERVER_ADDRESS_KEY, getString(R.string.mc_server_url));
//            userName = getString(R.string.mc_user_name);
//            psw = getString(R.string.mc_password);
//        } else {
        userInfo.putString(CLIENT_ID_KEY, userInputBundle.getString(CLIENT_ID_KEY));
        userInfo.putString(API_SECRET_KEY, userInputBundle.getString(API_SECRET_KEY));
        userInfo.putString(SERVER_ADDRESS_KEY, userInputBundle.getString(SERVER_ADDRESS_KEY));
        userName = userInputBundle.getString(USER_NAME_KEY);
        psw = userInputBundle.getString(PASSWORD_KEY);
//        }

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

    @Override
    public void xmlParseComplete() {
        Bundle serverInfo = ServerUtility.getServer();
        if (serverInfo == null) {
            return;
        }

        Logger.log(LOG_TAG, "Removing unnecessary fragments from login pager", Log.VERBOSE);
        viewPagerAdapter.removeSererFragments();
        viewPager.setCurrentItem(2);
        dotsIndicator.setVisibility(View.INVISIBLE);
    }

    private boolean checkConfigurationFile() {
        if (!new File(Environment.getExternalStorageDirectory() + File.separator + getString(R.string.server_ini_file_name)).exists()) {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file not found", Log.VERBOSE);
            return false;
        } else {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file found", Log.INFO);
            return true;
        }
    }

    private void parseServerConfFile() {
        File serverSetupFile = new File(Environment.getExternalStorageDirectory() + File.separator + getString(R.string.server_ini_file_name));
        if (!GeneralUtility.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            GeneralUtility.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, permissionReqID);
        } else {
            ConfigureServerAsyncTask configureServerAsyncTask = new ConfigureServerAsyncTask(this);
            configureServerAsyncTask.execute(serverSetupFile);
        }
    }
}

