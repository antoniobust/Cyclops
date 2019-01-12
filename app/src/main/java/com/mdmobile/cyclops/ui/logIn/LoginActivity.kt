package com.mdmobile.cyclops.ui.logIn

import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.android.volley.VolleyError
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.BuildConfig
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.dataModel.api.Token
import com.mdmobile.cyclops.interfaces.NetworkCallBack
import com.mdmobile.cyclops.services.AccountAuthenticator.*
import com.mdmobile.cyclops.sync.SyncService
import com.mdmobile.cyclops.ui.dialogs.HintDialog
import com.mdmobile.cyclops.ui.main.MainActivity
import com.mdmobile.cyclops.utils.GeneralUtility
import com.mdmobile.cyclops.utils.Logger
import com.mdmobile.cyclops.utils.ServerUtility
import com.mdmobile.cyclops.utils.UserUtility
import com.mdmobile.cyclops.utils.UserUtility.PASSWORD_KEY
import com.mdmobile.cyclops.utils.UserUtility.USER_NAME_KEY
import java.net.HttpURLConnection
import java.util.*
import javax.net.ssl.HttpsURLConnection

class LoginActivity : com.mdmobile.cyclops.utils.AccountAuthenticatorActivity(), NetworkCallBack, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {


    private val logTag = LoginActivity::class.java.simpleName
    private val SERVER_FRAG_TAG = "SERVER_FRAG_TAG"
    private val USER_FRAG_TAG = "USER_FRAG_TAG"
    private val instanceListKey = "InstanceList"
    var activityForResult = false
    lateinit var actionChip: Button
    lateinit var progressBar: ProgressBar
    var instanceList: ArrayList<Instance>? = ArrayList()
    private var authenticatorResponse: AccountAuthenticatorResponse? = null

    private val attachedFragmentTag: String
        get() {
            val fragments = supportFragmentManager.fragments
            for (fragment in fragments) {
                if (fragment.isAdded && fragment.isVisible) {
                    return fragment.tag!!
                }
            }
            return SERVER_FRAG_TAG
        }

    // -- Interface methods
    override fun onClick(view: View) {
        when (view.id) {
            R.id.light_bulb_view -> HintDialog.newInstance(getString(R.string.api_console_hint)).show(supportFragmentManager, null)
            R.id.action_chip -> buttonChipClick()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        if (requestCode == AddServerFragment.EXTERNAL_STORAGE_READ_PERMISSION) {
            if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.log(logTag, android.Manifest.permission.READ_EXTERNAL_STORAGE + " has been granted \n app restart required",
                        Log.INFO)
                (supportFragmentManager.findFragmentByTag(attachedFragmentTag) as AddServerFragment).parseServerConfigFile()
            } else {
                Logger.log(logTag, android.Manifest.permission.READ_EXTERNAL_STORAGE + " has been denied", Log.INFO)
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun tokenReceived(userInput: Bundle, response: Token) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(applicationContext, "token received", Toast.LENGTH_SHORT).show()
        }
        (supportFragmentManager.findFragmentByTag(SERVER_FRAG_TAG) as AddServerFragment).saveServer(instanceList)
        finishLogin(userInput, response)
    }

    override fun errorReceivingToken(error: VolleyError?) {
        progressBar.visibility = View.GONE
        actionChip.visibility = View.VISIBLE
        val message: String = if (error?.networkResponse == null) {
            "Login failed...Please try again"
        } else {
            when (error.networkResponse.statusCode) {
                HttpsURLConnection.HTTP_BAD_REQUEST -> "Login failed... Check your credentials"
                HttpsURLConnection.HTTP_FORBIDDEN -> "Login failed... Check your credentials"
                HttpURLConnection.HTTP_INTERNAL_ERROR -> "Internal server error"
                HttpURLConnection.HTTP_NOT_FOUND -> "Server not found"
                else -> "Login failed"
            }
        }
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    // -- Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MainActivity.TABLET_MODE = GeneralUtility.isTabletMode(applicationContext)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (!MainActivity.TABLET_MODE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val hintView = findViewById<ImageView>(R.id.light_bulb_view)
        actionChip = findViewById(R.id.action_chip)
        progressBar = findViewById(R.id.login_progress_view)

        if (savedInstanceState != null && savedInstanceState.containsKey(instanceListKey)) {
            instanceList = savedInstanceState.getParcelableArrayList(instanceListKey)
        }

        if (savedInstanceState == null) {
            //            if (!UserUtility.checkAnyUserLogged() || getCallingActivity() != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.login_activity_container, AddServerFragment.newInstance(), SERVER_FRAG_TAG).commit()
            //            } else {
            //                getSupportFragmentManager().beginTransaction()
            //                        .replace(R.id.login_activity_container, AddNewUserFragment.newInstance(), USER_FRAG_TAG).commit();
            //            }
        }
        authenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)

        hintView.setOnClickListener(this)
        actionChip.setOnClickListener(this)
    }

    override fun onPostCreate(@Nullable savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (callingActivity != null && ServerUtility.anyActiveServer()) {
            activityForResult = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(instanceListKey, instanceList)
    }

    private fun finishLogin(userInput: Bundle, response: Token) {
        //TODO: support multiple account
        val userName: String? = userInput.getString(USER_NAME_KEY)
        var tokenType: String? = null
        var accountType: String? = null
        val psw: String? = userInput.getString(PASSWORD_KEY)
        var newAccount: Boolean? = true
        val userInfo = Bundle()

        userInfo.putInt(AUTH_TOKEN_EXPIRATION_KEY, response.tokenExpiration)
        userInfo.putString(REFRESH_AUTH_TOKEN_KEY, response.refreshToken)

        if (intent.extras != null && intent.hasExtra(AUTH_TOKEN_TYPE_KEY)) {
            tokenType = intent.getStringExtra(AUTH_TOKEN_TYPE_KEY)
            userInfo.putString(AUTH_TOKEN_TYPE_KEY, tokenType)
        }

        if (intent.extras != null && intent.hasExtra(ADDING_NEW_ACCOUNT_KEY)) {
            newAccount = intent.getBooleanExtra(ADDING_NEW_ACCOUNT_KEY, false)
        }
        if (intent.extras != null && intent.hasExtra(ACCOUNT_TYPE_KEY)) {
            accountType = intent.getStringExtra(ACCOUNT_TYPE_KEY)
        }

        if (tokenType == null || tokenType == "") {
            tokenType = response.token_type
            userInfo.putString(AUTH_TOKEN_TYPE_KEY, tokenType)
        }
        if (accountType == null || tokenType == "") {
            accountType = getString(R.string.account_type)
        }


        val account: Account
        val accountManager = AccountManager.get(applicationContext)

        if (newAccount!!) {
            account = Account(userName, accountType)
            accountManager.addAccountExplicitly(account, psw, userInfo)
            accountManager.setPassword(account, psw)
        } else {
            account = accountManager.getAccountsByType(getString(R.string.account_type))[0]
            accountManager.setPassword(account, psw)
            UserUtility.updateUserData(userInfo)
        }
        accountManager.setAuthToken(account, tokenType, response.access_token)
        SyncService.initializeSync(account)

        if (authenticatorResponse != null) {
            setAccountAuthenticatorResult(intent.extras)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            startMainActivity()
        }

    }

    private fun buttonChipClick() {
        val currentFragTag = attachedFragmentTag
        val ft = supportFragmentManager.beginTransaction()
        when (currentFragTag) {
            SERVER_FRAG_TAG -> {
                val f = supportFragmentManager.findFragmentById(R.id.login_activity_container) as AddServerFragment
                val s = f.grabServerInfo()
                if (s != null) {
                    instanceList!!.add(s)
                }
                if (instanceList!!.size == 0) {
                    return
                }
                if (activityForResult) {
                    f.saveServer(instanceList)
                    val resultData = Intent()
                    resultData.putParcelableArrayListExtra("RESULT", instanceList)
                    setResult(1, resultData)
                    finish()
                    return
                } else {
                    ServerUtility.setActiveServer(instanceList!![0])
                    ft.replace(R.id.login_activity_container, AddNewUserFragment(), USER_FRAG_TAG)
                            .addToBackStack(SERVER_FRAG_TAG)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    return
                }
            }
            USER_FRAG_TAG -> {
                (supportFragmentManager.findFragmentById(R.id.login_activity_container) as AddNewUserFragment).logIn()
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ft.commit()
            }
        }


    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    companion object {

        fun launchActivity() {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }
}

