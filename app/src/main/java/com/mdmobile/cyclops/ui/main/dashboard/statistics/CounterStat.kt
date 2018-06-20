package com.mdmobile.cyclops.ui.main.dashboard.statistics

import android.content.AsyncQueryHandler
import android.content.ContentResolver

import com.mdmobile.cyclops.dataModel.Server
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.utils.ServerUtility

/**
 * Counter statistic, given a property groups the device per property value and counts
 * elements
 */

class CounterStat constructor(properties: List<String>)
    : Statistic(properties) {

    override fun initPoll() {
                val count = McContract.Device.FULL_PROJECTION
//        + " , COUNT(" + McContract.DEVICE_TABLE_NAME + "." + McContract.Device._ID + ")"
        val orderBy = "COUNT(?) DESC"
        val serverName = ServerUtility.getActiveServer().serverName

        for (i in 0 until properties.size) {
            startQuery(i,
                    properties[i],
                    McContract.Device.buildUriWithServerName(serverName),
                    count, null,null,null)
        }
    }

//    fun initPoll() {

//    }
}

