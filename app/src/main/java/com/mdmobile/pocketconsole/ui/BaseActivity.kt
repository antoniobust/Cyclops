package com.mdmobile.pocketconsole.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.mdmobile.pocketconsole.ui.logIn.LoginActivity
import com.mdmobile.pocketconsole.utils.UserUtility

/**
 * Every Activity should extend this base Activity
 * It handles:
 * 1. checks for account validity
 */

open class BaseActivity : AppCompatActivity() {

    //Extension function to add find view by id in Activity
    fun <T : View> Activity.bind(@IdRes id: Int): Lazy<T> {
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (!checkActiveUser()) {
            redirectToLoginPage()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkActiveUser()) {
            redirectToLoginPage()
        }
    }

    private fun checkActiveUser(): Boolean {
        return UserUtility.checkAnyUserLoggedIn()
    }

    private fun redirectToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
