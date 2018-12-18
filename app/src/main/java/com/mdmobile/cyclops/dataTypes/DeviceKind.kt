package com.mdmobile.cyclops.dataTypes

import androidx.annotation.StringDef
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ANDROID_ELM
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ANDROID_FOR_WORK
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ANDROID_GENERIC
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ANDROID_KNOX
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ANDROID_PLUS
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.IOS_V14
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.MAC
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.WINDOWS_CE
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.WINDOWS_DESKTOP
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.WINDOWS_DESKTOP_LEGACY
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.WINDOWS_PHONE
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.WINDOWS_RUNTIME
import com.mdmobile.cyclops.dataTypes.DeviceKind.Companion.ZEBRA_PRINTER


@StringDef(ANDROID_FOR_WORK, ANDROID_ELM, ANDROID_KNOX, ANDROID_PLUS, ANDROID_GENERIC, IOS_V14, MAC, WINDOWS_CE, WINDOWS_DESKTOP, WINDOWS_DESKTOP_LEGACY, WINDOWS_PHONE, WINDOWS_RUNTIME, ZEBRA_PRINTER)
@Retention(AnnotationRetention.SOURCE)
annotation class DeviceKind {
    companion object {
        const val ANDROID_FOR_WORK = "AndroidForWork"
        const val ANDROID_ELM = "AndroidElm"
        const val ANDROID_KNOX = "AndroidKnox"
        const val ANDROID_PLUS = "AndroidPlus"
        const val ANDROID_GENERIC = "AndroidGeneric"
        const val IOS_V14 = "Ios"
        const val IOS = "iOS"
        const val MAC = "Mac"
        const val WINDOWS_CE = "WindowsCE"
        const val WINDOWS_DESKTOP = "WindowsDesktop"
        const val WINDOWS_DESKTOP_LEGACY = "WindowsDesktopLegacy"
        const val WINDOWS_PHONE = "WindowsPhone"
        const val WINDOWS_RUNTIME = "WindowsRuntime"
        const val ZEBRA_PRINTER = "ZebraPrinter"
        const val LINUX = "Linux"
        const val WINDOWS_HOLO_LENS = "WindowsHololens"
    }
}

