package com.mdmobile.pocketconsole.dataModels.api.devices

import com.mdmobile.pocketconsole.dataTypes.DeviceAttributes.AndroidPlusDevice.SupportedApis

/**
 * Represent Gson class for android Plus Device
 */

open class AndroidPlus(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                       Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                       Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                       ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                       IsVirtual: Boolean = false, Platform: String = "N/A", val AgentVersion: String = "N/A", val HardwareSerialNumber: String = "N/A",
                       val HardwareVersion: String = "N/A", val IMEI_MEID_ESN: String = "N/A", val CellularCarrier: String = "N/A",
                       val LastLoggedOnUser: String = "N/A", val NetworkBSSID: String = "N/A", val Ipv6: String = "N/A", val NetworkSSID: String = "N/A",
                       val OEMVersion: String = "N/A", val PhoneNumber: String = "N/A", val SubscriberNumber: String = "N/A",
                       val PasscodeStatus: String = "N/A", val SupportedApis: Array<String> = arrayOf(), val ExchangeStatus: String = "N/A",
                       val LastCheckInTime: String = "N/A", val LastAgentConnectTime: String = "N/A", val LastAgentDisconnectTime: String = "N/A",
                       val InRoaming: Boolean = false, val AndroidDeviceAdmin: Boolean = false,
                       val CanResetPassword: Boolean = false, val ExchangeBlocked: Boolean = false,
                       val IsAgentCompatible: Boolean = false, val IsAgentless: Boolean = false,
                       val IsEncrypted: Boolean = false, val IsOSSecure: Boolean = false, val PasscodeEnabled: Boolean = false,
                       val BatteryStatus: Short = -1, val CellularSignalStrength: Int = -1, val NetworkConnectionType: Int = -1,
                       val NetworkRSSI: Int = -1, val HardwareEncryptionCaps: Int = -1)
    : BasicDevice(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
        OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform), IDevice<AndroidPlus>{

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

    val supportedApis: String
        get() {
            return SupportedApis.toString()
        }

    override fun getDevice(): AndroidPlus {
        return this
    }
}

