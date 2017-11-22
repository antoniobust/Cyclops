package com.mdmobile.pocketconsole.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.DevicesListAdapter;
import com.mdmobile.pocketconsole.adapters.ServerListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.services.DevicesSyncAdapter;
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.pocketconsole.ui.logIn.LoginActivity;
import com.mdmobile.pocketconsole.ui.main.dashboard.DashboardFragment;
import com.mdmobile.pocketconsole.ui.main.myDevices.DevicesFragment;
import com.mdmobile.pocketconsole.ui.main.server.ServerDetailsActivity;
import com.mdmobile.pocketconsole.ui.main.server.ServerFragment;
import com.mdmobile.pocketconsole.ui.main.users.UsersFragment;
import com.mdmobile.pocketconsole.utils.GeneralUtility;
import com.mdmobile.pocketconsole.utils.Logger;

import static com.mdmobile.pocketconsole.R.id.main_activity_fragment_container;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;


public class MainActivity extends AppCompatActivity implements DevicesListAdapter.DeviceSelected,
        ServerListAdapter.onClick {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String TOOLBAR_FILTER_STATUS = "FILTER_TOOLBAR_VISIBILITY";
    //Define a flag if we are in tablet layout or not
    public static boolean TABLET_MODE;
    String devId, devName;
    Toolbar filtersToolbar;
    RecyclerView filtersRecyclerView;

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
                    hideFiltersToolbar(View.VISIBLE);
                    ft.replace(main_activity_fragment_container, DevicesFragment.newInstance(), "DevicesFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_dashboard:
                    if (getSupportFragmentManager().findFragmentByTag("DashboardFragment") != null) {
                        break;
                    }
                    hideFiltersToolbar(View.VISIBLE);
                    ft.replace(main_activity_fragment_container, DashboardFragment.newInstance(), "DashboardFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_server:
                    if (getSupportFragmentManager().findFragmentByTag("ServerFragment") != null) {
                        break;
                    }
                    hideFiltersToolbar(View.GONE);
                    ft.replace(main_activity_fragment_container, ServerFragment.newInstance(), "ServerFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_users:
                    if (getSupportFragmentManager().findFragmentByTag("UserFragment") != null) {
                        break;
                    }
                    hideFiltersToolbar(View.GONE);
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

        filtersToolbar = findViewById(R.id.filters_toolbar);
//        supportPostponeEnterTransition();

        if (savedInstanceState != null && savedInstanceState.containsKey(TOOLBAR_FILTER_STATUS)) {
            filtersToolbar.setVisibility(savedInstanceState.getInt(TOOLBAR_FILTER_STATUS));
        }

        if (filtersToolbar.getVisibility() == View.VISIBLE) {
            setFilterView();
        }
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
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
        outState.putInt(TOOLBAR_FILTER_STATUS, filtersToolbar.getVisibility());
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


    private void startDetailsActivity(String devId, String devName) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra(DEVICE_NAME_EXTRA_KEY, devName);
        intent.putExtra(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId);
        startActivity(intent);
    }

    private void hideFiltersToolbar(final int setVisibility) {
        if (filtersToolbar != null && filtersToolbar.getVisibility() != setVisibility) {
            float translation;
            if (setVisibility == View.GONE) {
                translation = -GeneralUtility.dpToPx(this, 32);
            } else {
                translation = 32;
            }
            filtersToolbar.animate().translationY(translation).setDuration(80).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (setVisibility == View.VISIBLE) {
                        filtersToolbar.setVisibility(setVisibility);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (setVisibility == View.GONE) {
                        filtersToolbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    private void setFilterView(){
        filtersRecyclerView = filtersToolbar.findViewById(R.id.filter_recycler_view);
        filtersRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        filtersRecyclerView.setAdapter(new FiltersRecyclerAdapter());
    }

    @Override
    public void itemCLicked(Parcelable serverParcel) {
        Intent intent = new Intent(this, ServerDetailsActivity.class);
        intent.putExtra(McContract.DEPLOYMENT_SERVER_TABLE_NAME, serverParcel);
        startActivity(intent);
    }
}
