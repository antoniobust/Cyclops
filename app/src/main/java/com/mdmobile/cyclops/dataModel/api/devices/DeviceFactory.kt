package com.mdmobile.cyclops.dataModel.api.devices

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

        fun createDevice(cursor: Cursor): ArrayList<IDevice<BasicDevice>> {
            if (!cursor.moveToFirst()) {
                throw UnsupportedOperationException("Empty cursor passed into DeviceFactory c: ${cursor.count}")
            }
            val kind = cursor.getString(1)
            return selectDevice(kind, cursor)
        }

        private fun selectDevice(@DeviceKind kind: String, cursor: Cursor): ArrayList<IDevice<BasicDevice>> {
            val devices = ArrayList<IDevice<BasicDevice>>()

            do {
                with(devices) {
                    when (kind) {
                        DeviceKind.ANDROID_GENERIC -> add(AndroidGeneric(cursor))
                        DeviceKind.ANDROID_PLUS -> add(AndroidPlus(cursor))
                        DeviceKind.ANDROID_ELM -> add(AndroidPlus(cursor))
                        DeviceKind.ANDROID_FOR_WORK -> add(AndroidForWork(cursor))
                        DeviceKind.ANDROID_KNOX -> add(AndroidPlus(cursor))
                        DeviceKind.IOS_V14 -> add(IosDeviceV14(cursor))
                        DeviceKind.IOS -> add(IosDevice(cursor))
//                DeviceKind.MAC -> add( newMac(cursor))
                        DeviceKind.WINDOWS_CE -> add(WindowsCE(cursor))
                        DeviceKind.WINDOWS_DESKTOP -> add(WindowsDesktop(cursor))
                        DeviceKind.WINDOWS_DESKTOP_LEGACY -> add(WindowsDesktopLegacy(cursor))
                        DeviceKind.WINDOWS_PHONE -> add(WindowsPhone(cursor))
                        DeviceKind.WINDOWS_RUNTIME -> add(WindowsRuntime(cursor))
//                DeviceKind.ZEBRA_PRINTER -> add( newZebraPrinter(cursor))
                        DeviceKind.LINUX -> add(Linux(cursor))
                        DeviceKind.WINDOWS_HOLO_LENS -> add(Linux(cursor))
                        else -> {
                            throw UnsupportedOperationException("Non Supported device type: $kind")
                        }
                    }
                }
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
            return devices
        }


        private fun selectDevice(@DeviceKind kind: String): IDevice<*> {
            when (kind) {
                DeviceKind.ANDROID_GENERIC -> return AndroidGeneric()
                DeviceKind.ANDROID_PLUS -> return AndroidPlus()
                DeviceKind.ANDROID_ELM -> return AndroidPlus()
                DeviceKind.ANDROID_FOR_WORK -> return AndroidForWork()
                DeviceKind.ANDROID_KNOX -> return AndroidPlus()
                DeviceKind.IOS_V14 -> return IosDevice()
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