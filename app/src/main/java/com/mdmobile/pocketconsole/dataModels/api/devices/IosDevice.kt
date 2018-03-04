package com.mdmobile.pocketconsole.dataModels.api.devices

import android.annotation.SuppressLint
import android.content.ContentValues

/**
 * Represent gson class for iOS device
 */

@SuppressLint("ParcelCreator")
class IosDevice(Kind: String, DeviceId: String, DeviceName: String, EnrollmentTime: String, Family: String, HostName: String, MACAddress: String, Manufacturer: String, Mode: String, Model: String,
                OSVersion: String, Path: String, ComplianceStatus: Boolean,
                IsAgentOnline: Boolean, IsVirtual: Boolean, val AgentVersion: String, val BluetoothMACAddress: String,
                val BuildVersion: String, val CarrierSettingsVersion: String, val CellularCarrier: String, val CurrentMCC: String,
                val CurrentMNC: String, val FirmwareVersion: String, val CellularTechnology: String, val ExchangeStatus: String, val ICCID: String,
                val IMEI_MEID_ESN: String, val Ipv6: String, val LastCheckInTime: String, val LastAgentConnectTime: String,
                val LastAgentDisconnectTime: String, val LastLoggedOnUser: String, val LastStatusUpdate: String, val ManufacturerSerialNumber: String,
                val ModelNumber: String, val ModemFirmwareVersion: String, val PersonalizedName: String, val PhoneNumber: String,
                val ProductName: String, val SimCarrierNetwork: String, val SubscriberMCC: String, val SubscriberMNC: String,
                val SubscriberNumber: String, val UserIdHash: String, val PasscodeStatus: String, val HardwareEncryptionCaps: Int,
                val NetworkConnectionType: Int, val BatteryStatus: Short, private val InRoaming: Boolean, private val DataRoamingEnabled: Boolean,
                private val IsAgentCompatible: Boolean, private val IsAgentless: Boolean,
                private val IsDeviceLocatorServiceEnabled: Boolean, private val IsDoNotDisturbInEffect: Boolean,
                private val IsEncrypted: Boolean, private val IsEnrolled: Boolean, private val IsITunesStoreAccountActive: Boolean,
                private val IsOSSecure: Boolean, private val IsPersonalHotspotEnabled: Boolean,
                private val IsSupervised: Boolean, private val PasscodeEnabled: Boolean,
                private val VoiceRoamingEnabled: Boolean, private val ExchangeBlocked: Boolean, Platform: String)
    : BasicDevice(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
        OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform) {

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

    override fun toContentValues(): ContentValues {
        return super.toContentValues()
    }
}
