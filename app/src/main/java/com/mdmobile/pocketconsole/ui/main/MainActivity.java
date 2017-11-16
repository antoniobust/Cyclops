package com.mdmobile.pocketconsole.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.ServerDetailsActivity;
import com.mdmobile.pocketconsole.adapters.DevicesListAdapter;
import com.mdmobile.pocketconsole.adapters.ServerListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.services.DevicesSyncAdapter;
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.pocketconsole.ui.logIn.LoginActivity;
import com.mdmobile.pocketconsole.ui.main.dashboard.DashboardFragment;
import com.mdmobile.pocketconsole.ui.main.myDevices.DevicesFragment;
import com.mdmobile.pocketconsole.ui.main.server.ServerFragment;
import com.mdmobile.pocketconsole.ui.main.users.UsersFragment;
import com.mdmobile.pocketconsole.utils.Logger;

import static com.mdmobile.pocketconsole.R.id.main_activity_fragment_container;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;


public class MainActivity extends AppCompatActivity implements DevicesListAdapter.DeviceSelected,
        ServerListAdapter.onClick {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    //Define a flag if we are in tablet layout or not
    public static boolean TABLET_MODE;
    String devId, devName;
    //Bottom navigation bar, navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    if (getSupportFragmentManager().findFragmentByTag("DevicesFragment") != null) {
                        break;
                    }
                    ft.replace(main_activity_fragment_container, DevicesFragment.newInstance(), "DevicesFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_dashboard:
                    if (getSupportFragmentManager().findFragmentByTag("DashboardFragment") != null) {
                        break;
                    }
                    ft.replace(main_activity_fragment_container, DashboardFragment.newInstance(), "DashboardFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_server:
                    if (getSupportFragmentManager().findFragmentByTag("ServerFragment") != null) {
                        break;
                    }
                    ft.replace(main_activity_fragment_container, ServerFragment.newInstance(), "ServerFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_users:
                    if (getSupportFragmentManager().findFragmentByTag("UserFragment") != null) {
                        break;
                    }
                    ft.replace(main_activity_fragment_container, UsersFragment.newInstance(), "UserFragment");
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

//        supportPostponeEnterTransition();

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_dashboard);
        }

        //Set action bar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        //Set account manager to be used in this activity
        accountManager = AccountManager.get(getApplicationContext());

        if (TABLET_MODE && savedInstanceState != null && savedInstanceState.containsKey(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY)) {
            showDetailsFragment(savedInstanceState.getString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY), savedInstanceState.getString(DEVICE_NAME_EXTRA_KEY));
        }
//        Intent serviceIntent = new Intent(getApplicationContext(), RefreshDataService.class);
//        serviceIntent.setAction(getString(R.string.download_devices_action));
//        serviceIntent.setPackage(this.getPackageName());
//        startService(serviceIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //TODO: create a debug version of menu for extra options
        inflater.inflate(R.menu.main_activity_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.debug_sync_devices_now:
                syncDevicesNow();
                return true;
            case R.id.debug_refresh_token:
                refreshToken();
                return true;
            case R.id.debug_invalidate_token:
                invalidateToken();
                return true;
            case R.id.main_activity_search_button:
                item.expandActionView();
                return true;
            case R.id.logout_action:
                logOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logOut() {

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (TABLET_MODE && devName != null && devId != null) {
            outState.putString(DEVICE_NAME_EXTRA_KEY, devName);
            outState.putString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId);
        }
    }

    private void syncDevicesNow() {
        Logger.log(LOG_TAG, "Immediate device syc manually requested... ", Log.VERBOSE);
        Account account = accountManager.getAccountsByType(getString(R.string.account_type))[0];
        DevicesSyncAdapter.syncImmediately(getApplicationContext(), account);
    }

    private void refreshToken() {

        Account[] account = accountManager.getAccountsByType(getString(R.string.account_type));
        String token = accountManager.peekAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        accountManager.getAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY),
                null, new LoginActivity(), null, null);
        Logger.log(LOG_TAG, "Token refresh manually forced", Log.VERBOSE);

//
//        Intent serviceIntent = new Intent(getApplicationContext(), RefreshDataService.class);
//        serviceIntent.setAction(getString(R.string.download_devices_action));
//        serviceIntent.setPackage(getPackageName());
//        startService(serviceIntent);

    }

    private void invalidateToken() {

        Account[] account = accountManager.getAccountsByType(getString(R.string.account_type));
        String token = accountManager.peekAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        Logger.log(LOG_TAG, "Token manually invalidated", Log.VERBOSE);

    }

    //On device selected open details view
    @Override
    public void onDeviceSelected(String devId, String devName) {
        this.devId = devId;
        this.devName = devName;

        startDetailsActivity(devId, devName);

    }


    private void showDetailsFragment(String devId, String devName) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//
//        LinearLayout mainContainer = (LinearLayout) findViewById(R.id.main_activity_linear_layout);
//        SwipeRefreshLayout deviceList = (SwipeRefreshLayout) mainContainer.findViewById(R.id.devices_swipe_refresh);
//        CardView detailsContainer = (CardView) mainContainer.findViewById(R.id.main_activity_device_details_container);
//
//
//        if (detailsContainer.getVisibility() == View.GONE) {
//            detailsContainer.setVisibility(View.VISIBLE);
//        }
//
//        mainContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
//        layoutParams.setMarginEnd(GeneralUtility.dpToPx(getApplicationContext(), 12));
//        layoutParams.setMarginStart(GeneralUtility.dpToPx(getApplicationContext(), 0));
//        deviceList.setLayoutParams(layoutParams);
//        detailsContainer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4f));
//        detailsContainer.requestLayout();
//        deviceList.requestLayout();
//
//        fragmentTransaction.replace(R.id.main_activity_device_details_container, DeviceDetailsFragment.newInstance(devId, devName, null, null), getString(R.string.details_fragment_tag)).commit();
    }

    private void startDetailsActivity(String devId, String devName) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra(DEVICE_NAME_EXTRA_KEY, devName);
        intent.putExtra(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId);
        startActivity(intent);
    }


    @Override
    public void itemCLicked(Parcelable serverParcel) {
        Intent intent = new Intent(this, ServerDetailsActivity.class);
        intent.putExtra(McContract.DEPLOYMENT_SERVER_TABLE_NAME, serverParcel);
        startActivity(intent);
    }
}
