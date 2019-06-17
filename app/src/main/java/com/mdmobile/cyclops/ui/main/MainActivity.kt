package com.mdmobile.cyclops.ui.main

import android.accounts.AccountManager
import android.animation.Animator
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.vending.licensing.*
import com.mdmobile.cyclops.BuildConfig
import com.mdmobile.cyclops.CyclopsApplication.Companion.applicationContext
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.R.id.main_activity_fragment_container
import com.mdmobile.cyclops.adapters.DevicesListAdapter
import com.mdmobile.cyclops.adapters.ServerListAdapter
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.security.ServerNotFound
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.AUTH_TOKEN_TYPE_KEY
import com.mdmobile.cyclops.sync.SyncService
import com.mdmobile.cyclops.ui.BaseActivity
import com.mdmobile.cyclops.ui.BasicFragment
import com.mdmobile.cyclops.ui.dialogs.LicenceErrorDialog
import com.mdmobile.cyclops.ui.logIn.LoginActivity
import com.mdmobile.cyclops.ui.main.dashboard.DashboardFragment
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_NAME_EXTRA_KEY
import com.mdmobile.cyclops.ui.main.myDevices.DevicesFragment
import com.mdmobile.cyclops.ui.main.server.ServerDetailsActivity
import com.mdmobile.cyclops.ui.main.server.ServerFragment
import com.mdmobile.cyclops.ui.main.users.UsersFragment
import com.mdmobile.cyclops.ui.settings.SettingsActivity
import com.mdmobile.cyclops.util.GeneralUtility
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerUtility
import com.mdmobile.cyclops.util.UserUtility
import getMcAccount
import java.util.*


class MainActivity : BaseActivity(), DevicesListAdapter.DeviceSelected, ServerListAdapter.onClick, NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    internal var devId: String? = null
    internal var devName: String? = null
    internal var filtersToolbar: Toolbar? = null
    private var progressBar: ProgressBar? = null
    private var syncActions = 0
    private var actionProgress = 0
    private var lastSyncTimeView: TextView? = null
    private val syncReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == SYNC_DONE_BROADCAST_ACTION) {
                progressBar!!.progress = 100
                val preferences = applicationContext.getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_MULTI_PROCESS)

                val lastSync = preferences.getLong(getString(R.string.last_dev_sync_pref), 0)
                if (lastSync != 0L) {
                    updateSyncTimeView(lastSync)
                }
                progressBar!!.visibility = View.INVISIBLE
                progressBar!!.progress = 0

            } else if (intent.action == UPDATE_LOADING_BAR_ACTION) {
                if (syncActions == 0) {
                    syncActions = intent.getIntExtra(UPDATE_LOADING_BAR_ACTION_COUNT, 3)
                    actionProgress = calculateDelta(progressBar!!.max, syncActions)
                }
                progressBar!!.incrementProgressBy(actionProgress)
            }
        }
    }
    private var mLicenceChecker: LicenseChecker? = null
    private var mLicenseCheckerCallback: LicenseCheckerCallback? = null
    private var mLicenceCheckHandler: Handler? = null
    private var drawerNavigationView: NavigationView? = null
    private var navigationDrawer: DrawerLayout? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val ft = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_devices -> {
                if (supportFragmentManager.findFragmentByTag("DevicesFragment") != null) {

                }
                hideFiltersToolbar(View.VISIBLE)
                val devicesFragment = DevicesFragment.newInstance()
                ft.replace(main_activity_fragment_container, devicesFragment, "DevicesFragment")
                ft.commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_dashboard -> {
                if (supportFragmentManager.findFragmentByTag("DashboardFragment") != null) {
                    return@OnNavigationItemSelectedListener false
                }
                hideFiltersToolbar(View.VISIBLE)
                val dashboardFragment = DashboardFragment.newInstance()
                ft.replace(main_activity_fragment_container, dashboardFragment, "DashboardFragment")
                ft.commit()
                //                    setFragmentObserver(dashboardFragment);
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_server -> {
                if (supportFragmentManager.findFragmentByTag("ServerFragment") != null) {
                    return@OnNavigationItemSelectedListener false
                }
                hideFiltersToolbar(GONE)
                val serverFragment = ServerFragment.newInstance()
                ft.replace(main_activity_fragment_container, serverFragment, "ServerFragment")
                ft.commit()
                //                    setFragmentObserver(serverFragment);
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_users -> {
                if (supportFragmentManager.findFragmentByTag("UserFragment") != null) {
                    return@OnNavigationItemSelectedListener false
                }
                hideFiltersToolbar(GONE)
                val usersFragment = UsersFragment.newInstance()
                ft.replace(main_activity_fragment_container, usersFragment, "UserFragment")
                ft.commit()
                //                    setFragmentObserver(UsersFragment.newInstance());
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // -- Interface methods
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.server_name_preference)) {
            Logger.log(LOG_TAG, "Active server changed..Reloading attached fragments", Log.VERBOSE)
            val fragments = supportFragmentManager.fragments
            for (f in fragments) {
                if (f is BasicFragment) {
                    f.changeServerContent()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_logout -> {
                logout()
                navigationDrawer!!.closeDrawer(GravityCompat.START, true)
                return true
            }
            R.id.drawer_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                navigationDrawer!!.closeDrawer(GravityCompat.START, true)
                return true
            }
            R.id.nav_drawer_add_server -> {
                launchLoginActivity()
                return true
            }
            1000 -> {
                ServerUtility.setActiveServer(item.title.toString())
                setNavigationDrawer()
                navigationDrawer!!.closeDrawer(GravityCompat.START, true)
                return true
            }
        }
        return false
    }

    // OnClick avatar click
    fun setUserLogo(v: View) {
        //TODO: display dialog to change user logo
    }

    override fun itemCLicked(serverParcel: Parcelable) {
        val intent = Intent(this, ServerDetailsActivity::class.java)
        intent.putExtra(McContract.DEPLOYMENT_SERVER_TABLE_NAME, serverParcel)
        startActivity(intent)
    }

    override fun onDeviceSelected(devId: String, devName: String) {
        this.devId = devId
        this.devName = devName
        startDetailsActivity(devId, devName)
    }


    // -- Life cycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filtersToolbar = findViewById(R.id.filters_toolbar)

        if (savedInstanceState != null && savedInstanceState.containsKey(TOOLBAR_FILTER_STATUS)) {
            filtersToolbar!!.visibility = savedInstanceState.getInt(TOOLBAR_FILTER_STATUS)
        }
        if (!BuildConfig.DEBUG) {
            mLicenceCheckHandler = Handler()
            mLicenseCheckerCallback = LicenceCheckerCallback()
            mLicenceChecker = LicenseChecker(this,
                    ServerManagedPolicy(this,
                            AESObfuscator(SALT, applicationContext.packageName, Settings.Secure.ANDROID_ID)),
                    getString(R.string.app_billing_public_key)
            )

            checkLicence()
        }

        progressBar = findViewById(R.id.loading_bar)
        setNavigationDrawer()
        setFiltersView()
        setLastSyncTimeView()
        setBottomNavigationView(savedInstanceState)
        setActionBar()


    }

    fun checkLicence() {
        mLicenceChecker!!.checkAccess(mLicenseCheckerCallback)
    }

    private fun setActionBar() {
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setTitle(R.string.app_name)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigationDrawer!!.openDrawer(GravityCompat.START, true)
                return true
            }
            R.id.main_activity_search_button -> {
                item.expandActionView()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(SYNC_DONE_BROADCAST_ACTION)
        intentFilter.addAction(UPDATE_LOADING_BAR_ACTION)
        this.registerReceiver(syncReceiver, intentFilter)
        if (!BuildConfig.DEBUG) {
            checkLicence()
        }
        getSharedPreferences(getString(R.string.server_shared_preference), Context.MODE_PRIVATE)
                .registerOnSharedPreferenceChangeListener(this)

    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(syncReceiver)
        getSharedPreferences(getString(R.string.server_shared_preference), Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (TABLET_MODE && devName != null && devId != null) {
            outState.putString(DEVICE_NAME_EXTRA_KEY, devName)
            outState.putString(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId)
        }
        outState.putInt(TOOLBAR_FILTER_STATUS, filtersToolbar!!.visibility)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLicenceChecker != null) {
            mLicenceChecker!!.onDestroy()
        }
    }

    private fun syncDevicesNow() {
        Logger.log(LOG_TAG, "Immediate device syc manually requested... ", Log.VERBOSE)
        val accountManager = AccountManager.get(applicationContext)
        val account = accountManager.getMcAccount()
        val b = Bundle()
        b.putBoolean(SyncService.SYNC_DEVICES, true)
        SyncService.syncImmediately(account, b)
    }

    private fun refreshToken() {
        val accountManager = AccountManager.get(applicationContext)
        val account = accountManager.getMcAccount()
        val token = accountManager.peekAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY))
        accountManager.invalidateAuthToken(getString(R.string.MC_account_type), token)
        accountManager.getAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY), null, LoginActivity(), null, null)
        Logger.log(LOG_TAG, "Token refresh manually forced", Log.VERBOSE)
    }

    private fun invalidateToken() {
        val accountManager = AccountManager.get(applicationContext)
        val account = accountManager.getMcAccount()
        val token = accountManager.peekAuthToken(account, accountManager.getUserData(account, AUTH_TOKEN_TYPE_KEY))
        accountManager.invalidateAuthToken(getString(R.string.MC_account_type), token)
        Logger.log(LOG_TAG, "Token manually invalidated", Log.VERBOSE)

    }

    private fun startDetailsActivity(devId: String, devName: String) {
        val intent = Intent(this, DeviceDetailsActivity::class.java)
        intent.putExtra(DEVICE_NAME_EXTRA_KEY, devName)
        intent.putExtra(DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY, devId)
        startActivity(intent)
    }

    private fun setFiltersView() {
        //        filtersRecycler = filtersToolbar.findViewById(R.id.filters_recycler);
        //        View emptyView = filtersToolbar.findViewById(R.id.filters_recycler_empty_view);
        //        filtersRecycler.setEmptyView(emptyView);
    }


    private fun hideFiltersToolbar(setVisibility: Int) {
        if (filtersToolbar != null && filtersToolbar!!.visibility != setVisibility) {
            var translation = (-filtersToolbar!!.height).toFloat()
            if (setVisibility == View.VISIBLE) {
                translation = 0f
            }

            filtersToolbar!!.animate().translationY(translation).setDuration(80)
                    .setStartDelay(100).setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            if (setVisibility == View.VISIBLE) {
                                filtersToolbar!!.visibility = setVisibility
                            }
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            if (setVisibility == GONE) {
                                filtersToolbar!!.visibility = GONE
                            }
                        }

                        override fun onAnimationCancel(animation: Animator) {

                        }

                        override fun onAnimationRepeat(animation: Animator) {

                        }
                    }).start()
        }
    }

    private fun setNavigationDrawer() {
        navigationDrawer = findViewById(R.id.main_activity_drawer_layout)
        drawerNavigationView = navigationDrawer!!.findViewById(R.id.drawer_nav_view)

        try {
            val headerView = drawerNavigationView!!.getHeaderView(0)
            (headerView.findViewById<View>(R.id.nav_user_name_text_view) as TextView).text =
                    AccountManager.get(applicationContext).getMcAccount().name
            (headerView.findViewById<View>(R.id.nav_server_text_view) as TextView).text = ServerUtility.getActiveServer().serverName
            (headerView.findViewById<View>(R.id.nav_user_icon) as ImageView)
                    .setImageDrawable(UserUtility.getUserLogo())
        } catch (e: ServerNotFound) {
            e.printStackTrace()
            LoginActivity.launchActivity()
        }

        drawerNavigationView!!.setNavigationItemSelectedListener(this)

    }

    // Navigation Drawer -> on server text view click toggle menu
    fun toggleMenu(view: View) {
        val arrowIcon = drawerNavigationView!!.findViewById<View>(R.id.nav_server_selector_icon)
        drawerNavigationView!!.headerCount
        if (drawerNavigationView!!.menu.findItem(R.id.drawer_logout).isVisible) {
            arrowIcon.animate().rotationBy(180f).setDuration(300L).setInterpolator(DecelerateInterpolator()).start()
            drawerNavigationView!!.menu.clear()
            drawerNavigationView!!.inflateMenu(R.menu.drawer_settings)
            drawerNavigationView!!.menu.setGroupVisible(R.id.nav_drawer_server_list_group, true)
            drawerNavigationView!!.menu.setGroupVisible(R.id.nav_drawer_general_settings_group, false)

            val instances = ServerUtility.getAllInstances()

            val menu = drawerNavigationView!!.menu
            var item: MenuItem
            for ((serverName) in instances) {
                item = menu.add(R.id.nav_drawer_server_list_group, 1000, Menu.NONE, serverName)
                item.setIcon(R.drawable.ic_server_black_24dp)
            }
        } else {
            arrowIcon.animate().rotationBy(180f).setDuration(300L).setInterpolator(DecelerateInterpolator()).start()
            drawerNavigationView!!.menu.clear()
            drawerNavigationView!!.inflateMenu(R.menu.drawer_settings)
            drawerNavigationView!!.menu.setGroupVisible(R.id.nav_drawer_server_list_group, false)
            drawerNavigationView!!.menu.setGroupVisible(R.id.nav_drawer_general_settings_group, true)
        }
    }


    private fun setLastSyncTimeView() {
        lastSyncTimeView = filtersToolbar!!.findViewById(R.id.last_sync_view)
        val preferences = applicationContext
                .getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_MULTI_PROCESS)

        val lastSync = preferences.getLong(getString(R.string.last_dev_sync_pref), 0)
        updateSyncTimeView(lastSync)
    }

    private fun setBottomNavigationView(savedInstanceState: Bundle?) {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.navigation_dashboard
        }
    }

    private fun updateSyncTimeView(syncTime: Long) {
        val labels = resources.getStringArray(R.array.last_sync_labels)
        var label = labels[4]

        if (lastSyncTimeView == null) {
            return
        }
        try {
            if (syncTime == 0L) {
                return
            }
            val currentTime = Calendar.getInstance().time.time
            val diff = currentTime - syncTime
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val elapsedDays = diff / daysInMilli

            if (elapsedDays > 0) {
                label = labels[3]
                label = String.format(label, elapsedDays)
                return
            }
            val elapsedHours = diff / hoursInMilli
            if (elapsedHours > 0) {
                label = labels[2]
                label = String.format(label, elapsedHours)
                return
            }
            val elapsedMinutes = diff / minutesInMilli
            if (elapsedMinutes > 0) {
                label = labels[1]
                label = String.format(label, elapsedMinutes)
            } else if (elapsedDays == 0L) {
                label = labels[0]
            }
        } finally {
            lastSyncTimeView!!.text = label
        }

    }

    private fun logout() {
        // TODO: optimize
        val instances = ServerUtility.getAllInstances()
        val selectionArgs = arrayOfNulls<String>(instances.size)
        var selection = ""
        for (i in instances.indices) {
            selection = selection + (McContract.ServerInfo.NAME + "=? OR ")
            selectionArgs[i] = instances[i].serverName
        }
        selection = selection.substring(0, selection.length - 3)

        val c = contentResolver.query(McContract.ServerInfo.CONTENT_URI, arrayOf(McContract.ServerInfo._ID),
                selection, selectionArgs, null)
        if (c == null || !c.moveToFirst()) {
            return
        }
        do {
            val serverId = c.getInt(0)
            ServerUtility.deleteServer(serverId)
        } while (c.moveToNext())
        c.close()
        UserUtility.clearUserPreferences(AccountManager.get(applicationContext).getMcAccount().name)
        AccountManager.get(this).removeAccount(UserUtility.getMcAccount(), null, null)
        launchLoginActivity()
        ServerUtility.deactivateServer()
        finish()

    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 100)
    }

    private fun calculateDelta(max: Int, units: Int): Int {
        return max / (units * 3)
    }

    private fun displayDialog(showRetry: Boolean) {
        mLicenceCheckHandler!!.post {
            val dialog = LicenceErrorDialog.newInstance(showRetry)
            dialog.show(supportFragmentManager, "licenceError")
        }
    }

    private inner class LicenceCheckerCallback : LicenseCheckerCallback {

        override fun allow(reason: Int) {
            //Do nothing licence is valid
            Logger.log(LOG_TAG, "Valid installation licence: $reason", Log.VERBOSE)
        }

        override fun dontAllow(reason: Int) {
            Logger.log(LOG_TAG, "Invalid installation licence: $reason", Log.ERROR)
            if (isFinishing) {
                return
            }
            if (reason == Policy.RETRY) {
                displayDialog(true)
                return
            }
            displayDialog(false)
        }

        override fun applicationError(errorCode: Int) {
            Logger.log(LOG_TAG, "Application error verifying installation licence:$errorCode", Log.ERROR)
        }

    }

    companion object {
        val SYNC_DONE_BROADCAST_ACTION = "com.mdmobile.cyclops.SYNC_DONE"
        val UPDATE_LOADING_BAR_ACTION = "com.mdmobile.cyclops.UPDATE_LOADING_BAR"
        val UPDATE_LOADING_BAR_ACTION_COUNT = "com.mdmobile.cyclops.UPDATE_LOADING_BAR_ACTIONS"
        private val LOG_TAG = MainActivity::class.java.simpleName
        private val TOOLBAR_FILTER_STATUS = "FILTER_TOOLBAR_VISIBILITY"
        private val SALT = byteArrayOf(-56, 45, 35, -128, -3, -57, 74, -64, 51, 88, -95, -45, 77, -17, -36, -113, -111, 13, -54, 99)
        var TABLET_MODE = GeneralUtility.isTabletMode(applicationContext)
    }
}
