package com.mdmobile.pocketconsole.dataModels.api.devices

/**
 * Abstraction used in DeviceFactory
 */
interface IDevice<out T> {
    fun getDevice(): T
}