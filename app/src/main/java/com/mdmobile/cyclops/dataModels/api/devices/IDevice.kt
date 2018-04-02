package com.mdmobile.cyclops.dataModels.api.devices

import android.content.ContentValues

/**
 * Abstraction used in DeviceFactory
 */
interface IDevice<out T> {
    fun getDevice(): T
    fun toContentValues():ContentValues
}