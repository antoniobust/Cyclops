package com.mdmobile.cyclops.dataModels.api.devices

import android.database.Cursor
import com.mdmobile.cyclops.dataTypes.DeviceKind

/**
 * DeviceFactory which returns the right device object given Kind
 */
class DeviceFactory {
    companion object {
        fun createDevice(@DeviceKind kind: String): IDevice<*> {
            return selectDevice(kind)
        }

        fun createDevice(cursor: Cursor): IDevice<BasicDevice> {
            if (!cursor.moveToFirst()) {
                throw UnsupportedOperationException("Empty cursor passed into DeviceFactory c: ${cursor.count}")
            }
            val kind = cursor.getString(1)
            return selectDevice(kind, cursor)
        }

        private fun selectDevice(@DeviceKind kind: String, cursor: Cursor): IDevice<BasicDevice> {
            when (kind) {
                DeviceKind.ANDROID_GENERIC -> return AndroidGeneric(cursor)
                DeviceKind.ANDROID_PLUS -> return AndroidPlus(cursor)
                DeviceKind.ANDROID_ELM -> return AndroidPlus(cursor)
                DeviceKind.ANDROID_FOR_WORK -> return AndroidForWork(cursor)
                DeviceKind.ANDROID_KNOX -> return AndroidPlus(cursor)
                DeviceKind.IOS -> return IosDevice(cursor)
//                DeviceKind.MAC -> return newMac(cursor)
                DeviceKind.WINDOWS_CE -> return WindowsCE(cursor)
                DeviceKind.WINDOWS_DESKTOP -> return WindowsDesktop(cursor)
                DeviceKind.WINDOWS_DESKTOP_LEGACY -> return WindowsDesktopLegacy(cursor)
                DeviceKind.WINDOWS_PHONE -> return WindowsPhone(cursor)
                DeviceKind.WINDOWS_RUNTIME -> return WindowsRuntime(cursor)
//                DeviceKind.ZEBRA_PRINTER -> return newZebraPrinter(cursor)
                else -> {
                    throw UnsupportedOperationException("Non Supported device type: $kind")
                }
            }
        }

        private fun selectDevice(@DeviceKind kind: String): IDevice<*> {
            when (kind) {
                DeviceKind.ANDROID_GENERIC -> return AndroidGeneric()
                DeviceKind.ANDROID_PLUS -> return AndroidPlus()
                DeviceKind.ANDROID_ELM -> return AndroidPlus()
                DeviceKind.ANDROID_FOR_WORK -> return AndroidForWork()
                DeviceKind.ANDROID_KNOX -> return AndroidPlus()
                DeviceKind.IOS -> return IosDevice()
//                DeviceKind.MAC -> return newMac()
                DeviceKind.WINDOWS_CE -> return WindowsCE()
                DeviceKind.WINDOWS_DESKTOP -> return WindowsDesktop()
                DeviceKind.WINDOWS_DESKTOP_LEGACY -> return WindowsDesktopLegacy()
                DeviceKind.WINDOWS_PHONE -> return WindowsPhone()
                DeviceKind.WINDOWS_RUNTIME -> return WindowsRuntime()
//                DeviceKind.ZEBRA_PRINTER -> return newZebraPrinter()
                else -> {
                    throw UnsupportedOperationException("Non Supported device type: $kind")
                }
            }
        }
    }

}