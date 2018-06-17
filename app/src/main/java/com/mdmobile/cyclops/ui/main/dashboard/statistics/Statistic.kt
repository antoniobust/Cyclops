package com.mdmobile.cyclops.ui.main.dashboard.statistics

import android.database.Cursor
import android.os.Bundle
import android.support.annotation.WorkerThread
import android.util.Log
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.utils.LabelHelper
import com.mdmobile.cyclops.utils.Logger

/**
 * Class responsible for creating a new statistic and return statsData from DB
 */

abstract class Statistic constructor(val properties: List<String>) {
    companion object {
        const val COUNTER_STAT = 1
        const val COUNTER_RANGE = 2
    }

    private val maxPopulationSize = 7
    private val logTag = Statistic::class.java.simpleName

    interface OnPollFinished {
        fun IStatisticReady(statId: Int, values: Map<String, Array<StatDataEntry>>)
    }

    private val listeners = ArrayList<OnPollFinished>()

    @WorkerThread
    abstract fun initPoll()

    fun registerPollListener(listener: OnPollFinished) = listeners.add(listener)
    fun unregisterListener(listener: OnPollFinished) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    private fun groupByProperty(devices: List<BasicDevice>, property: String): Map<String, List<StatDataEntry>> {
        val statsEntryList = ArrayList<StatDataEntry>()

        if (LabelHelper.isBasicProperty(property)) {
            Logger.log(logTag, "Grouping by basic property: $property", Log.VERBOSE)
            val valuesMap = devices.groupBy {
                it::class.java.getField(property)
            }
            valuesMap.forEach {
                statsEntryList.add(StatDataEntry(it.key.toString(), it.value.size))
            }
        } else {
            Logger.log(logTag, "Grouping by extra property: $property", Log.VERBOSE)
            devices.forEach {
                val extraInfoBundle = it.extraInfoStringToBundle(it.ExtraInfo)
                it.takeIf {
                    extraInfoBundle.containsKey(property)
                }?.apply {
                    statsEntryList.add(StatDataEntry(property, extraInfoBundle.getInt(property)))
                }
            }
        }
        return mapOf(Pair(property, statsEntryList))
    }

    private fun formatResult(statsMap: Map<String, ArrayList<StatDataEntry>>) {
        //If collection is bigger than 6 different values we will just show "others" with the sum of other entries
        statsMap.forEach {
            if (it.value.size > maxPopulationSize) {
                val other = StatDataEntry("Other", 0)
                for (i in it.value.size downTo 6) {
                    other.value = other.value + it.value[i - 1].value
                    it.value.removeAt(i - 1)
                }
                it.value.add(other)
            }
        }
    }
}