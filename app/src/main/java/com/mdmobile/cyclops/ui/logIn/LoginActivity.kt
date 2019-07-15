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
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mdmobile.cyclops.CyclopsApplication.Companion.applicationContext
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.User
import com.mdmobile.cyclops.dataModel.api.Token
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataTypes.ResourceStatus
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.ACCOUNT_TYPE_KEY
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.ADDING_NEW_ACCOUNT_KEY
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.AUTH_TOKEN_EXPIRATION_KEY
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.AUTH_TOKEN_TYPE_KEY
import com.mdmobile.cyclops.services.AccountAuthenticator.Companion.REFRESH_AUTH_TOKEN_KEY
import com.mdmobile.cyclops.sync.SyncService
import com.mdmobile.cyclops.ui.dialogs.HintDialog
import com.mdmobile.cyclops.ui.main.MainActivity
import com.mdmobile.cyclops.util.GeneralUtility
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerUtility
import com.mdmobile.cyclops.util.UserUtility
import com.mdmobile.cyclops.util.UserUtility.PASSWORD_KEY
import com.mdmobile.cyclops.util.UserUtility.USER_NAME_KEY
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.light_bulb.*
import javax.inject.Inject

class LoginActivity : com.mdmobile.cyclops.util.AccountAuthenticatorActivity(),
        View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val logTag = LoginActivity::class.java.simpleName
    lateinit var actionChip: Button
    lateinit var progressBar: ProgressBar
    private var authenticatorResponse: AccountAuthenticatorResponse? = null
    lateinit var viewModel: LoginViewModel

    companion object {
        private const val USER_FRAG_TAG = "USER_FRAG_TAG"
        private const val SERVER_FRAG_TAG = "SERVER_FRAG_TAG"
        fun launchActivity() {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            applicationContext.startActivity(intent)
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.light_bulb_view -> showHelpDialog()
            R.id.action_chip -> buttonChipClick()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        if (requestCode == AddInstanceFragment.EXTERNAL_STORAGE_READ_PERMISSION) {
            if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.log(logTag, android.Manifest.permission.READ_EXTERNAL_STORAGE + " has been granted \n app restart required",
                        Log.INFO)
                (supportFragmentManager.findFragmentByTag(attachedFragmentTag) as AddInstanceFragment).parseServerConfigFile()
            } else {
                Logger.log(logTag, android.Manifest.permission.READ_EXTERNAL_STORAGE + " has been denied", Log.INFO)
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MainActivity.TABLET_MODE = GeneralUtility.isTabletMode(applicationContext)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (!MainActivity.TABLET_MODE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        val instanceObserver = Observer<InstanceInfo> {
            when {
                it == null -> {
                    return@Observer
                }
                (InstanceInfo.instanceNotDefault(it)) -> {
                    actionChip.isEnabled = true
                    return@Observer
                }
                else -> actionChip.isEnabled = false
            }
        }
        val userObserver = Observer<User> {

        }
        viewModel.instanceInfo.observe(this, instanceObserver)
        viewModel.serverInfo.observe(this, Observer {
            if (it != null || (it?.status == ResourceStatus.SUCCESS && it.data != null)) {
                it.data
            }
        })
        viewModel.user.observe(this, userObserver)


        light_bulb_view.setOnClickListener(this)
        actionChip = action_chip
        progressBar = login_progress_view
        actionChip.setOnClickListener(this)

        authenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.login_activity_container, AddInstanceFragment.newInstance(), SERVER_FRAG_TAG).commit()
        }
//
//        if (savedInstanceState != null && savedInstanceState.containsKey(instanceListKey)) {
//            instanceList = savedInstanceState.getParcelableArrayList(instanceListKey)
//        }

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
            accountType = getString(R.string.MC_account_type)
        }


        val account: Account
        val accountManager = AccountManager.get(applicationContext)

        if (newAccount!!) {
            account = Account(userName, accountType)
            accountManager.addAccountExplicitly(account, psw, userInfo)
            accountManager.setPassword(account, psw)
        } else {
            account = accountManager.getAccountsByType(getString(R.string.MC_account_type))[0]
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

    private fun showHelpDialog() {
        HintDialog.newInstance(getString(R.string.api_console_hint)).show(supportFragmentManager, null)
    }

    private fun buttonChipClick() {
        val ft = supportFragmentManager.beginTransaction()
        val instance = viewModel.instanceInfo.value ?: return
        when (attachedFragmentTag) {
            SERVER_FRAG_TAG -> {
                if (activityForResult()) {
                    val resultData = Intent()
                    resultData.putExtra("RESULT", instance)
                    setResult(1, resultData)
                    finish()
                } else {
//                  ServerUtility.setActiveServer(instance)
                    ft.replace(R.id.login_activity_container, LogIngConfigureUserFragment(), USER_FRAG_TAG)
                            .addToBackStack(SERVER_FRAG_TAG)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                }
            }

            USER_FRAG_TAG -> {
//              (supportFragmentManager.findFragmentById(R.id.login_activity_container) as LogIngConfigureUserFragment).getServerInfo()
                viewModel.loginButtonClick()
            }
        }
    }

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

    private fun activityForResult(): Boolean {
        return (callingActivity != null && ServerUtility.anyActiveServer())
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}

