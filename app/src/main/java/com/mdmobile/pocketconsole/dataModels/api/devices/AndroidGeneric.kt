package com.mdmobile.pocketconsole.dataModels.api.devices

/**
 * Represent Gson class for android generic device
 */

class AndroidGeneric(Kind: String = "N/A", deviceId: String = "N/A", deviceName: String = "N/A",
                     enrollmentTime: String = "N/A", family: String = "N/A", hostName: String = "N/A",
                     MACAddress: String = "N/A", manufacturer: String = "N/A", mode: String = "N/A", model: String = "N/A", OSVersion: String = "N/A",
                     path: String = "N/A", complianceStatus: Boolean = false, isAgentOnline: Boolean = false, isVirtual: Boolean = false,
                     Platform: String = "N/A", val agentVersion: String = "N/A", val lastCheckInTime: String = "N/A", val lastAgentConnectTime: String = "N/A",
                     val lastAgentDisconnectTime: String = "N/A", val lastLoggedOnUser: String = "N/A", val networkBSSID: String = "N/A",
                     val exchangeStatus: String = "N/A", val imeI_MEID_ESN: String = "N/A", val ipv6: String = "N/A", val cellularCarrier: String = "N/A",
                     val networkSSID: String = "N/A", val phoneNumber: String = "N/A", val subscriberNumber: String = "N/A", private val AndroidDeviceAdmin: Boolean = false,
                     private val IsAgentCompatible: Boolean = false, private val IsAgentless: Boolean = false, private val IsEncrypted: Boolean = false, private val IsOSSecure: Boolean = false,
                     private val CanResetPassword: Boolean = false, private val ExchangeBlocked: Boolean = false, private val InRoaming: Boolean = false, private val PasscodeEnabled: Boolean = false,
                     val batteryStatus: Short = -1, val cellularSignalStrength: Int = -1, val hardwareEncryptionCaps: Int = -1,
                     val networkConnectionType: Int = -1, val networkRSSI: Int = -1)
    : BasicDevice(Kind, deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
        OSVersion, path, complianceStatus, isAgentOnline, isVirtual, Platform),IDevice<AndroidGeneric> {

    //TODO: support following data
//    Array	Antivirus;
//    Array	CustomData;
//    DeviceTerms	DeviceTerms;
//    DeviceUser	DeviceUserInfo;

    val isAndroidDeviceAdmin: Int
        get() = if (AndroidDeviceAdmin) 1 else 0

    val isAgentCompatible: Int
        get() = if (IsAgentCompatible) 1 else 0

    val isAgentless: Int
        get() = if (IsAgentless) 1 else 0

    val isEncrypted: Int
        get() = if (IsEncrypted) 1 else 0

    val isOSSecure: Int
        get() = if (IsOSSecure) 1 else 0

    val isCanResetPassword: Int
        get() = if (CanResetPassword) 1 else 0

    val isExchangeBlocked: Int
        get() = if (ExchangeBlocked) 1 else 0

    val isInRoaming: Int
        get() = if (InRoaming) 1 else 0

    val isPasscodeEnabled: Int
        get() = if (PasscodeEnabled) 1 else 0

    override fun getDevice(): AndroidGeneric {
        return this
    }
}
