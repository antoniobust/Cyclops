package com.mdmobile.cyclops.ui.main.dashboard.statistics

import android.content.AsyncQueryHandler
import android.database.Cursor
import android.os.Bundle
import android.support.annotation.WorkerThread
import android.util.Log
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.utils.LabelHelper
import com.mdmobile.cyclops.utils.Logger

/**
 * Class responsible for creating a new statistic and return statsData from DB
 */

abstract class Statistic constructor(val properties: List<String>) : AsyncQueryHandler(applicationContext.contentResolver) {
    companion object {
        const val COUNTER_STAT = 1
        const val COUNTER_RANGE = 2
        const val MAX_POPULATION_SIZE = 7
    }

    private lateinit var devices : ArrayList<BasicDevice>
    private val logTag = Statistic::class.java.simpleName
    private val statDataList = ArrayList<Pair<String, ArrayList<StatDataEntry>>>()

    interface IStatisticReady {
        fun getStatisticData(statId: Int, values: ArrayList<Pair<String, ArrayList<StatDataEntry>>>)
    }

    private val listeners = ArrayList<IStatisticReady>()

    @WorkerThread
    abstract fun initPoll()

    fun registerPollListener(listener: IStatisticReady) = listeners.add(listener)
    fun unregisterListener(listener: IStatisticReady) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
        if (cursor == null) {
            Logger.log(logTag, "Empty cursor returned, statistic id: $token", Log.ERROR)
            return
        }
        devices = BasicDevice.devicesFromCursor(cursor)
        var statData = groupDataByProperty(devices, cookie.toString())
        statData = formatResult(statData)

        statDataList.add(Pair(cookie.toString(), statData))
        if(statDataList.size == properties.size) {
            listeners.forEach {
                it.getStatisticData(token, statDataList)
            }
        }
    }

    private fun groupDataByProperty(devices: List<BasicDevice>, property: String): ArrayList<StatDataEntry> {
        val statsEntryList = ArrayList<StatDataEntry>()

        if (LabelHelper.isBasicProperty(property)) {
            Logger.log(logTag, "Grouping by basic property: $property", Log.VERBOSE)

            val valuesMap = devices.groupBy {
                val f = it::class.java.getDeclaredField(property)
                f.isAccessible = true
                f.get(it)
            }
            valuesMap.forEach {
                statsEntryList.add(StatDataEntry(it.key.toString(), it.value.size))
            }
        } else {
            Logger.log(logTag, "Grouping by extra property: $property", Log.VERBOSE)
            var extraInfoBundle: Bundle
            val propertyMap: ArrayList<Pair<String, String>> = ArrayList()
            devices.forEach {
                extraInfoBundle = it.extraInfoStringToBundle(it.ExtraInfo)
                it.takeIf {
                    extraInfoBundle.containsKey(property)
                }?.apply {
                    propertyMap.add(Pair(property, extraInfoBundle.getString(property)))
                }
            }
            val valuesMap = propertyMap.groupBy {
                it.second
            }
            valuesMap.forEach {
                statsEntryList.add(StatDataEntry(it.key, it.value.size))
            }
        }
        return statsEntryList
    }

    private fun formatResult(statsDataList: ArrayList<StatDataEntry>): ArrayList<StatDataEntry> {
        //If collection is bigger than 6 different values we will just show "others" with the sum of other entries
        if (statsDataList.size <= MAX_POPULATION_SIZE) {
            return statsDataList
        }
        var sum = 0
        for (i in statsDataList.size downTo MAX_POPULATION_SIZE) {
            sum += statsDataList[i - 1].value
            statsDataList.removeAt(i - 1)
        }
        statsDataList.add(StatDataEntry("Other", sum))

        return statsDataList
    }
}