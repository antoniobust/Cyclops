package com.mdmobile.pocketconsole.dataTypes;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ANDROID_ELM;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ANDROID_FOR_WORK;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ANDROID_GENERIC;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ANDROID_KNOX;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ANDROID_PLUS;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.IOS;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.MAC;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.WINDOWS_CE;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.WINDOWS_DESKTOP;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.WINDOWS_DESKTOP_LEGACY;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.WINDOWS_PHONE;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.WINDOWS_RUNTIME;
import static com.mdmobile.pocketconsole.dataTypes.DeviceKind.ZEBRA_PRINTER;


@StringDef({ANDROID_FOR_WORK, ANDROID_ELM, ANDROID_KNOX, ANDROID_PLUS, ANDROID_GENERIC, IOS, MAC,
        WINDOWS_CE, WINDOWS_DESKTOP, WINDOWS_DESKTOP_LEGACY, WINDOWS_PHONE, WINDOWS_RUNTIME, ZEBRA_PRINTER})
@Retention(RetentionPolicy.SOURCE)
public @interface DeviceKind {

    String ANDROID_FOR_WORK = "AndroidForWork";
    String ANDROID_ELM = "AndroidElm";
    String ANDROID_KNOX = "AndroidKnox";
    String ANDROID_PLUS = "AndroidPlus";
    String ANDROID_GENERIC = "AndroidGeneric";
    String IOS = "iOS";
    String MAC = "Mac";
    String WINDOWS_CE = "WindowsCE";
    String WINDOWS_DESKTOP = "WindowsDesktop";
    String WINDOWS_DESKTOP_LEGACY = "WindowsDesktopLegacy";
    String WINDOWS_PHONE = "WindowsPhone";
    String WINDOWS_RUNTIME = "WindowsRuntime";
    String ZEBRA_PRINTER = "ZebraPrinter";
}

