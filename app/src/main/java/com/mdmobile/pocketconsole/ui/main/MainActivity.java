package com.mdmobile.pocketconsole.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.services.RefreshDataService;
import com.mdmobile.pocketconsole.ui.logIn.LoginActivity;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.AUTH_TOKEN_TYPE_KEY;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public final static String SEARCH_QUERY_KEY = "searchQueryKey";
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
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
            actionBar.setTitle(R.string.app_name);
        }

        //Set account manager to be used in this activity
        accountManager = AccountManager.get(getApplicationContext());

        Intent serviceIntent = new Intent(getApplicationContext(), RefreshDataService.class);
        serviceIntent.setAction(getString(R.string.download_devices_action));
        serviceIntent.setPackage(this.getPackageName());
        startService(serviceIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //TODO: create a debug version of menu for extra options
        inflater.inflate(R.menu.main_activity_toolbar, menu);

        //Get search view and set searchable conf
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_activity_search_button).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(this);
        return true;
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
    }

    private void refreshToken() {

        Account[] account = accountManager.getAccountsByType(getString(R.string.account_type));
        String token = accountManager.peekAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY));
        accountManager.invalidateAuthToken(getString(R.string.account_type), token);
        accountManager.getAuthToken(account[0], accountManager.getUserData(account[0], AUTH_TOKEN_TYPE_KEY),
                null, new LoginActivity(), null, null);
//
//        Intent serviceIntent = new Intent(getApplicationContext(), RefreshDataService.class);
//        serviceIntent.setAction(getString(R.string.download_devices_action));
//        serviceIntent.setPackage(getPackageName());
//        startService(serviceIntent);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //On text changed send an intent to devices list fragment so it refreshes the listView with results
        Bundle args = new Bundle(1);
        args.putString(SEARCH_QUERY_KEY, query);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DevicesFragment devicesFragment = DevicesFragment.newInstance();
        devicesFragment.setArguments(args);

        ft.replace(R.id.main_activity_fragment_container, devicesFragment).commit();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {

        return false;
    }
}
