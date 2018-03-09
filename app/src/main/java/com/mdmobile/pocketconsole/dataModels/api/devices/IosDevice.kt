package com.mdmobile.pocketconsole.dataModels.api.devices

import android.annotation.SuppressLint

/**
 * Represent gson class for iOS device
 */

@SuppressLint("ParcelCreator")
class IosDevice(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                IsVirtual: Boolean = false, Platform: String = "N/A", val AgentVersion: String = "N/A", val BluetoothMACAddress: String = "N/A",
                val BuildVersion: String = "N/A", val CarrierSettingsVersion: String = "N/A", val CellularCarrier: String = "N/A", val CurrentMCC: String = "N/A",
                val CurrentMNC: String = "N/A", val FirmwareVersion: String = "N/A", val CellularTechnology: String = "N/A", val ExchangeStatus: String = "N/A", val ICCID: String = "N/A",
                val IMEI_MEID_ESN: String = "N/A", val Ipv6: String = "N/A", val LastCheckInTime: String = "N/A", val LastAgentConnectTime: String = "N/A",
                val LastAgentDisconnectTime: String = "N/A", val LastLoggedOnUser: String = "N/A", val LastStatusUpdate: String = "N/A", val ManufacturerSerialNumber: String = "N/A",
                val ModelNumber: String = "N/A", val ModemFirmwareVersion: String = "N/A", val PersonalizedName: String = "N/A", val PhoneNumber: String = "N/A",
                val ProductName: String = "N/A", val SimCarrierNetwork: String = "N/A", val SubscriberMCC: String = "N/A", val SubscriberMNC: String = "N/A",
                val SubscriberNumber: String = "N/A", val UserIdHash: String = "N/A", val PasscodeStatus: String = "N/A", val HardwareEncryptionCaps: Int = -1,
                val NetworkConnectionType: Int = -1, val BatteryStatus: Short = -1, private val InRoaming: Boolean = false, private val DataRoamingEnabled: Boolean = false,
                val IsAgentCompatible: Boolean = false, val IsAgentless: Boolean = false,
                val IsDeviceLocatorServiceEnabled: Boolean = false, val IsDoNotDisturbInEffect: Boolean = false,
                val IsEncrypted: Boolean = false, val IsEnrolled: Boolean = false, val IsITunesStoreAccountActive: Boolean = false,
                val IsOSSecure: Boolean = false, val IsPersonalHotspotEnabled: Boolean = false, val IsSupervised: Boolean = false,
                val PasscodeEnabled: Boolean = false, val VoiceRoamingEnabled: Boolean = false, val ExchangeBlocked: Boolean = false)
    : BasicDevice(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
        OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform), IDevice<IosDevice> {

    //    TODO:Support following info
    //    DeviceTerms	DeviceTerms
    //    DeviceUser	DeviceUserInfo

    private val isInRoaming: Int
        get() = if (InRoaming) 1 else 0

    private val isDataRoamingEnabled: Int
        get() = if (DataRoamingEnabled) 1 else 0

    private val isAgentCompatible: Int
        get() = if (IsAgentCompatible) 1 else 0

    private val isAgentless: Int
        get() = if (IsAgentless) 1 else 0

    private val isDeviceLocatorServiceEnabled: Int
        get() = if (IsDeviceLocatorServiceEnabled) 1 else 0

    private val isDoNotDisturbInEffect: Int
        get() = if (IsDoNotDisturbInEffect) 1 else 0

    private val isEncrypted: Int
        get() = if (IsEncrypted) 1 else 0

    private val isEnrolled: Int
        get() = if (IsEnrolled) 1 else 0

    private val isITunesStoreAccountActive: Int
        get() = if (IsITunesStoreAccountActive) 1 else 0

    private val isOSSecure: Int
        get() = if (IsOSSecure) 1 else 0

    private val isPersonalHotspotEnabled: Int
        get() = if (IsPersonalHotspotEnabled) 1 else 0

    private val isSupervised: Int
        get() = if (IsSupervised) 1 else 0

    private val isPasscodeEnabled: Int
        get() = if (PasscodeEnabled) 1 else 0

    private val isVoiceRoamingEnabled: Int
        get() = if (VoiceRoamingEnabled) 1 else 0

    private val isExchangeBlocked: Int
        get() = if (ExchangeBlocked) 1 else 0

    override fun getDevice(): IosDevice {
        return this
    }

}
