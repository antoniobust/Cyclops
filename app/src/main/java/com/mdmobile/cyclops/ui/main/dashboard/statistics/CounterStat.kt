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

    }

//    fun initPoll() {
//        val count = "COUNT(" + McContract.DEVICE_TABLE_NAME + "." + McContract.Device._ID + ")"
//        val orderBy = "COUNT(?) DESC"
//
//        val (serverName) = ServerUtility.getActiveServer()
//        for (i in 0 until getMProperties().size()) {
//            startQuery(i,
//                    getMProperties().get(i),
//                    McContract.Device.buildUriWithGroup(getMProperties().get(i)),
//                    arrayOf(count, getMProperties().get(i)),
//                    McContract.SERVER_INFO_TABLE_NAME + "." + McContract.ServerInfo.NAME + " = ? ",
//                    arrayOf(serverName),
//                    orderBy.replace("?", getMProperties().get(i)))
//        }
//    }
}

