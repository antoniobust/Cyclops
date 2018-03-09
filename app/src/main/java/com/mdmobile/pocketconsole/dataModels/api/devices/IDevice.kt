package com.mdmobile.pocketconsole.dataModels.api.devices

import android.os.Parcelable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * Abstraction used in DeviceFactory
 */
interface IDevice<out T> {
    fun getDevice() : T
}