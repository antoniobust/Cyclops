package com.mdmobile.pocketconsole.dataModels.api.devices

import android.content.ContentValues
import kotlin.reflect.full.instanceParameter

/**
 * Represent Gson class for android Plus Device
 */

open class AndroidPlusDevice(Kind: String, DeviceId: String, DeviceName: String, EnrollmentTime: String, Family: String,
                             HostName: String, MACAddress: String, Manufacturer: String, Mode: String, Model: String,
                             OSVersion: String, Path: String, ComplianceStatus: Boolean, isAgentOnline: Boolean,
                             IsVirtual: Boolean, val AgentVersion: String, val HardwareSerialNumber: String,
                             val HardwareVersion: String, val IMEI_MEID_ESN: String, val CellularCarrier: String,
                             val LastLoggedOnUser: String, val NetworkBSSID: String, val Ipv6: String, val NetworkSSID: String,
                             val OemVersion: String, val PhoneNumber: String, val SubscriberNumber: String,
                             val PasscodeStatus: String, val SupportedApis: Array<String>, val ExchangeStatus: String,
                             val LastCheckInTime: String, val LastAgentConnectTime: String, val LastAgentDisconnectTime: String,
                             val InRoaming: Boolean, val AndroidDeviceAdmin: Boolean,
                             val CanResetPassword: Boolean, val ExchangeBlocked: Boolean,
                             val IsAgentCompatible: Boolean, val IsAgentless: Boolean,
                             val IsEncrypted: Boolean, val IsOSSecure: Boolean, val PasscodeEnabled: Boolean,
                             val batteryStatus: Short, val cellularSignalStrength: Int, val networkConnectionType: Int,
                             val networkRSSI: Int, val hardwareEncryptionCaps: Int, Platform: String)
    : BasicDevice(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
        OSVersion, Path, ComplianceStatus, isAgentOnline, IsVirtual, Platform) {

    //TODO: Support following info
//    Array	Antivirus
//    Array	CustomData
//    DeviceTerms	DeviceTerms
//    DeviceUser	DeviceUserInfo
//    DeviceIntegratedApplication[]	IntegratedApplications

    private val isInRoaming: Int
        get() = if (InRoaming) 1 else 0

    private val isAndroidDeviceAdmin: Int
        get() = if (AndroidDeviceAdmin) 1 else 0

    private val isCanResetPassword: Int
        get() = if (CanResetPassword) 1 else 0

    private val isExchangeBlocked: Int
        get() = if (ExchangeBlocked) 1 else 0

    private val isAgentCompatible: Int
        get() = if (IsAgentCompatible) 1 else 0

    private val isAgentless: Int
        get() = if (IsAgentless) 1 else 0

    private val isEncrypted: Int
        get() = if (IsEncrypted) 1 else 0

    private val isOSSecure: Int
        get() = if (IsOSSecure) 1 else 0

    private val isPasscodeEnabled: Int
        get() = if (PasscodeEnabled) 1 else 0

    override fun toContentValues():ContentValues{
        //TODO: Add extra info
        return super.toContentValues()
    }


}
