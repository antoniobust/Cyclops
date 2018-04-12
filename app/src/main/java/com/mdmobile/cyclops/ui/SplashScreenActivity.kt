package com.mdmobile.cyclops.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.ui.logIn.LoginActivity
import com.mdmobile.cyclops.ui.main.MainActivity
import com.mdmobile.cyclops.utils.Logger
import com.mdmobile.cyclops.utils.ServerUtility
import com.mdmobile.cyclops.utils.UserUtility

class SplashScreenActivity : AppCompatActivity() {

    private val LOG_TAG = SplashScreenActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        scheduleSplashScreen()

    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = 1500L
        Handler().postDelayed(
                {
                    val intent: Intent = Intent(Intent.ACTION_VIEW)

                    if (UserUtility.checkAnyUserLoggedIn() && ServerUtility.getServer() != null) {
                        Logger.log(LOG_TAG,"No user/server found, redirecting to login", Log.VERBOSE)
                        intent.setClass(this, MainActivity::class.java)
                    } else {
                        Logger.log(LOG_TAG,"User logged, redirecting to main activity", Log.VERBOSE)
                        intent.setClass(this, LoginActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                },
                splashScreenDuration)
    }

}
