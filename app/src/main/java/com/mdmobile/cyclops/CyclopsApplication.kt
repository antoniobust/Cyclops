package com.mdmobile.cyclops


import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mdmobile.cyclops.dataModel.chart.Chart
import com.mdmobile.cyclops.di.ApplicationModules
import com.mdmobile.cyclops.di.DaggerAppComponent
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.ui.main.dashboard.ChartFactory
import com.mdmobile.cyclops.utils.GeneralUtility
import com.mdmobile.cyclops.utils.Logger

import java.lang.reflect.Type
import java.util.ArrayList

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

class CyclopsApplication : Application(), HasActivityInjector {
    @Inject
    internal lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    private val LOG_TAG = CyclopsApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build().inject(this)

        CyclopsApplication.applicationContext = applicationContext
        val firstLoad = applicationContext.getSharedPreferences(getString(R.string.general_shared_preference), Context.MODE_PRIVATE)
                .getBoolean(getString(R.string.first_boot_preference), true)

        if (firstLoad) {
            val defaultChart = ArrayList<Chart>(3)
            defaultChart.add(0, Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_AGENT_ONLINE, "N/A"))
            defaultChart.add(1, Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_MODE, "N/A"))
            defaultChart.add(2, Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_COMPLIANCE_STATUS, "N/A"))

            val deviceCollectionType = object : TypeToken<ArrayList<Chart>>() {

            }.type
            val gson = Gson()
            val json = gson.toJson(defaultChart, deviceCollectionType)
            GeneralUtility.setSharedPreference(applicationContext, getString(R.string.charts_preference), json)
            GeneralUtility.setSharedPreference(applicationContext, getString(R.string.first_boot_preference), false)

            Logger.log(LOG_TAG,
                    "First boot... Added following charts:\n" + json.replace("},{", "}\n{") + "}\n in saved charts",
                    Log.VERBOSE)

        }


        if (Build.VERSION.SDK_INT >= 26) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val id = packageName
            val channelName = getString(R.string.notification_channel_name)
            val description = getString(R.string.notification_channel_description)
            val priority = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(id, channelName, priority)
            notificationChannel.description = description
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingActivityInjector
    }

    companion object {
        @Volatile
        lateinit var applicationContext: Context
    }
}