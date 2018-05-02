package com.mdmobile.cyclops.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.DevicesListAdapter;
import com.mdmobile.cyclops.adapters.ServerListAdapter;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.sync.DevicesSyncAdapter;
import com.mdmobile.cyclops.ui.BaseActivity;
import com.mdmobile.cyclops.ui.logIn.LoginActivity;
import com.mdmobile.cyclops.ui.main.dashboard.DashboardFragment;
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity;
import com.mdmobile.cyclops.ui.main.myDevices.DevicesFragment;
import com.mdmobile.cyclops.ui.main.server.ServerDetailsActivity;
import com.mdmobile.cyclops.ui.main.server.ServerFragment;
import com.mdmobile.cyclops.ui.main.users.UsersFragment;
import com.mdmobile.cyclops.ui.settings.AppCompatPreferenceActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.RecyclerEmptyView;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

import java.util.Calendar;

import static android.view.View.GONE;
import static com.mdmobile.cyclops.R.id.main_activity_fragment_container;
import static com.mdmobile.cyclops.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;
import static com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY;


public class MainActivity extends BaseActivity implements DevicesListAdapter.DeviceSelected,
        ServerListAdapter.onClick, NavigationView.OnNavigationItemSelectedListener {
    public static final String DEV_SYNC_BROADCAST_ACTION = "com.mdmobile.cyclops.DEVICE_SYNC_DONE";
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String TOOLBAR_FILTER_STATUS = "FILTER_TOOLBAR_VISIBILITY";
    //Define a flag if we are in tablet layout or not
    public static boolean TABLET_MODE;
    String devId, devName;
    Toolbar filtersToolbar;
    RecyclerEmptyView filtersRecycler;
    private TextView lastSyncTimeView;
    private final BroadcastReceiver devSyncReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences preferences =
                    getApplicationContext().getSharedPreferences(getString(R.string.general_shared_preference), MODE_MULTI_PROCESS);

            long lastSync = preferences.getLong(getString(R.string.last_dev_sync_pref), 0);
            if (lastSync != 0) {
                updateSyncTimeView(lastSync);
            }
        }
    };
    private NavigationView drawerNavigationView;
    private DrawerLayout navigationDrawer;
    // -- Interface methods
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
                    hideFiltersToolbar(GONE);
                    ft.replace(main_activity_fragment_container, ServerFragment.newInstance(), "ServerFragment");
                    ft.commit();
                    return true;

                case R.id.navigation_users:
                    if (getSupportFragmentManager().findFragmentByTag("UserFragment") != null) {
                        break;
                    }
                    hideFiltersToolbar(GONE);
                    ft.replace(main_activity_fragment_container, UsersFragment.newInstance(), "UserFragment");
                    ft.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_logout:
                logout();
                navigationDrawer.closeDrawer(Gravity.START, true);
                return true;
            case R.id.drawer_settings:
                Intent intent = new Intent(this, AppCompatPreferenceActivity.class);
                startActivity(intent);
                navigationDrawer.closeDrawer(Gravity.START, true);
                return true;

        }
        return false;
    }

    // OnClick avatar click
    public void setUserLogo(View v) {
        //TODO: diaplay dialog to chenge user logo
    }

    @Override
    public void itemCLicked(Parcelable serverParcel) {
        Intent intent = new Intent(this, ServerDetailsActivity.class);
        intent.putExtra(McContract.DEPLOYMENT_SERVER_TABLE_NAME, serverParcel);
        startActivity(intent);
    }

    @Override
    public void onDeviceSelected(String devId, String devName) {
        this.devId = devId;
        this.devName = devName;

        startDetailsActivity(devId, devName);
    }


    // -- Life cycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filtersToolbar = findViewById(R.id.filters_toolbar);

        if (savedInstanceState != null && savedInstanceState.containsKey(TOOLBAR_FILTER_STATUS)) {
            filtersToolbar.setVisibility(savedInstanceState.getInt(TOOLBAR_FILTER_STATUS));
        }

        setNavigationDrawer();
        setFiltersView();
        setLastSyncTimeView();
        setBottomNavigationView(savedInstanceState);
        setActionBar();

        //Set account manager to be used in this activity

    }

    private void setActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //TODO: create a debug Version of main_activity_toolbar for extra options
        inflater.inflate(R.menu.main_activity_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.debug_sync_devices_now:
//                syncDevicesNow();
//                return true;
//            case R.id.debug_refresh_token:
//                refreshToken();
//                return true;
//            case R.id.debug_invalidate_token:
//                invalidateToken();
//                return true;
            case android.R.id.home:
                navigationDrawer.openDrawer(Gravity.START, true);
                return true;
            case R.id.main_activity_search_button:
                item.expandActionView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(devSyncReceiver, new IntentFilter(DEV_SYNC_BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(devSyncReceiver);
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
        Account account = UserUtility.getUser();
        DevicesSyncAdapter.syncImmediately(getApplicationContext(), account);
    }

    private void refreshToken() {
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account account = UserUtility.getUser();
        String token = accountManager.peekAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        accountManager.getAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY),
                null, new LoginActivity(), null, null);
        Logger.log(LOG_TAG, "Token refresh manually forced", Log.VERBOSE);
    }

    private void invalidateToken() {
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account account = UserUtility.getUser();
        String token = accountManager.peekAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        Logger.log(LOG_TAG, "Token manually invalidated", Log.VERBOSE);

    }

    private void startDetailsActivity(String devId, String devName) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra(DEVICE_NAME_EXTRA_KEY, devName);
        intent.putExtra(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId);
        startActivity(intent);
    }

    private void setFiltersView() {
        filtersRecycler = filtersToolbar.findViewById(R.id.filters_recycler);
        View emptyView = filtersToolbar.findViewById(R.id.filters_recycler_empty_view);
        filtersRecycler.setEmptyView(emptyView);
    }


    private void hideFiltersToolbar(final int setVisibility) {
        if (filtersToolbar != null && filtersToolbar.getVisibility() != setVisibility) {
            float translation = -filtersToolbar.getHeight();
            if (setVisibility == View.VISIBLE) {
                translation = 0;
            }

            filtersToolbar.animate().translationY(translation).setDuration(80)
                    .setStartDelay(100).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (setVisibility == View.VISIBLE) {
                        filtersToolbar.setVisibility(setVisibility);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (setVisibility == GONE) {
                        filtersToolbar.setVisibility(GONE);
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

    private void setNavigationDrawer() {
        navigationDrawer = findViewById(R.id.main_activity_drawer_layout);
        drawerNavigationView = navigationDrawer.findViewById(R.id.drawer_nav_view);

        View headerView = drawerNavigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.nav_user_name_text_view))
                .setText(UserUtility.getUser().name);
        ((TextView) headerView.findViewById(R.id.nav_server_text_view))
                .setText(ServerUtility.getActiveServer().getServerName());
        ((ImageView) headerView.findViewById(R.id.nav_user_icon))
                .setImageDrawable(UserUtility.getUserLogo());

        drawerNavigationView.setNavigationItemSelectedListener(this);
    }

    // Navigation Drawer -> on server text view click toggle menu
    public void toggleMenu(View view) {
        View arrowIcon = drawerNavigationView.findViewById(R.id.nav_server_selector_icon);
        if (drawerNavigationView.getMenu().findItem(R.id.drawer_logout).isVisible()) {
            arrowIcon.animate().rotationBy(180).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
            drawerNavigationView.getMenu().setGroupVisible(R.id.nav_drawer_server_list_group, true);
            drawerNavigationView.getMenu().setGroupVisible(R.id.nav_drawer_general_settings_group, false);

            Server[] servers = ServerUtility.getAllServers();

            Menu menu = drawerNavigationView.getMenu();
            MenuItem item;
            for (Server s : servers) {
                item = menu.add(R.id.nav_drawer_server_list_group, 1, Menu.NONE, s.getServerName());
                item.setIcon(R.drawable.ic_server_black_24dp);
            }
        } else {
            arrowIcon.animate().rotationBy(180).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
            drawerNavigationView.getMenu().setGroupVisible(R.id.nav_drawer_server_list_group, false);
            drawerNavigationView.getMenu().setGroupVisible(R.id.nav_drawer_general_settings_group, true);
        }
    }


    private void setLastSyncTimeView() {
        lastSyncTimeView = filtersToolbar.findViewById(R.id.last_sync_view);
        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.general_shared_preference), MODE_MULTI_PROCESS);

        long lastSync = preferences.getLong(getString(R.string.last_dev_sync_pref), 0);
        updateSyncTimeView(lastSync);
    }

    private void setBottomNavigationView(Bundle savedInstanceState) {
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_dashboard);
        }
    }

    private void updateSyncTimeView(long syncTime) {
        String[] labels = getResources().getStringArray(R.array.last_sync_labels);
        String label = labels[4];

        if (lastSyncTimeView == null) {
            return;
        }
        try {
            if (syncTime == 0) {
                return;
            }
            long currentTime = Calendar.getInstance().getTime().getTime();
            long diff = currentTime - syncTime;
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long elapsedDays = diff / daysInMilli;

            if (elapsedDays > 0) {
                label = labels[3];
                label = String.format(label, elapsedDays);
                return;
            }
            long elapsedHours = diff / hoursInMilli;
            if (elapsedHours > 0) {
                label = labels[2];
                label = String.format(label, elapsedHours);
                return;
            }
            long elapsedMinutes = diff / minutesInMilli;
            if (elapsedMinutes > 0) {
                label = labels[1];
                label = String.format(label, elapsedMinutes);
            } else if (elapsedDays == 0) {
                label = labels[0];
            }
        } finally {
            lastSyncTimeView.setText(label);
        }

    }

    private void logout() {
        // To log out we need to clear
        // 1. The server currently active and all related devices
        // 2. Current user preferences
        // 3. Go back to login activity
        // TODO: optimize
        String serverName = ServerUtility.getActiveServer().getServerName();

        Cursor c = getContentResolver().query(McContract.ServerInfo.CONTENT_URI, new String[]{McContract.ServerInfo._ID},
                McContract.ServerInfo.NAME + "=?", new String[]{serverName}, null);
        if (c == null || !c.moveToFirst()) {
            return;
        }
        int serverId = c.getInt(0);
        c.close();
        ServerUtility.deleteServer(serverId);

        UserUtility.clearUserPreferences(UserUtility.getUser().name);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
