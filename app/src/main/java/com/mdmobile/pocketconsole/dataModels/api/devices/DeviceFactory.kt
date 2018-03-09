package com.mdmobile.pocketconsole.dataModels.api.devices

import android.database.Cursor
import com.mdmobile.pocketconsole.dataTypes.DeviceKind

/**
 * DeviceFactory which returns the right device object given Kind
 */
class DeviceFactory(c: Cursor) {
    companion object {
        fun createDevice(@DeviceKind kind: String): IDevice<*> {
            when (kind) {
                DeviceKind.ANDROID_GENERIC -> return newAndroidGeneric()
                DeviceKind.ANDROID_PLUS -> return newAndroidPlus()
//                DeviceKind.ANDROID_ELM -> return newElm()
                DeviceKind.ANDROID_FOR_WORK -> return newAndroidForWork()
//                DeviceKind.ANDROID_KNOX -> return newKnowDevice()
                DeviceKind.IOS -> return newIos()
//                DeviceKind.MAC -> return newMac()
                DeviceKind.WINDOWS_CE -> return newWindowsCE()
                DeviceKind.WINDOWS_DESKTOP -> return newWindowsDesktop()
                DeviceKind.WINDOWS_DESKTOP_LEGACY -> return newWindowsDesktopLegacy()
                DeviceKind.WINDOWS_PHONE -> return newWindowsPhone()
                DeviceKind.WINDOWS_RUNTIME -> return newWindowsRuntime()
//                DeviceKind.ZEBRA_PRINTER -> return newZebraPrinter()
                else -> {
                    throw UnsupportedOperationException("Non Supported device type: $kind")
                }
            }
        }

//        private fun newZebraPrinter(): IDevice<ZebraPrinter> {
//            return BasicDevice()
//        }

        private fun newWindowsRuntime(): IDevice<WindowsRuntime> {
            return WindowsRuntime()
        }

        private fun newWindowsPhone(): IDevice<WindowsPhone> {
            return WindowsPhone()
        }

        private fun newWindowsDesktopLegacy(): IDevice<WindowsDesktopLegacy> {
            return WindowsDesktopLegacy()
        }

        private fun newWindowsDesktop(): IDevice<WindowsDesktop> {
            return WindowsDesktop()
        }

        private fun newWindowsCE(): IDevice<WindowsCE> {
            return WindowsCE()
        }

//        private fun newMac(): IDevice<MacDevice> {
//            //TODO: support MAC devices
//            return BasicDevice()
//        }

        private fun newIos(): IDevice<IosDevice> {
            return IosDevice()
        }

//        private fun newKnox(): IDevice<Knox> {
//            //TODO: Support KNOX devices
//            return BasicDevice()
//        }

        private fun newAndroidForWork(): IDevice<AndroidPlus> {
            return AndroidForWork()
        }

//        private fun newElm(): IDevice<SamsungElm> {
//            return SamsungElm()
//        }

        private fun newAndroidGeneric(): IDevice<AndroidGeneric> {
            return AndroidGeneric()
        }

        private fun newAndroidPlus(): IDevice<AndroidPlus> {
            return AndroidPlus()
        }
    }

}