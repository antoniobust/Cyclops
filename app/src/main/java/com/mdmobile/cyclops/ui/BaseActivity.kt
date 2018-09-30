package com.mdmobile.cyclops.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.mdmobile.cyclops.apiManager.ApiRequestManager.API_AUTH_ERROR
import com.mdmobile.cyclops.ui.logIn.LoginActivity
import com.mdmobile.cyclops.utils.ServerUtility
import com.mdmobile.cyclops.utils.UserUtility
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Every Activity should extend this base Activity
 * It handles:
 * 1. checks for account validity
 * 2. API Authentication error receiver
 */

open class BaseActivity : AppCompatActivity() {

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(API_AUTH_ERROR)) {
                if (loading_bar != null) {
                    loading_bar.progress = 0
                    loading_bar.visibility = View.INVISIBLE
                }
                Toast.makeText(applicationContext, "Request couldn't be authorized\n Please check your user name & password " +
                        "and " + intent?.getStringExtra("srvName") + " instance information", Toast.LENGTH_LONG).show()
            }
        }
    }


    //Extension function to add find view by id in Activity
    fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> {
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        if (!checkActiveUser() || !checkActiveServer()) {
            redirectToLoginPage()
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(API_AUTH_ERROR)
        this.registerReceiver(broadCastReceiver, intentFilter)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(broadCastReceiver)
    }

    private fun checkActiveUser(): Boolean {
        return UserUtility.checkAnyUserLogged()
    }

    private fun checkActiveServer(): Boolean {
        return ServerUtility.anyActiveServer()
    }

    private fun redirectToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
