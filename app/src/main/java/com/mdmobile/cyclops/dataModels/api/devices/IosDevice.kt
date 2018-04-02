package com.mdmobile.cyclops.dataModels.api.devices

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import com.mdmobile.cyclops.provider.McContract
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

/**
 * Represent gson class for iOS device
 */

@SuppressLint("ParcelCreator")
class IosDevice : BasicDevice, IDevice<IosDevice> {
    val AgentVersion: String
    val BluetoothMACAddress: String
    val BuildVersion: String
    val CarrierSettingsVersion: String
    val CellularCarrier: String
    val CurrentMCC: String
    val CurrentMNC: String
    val FirmwareVersion: String
    val CellularTechnology: String
    val ExchangeStatus: String
    val ICCID: String
    val IMEI_MEID_ESN: String
    val Ipv6: String
    val LastCheckInTime: String
    val LastAgentConnectTime: String
    val LastAgentDisconnectTime: String
    val LastLoggedOnUser: String
    val LastStatusUpdate: String
    val ManufacturerSerialNumber: String
    val ModelNumber: String
    val ModemFirmwareVersion: String
    val PersonalizedName: String
    val PhoneNumber: String
    val ProductName: String
    val SimCarrierNetwork: String
    val SubscriberMCC: String
    val SubscriberMNC: String
    val SubscriberNumber: String
    val UserIdHash: String
    val PasscodeStatus: String
    val HardwareEncryptionCaps: Int
    val NetworkConnectionType: Int
    val BatteryStatus: Short
    val InRoaming: Boolean
    val DataRoamingEnabled: Boolean
    val IsAgentCompatible: Boolean
    val IsAgentless: Boolean
    val IsDeviceLocatorServiceEnabled: Boolean
    val IsDoNotDisturbInEffect: Boolean
    val IsEncrypted: Boolean
    val IsEnrolled: Boolean
    val IsITunesStoreAccountActive: Boolean
    val IsOSSecure: Boolean
    val IsPersonalHotspotEnabled: Boolean
    val IsSupervised: Boolean
    val PasscodeEnabled: Boolean
    val VoiceRoamingEnabled: Boolean
    val ExchangeBlocked: Boolean

    //Primary constructor
    constructor(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                IsVirtual: Boolean = false, Platform: String = "N/A", AgentVersion: String = "N/A", BluetoothMACAddress: String = "N/A",
                BuildVersion: String = "N/A", CarrierSettingsVersion: String = "N/A", CellularCarrier: String = "N/A", CurrentMCC: String = "N/A",
                CurrentMNC: String = "N/A", FirmwareVersion: String = "N/A", CellularTechnology: String = "N/A", ExchangeStatus: String = "N/A", ICCID: String = "N/A",
                IMEI_MEID_ESN: String = "N/A", Ipv6: String = "N/A", LastCheckInTime: String = "N/A", LastAgentConnectTime: String = "N/A",
                LastAgentDisconnectTime: String = "N/A", LastLoggedOnUser: String = "N/A", LastStatusUpdate: String = "N/A", ManufacturerSerialNumber: String = "N/A",
                ModelNumber: String = "N/A", ModemFirmwareVersion: String = "N/A", PersonalizedName: String = "N/A", PhoneNumber: String = "N/A",
                ProductName: String = "N/A", SimCarrierNetwork: String = "N/A", SubscriberMCC: String = "N/A", SubscriberMNC: String = "N/A",
                SubscriberNumber: String = "N/A", UserIdHash: String = "N/A", PasscodeStatus: String = "N/A", HardwareEncryptionCaps: Int = -1,
                NetworkConnectionType: Int = -1, BatteryStatus: Short = -1, InRoaming: Boolean = false, DataRoamingEnabled: Boolean = false,
                IsAgentCompatible: Boolean = false, IsAgentless: Boolean = false,
                IsDeviceLocatorServiceEnabled: Boolean = false, IsDoNotDisturbInEffect: Boolean = false,
                IsEncrypted: Boolean = false, IsEnrolled: Boolean = false, IsITunesStoreAccountActive: Boolean = false,
                IsOSSecure: Boolean = false, IsPersonalHotspotEnabled: Boolean = false, IsSupervised: Boolean = false,
                PasscodeEnabled: Boolean = false, VoiceRoamingEnabled: Boolean = false, ExchangeBlocked: Boolean = false)
            : super(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform) {
        this.AgentVersion = AgentVersion
        this.BluetoothMACAddress = BluetoothMACAddress
        this.BuildVersion = BuildVersion
        this.CarrierSettingsVersion = CarrierSettingsVersion
        this.CellularCarrier = CellularCarrier
        this.CurrentMCC = CurrentMCC
        this.CurrentMNC = CurrentMNC
        this.FirmwareVersion = FirmwareVersion
        this.CellularTechnology = CellularTechnology
        this.ExchangeStatus = ExchangeStatus
        this.ICCID = ICCID
        this.IMEI_MEID_ESN = IMEI_MEID_ESN
        this.Ipv6 = Ipv6
        this.LastCheckInTime = LastCheckInTime
        this.LastAgentConnectTime = LastAgentConnectTime
        this.LastAgentDisconnectTime = LastAgentDisconnectTime
        this.LastLoggedOnUser = LastLoggedOnUser
        this.LastStatusUpdate = LastStatusUpdate
        this.ManufacturerSerialNumber = ManufacturerSerialNumber
        this.ModelNumber = ModelNumber
        this.ModemFirmwareVersion = ModemFirmwareVersion
        this.PersonalizedName = PersonalizedName
        this.PhoneNumber = PhoneNumber
        this.ProductName = ProductName
        this.SimCarrierNetwork = SimCarrierNetwork
        this.SubscriberMCC = SubscriberMCC
        this.SubscriberMNC = SubscriberMNC
        this.SubscriberNumber = SubscriberNumber
        this.UserIdHash = UserIdHash
        this.PasscodeStatus = PasscodeStatus
        this.HardwareEncryptionCaps = HardwareEncryptionCaps
        this.NetworkConnectionType = NetworkConnectionType
        this.BatteryStatus = BatteryStatus
        this.InRoaming = InRoaming
        this.DataRoamingEnabled = DataRoamingEnabled
        this.IsAgentCompatible = IsAgentCompatible
        this.IsAgentless = IsAgentless
        this.IsDeviceLocatorServiceEnabled = IsDeviceLocatorServiceEnabled
        this.IsDoNotDisturbInEffect = IsDoNotDisturbInEffect
        this.IsEncrypted = IsEncrypted
        this.IsEnrolled = IsEnrolled
        this.IsITunesStoreAccountActive = IsITunesStoreAccountActive
        this.IsOSSecure = IsOSSecure
        this.IsPersonalHotspotEnabled = IsPersonalHotspotEnabled
        this.IsSupervised = IsSupervised
        this.PasscodeEnabled = PasscodeEnabled
        this.VoiceRoamingEnabled = VoiceRoamingEnabled
        this.ExchangeBlocked = ExchangeBlocked
    }

    //Secondary constructor
    constructor(c: Cursor) : super(c) {
        val extra = c.getString(24);
        this.AgentVersion = "N/A"
        this.BluetoothMACAddress = "N/A"
        this.BuildVersion = "N/A"
        this.CarrierSettingsVersion = "N/A"
        this.CellularCarrier = "N/A"
        this.CurrentMCC = "N/A"
        this.CurrentMNC = "N/A"
        this.FirmwareVersion = "N/A"
        this.CellularTechnology = "N/A"
        this.ExchangeStatus = "N/A"
        this.ICCID = "N/A"
        this.IMEI_MEID_ESN = "N/A"
        this.Ipv6 = "N/A"
        this.LastCheckInTime = "N/A"
        this.LastAgentConnectTime = "N/A"
        this.LastAgentDisconnectTime = "N/A"
        this.LastLoggedOnUser = "N/A"
        this.LastStatusUpdate = "N/A"
        this.ManufacturerSerialNumber = "N/A"
        this.ModelNumber = "N/A"
        this.ModemFirmwareVersion = "N/A"
        this.PersonalizedName = "N/A"
        this.PhoneNumber = "N/A"
        this.ProductName = "N/A"
        this.SimCarrierNetwork = "N/A"
        this.SubscriberMCC = "N/A"
        this.SubscriberMNC = "N/A"
        this.SubscriberNumber = "N/A"
        this.UserIdHash = "N/A"
        this.PasscodeStatus = "N/A"
        this.HardwareEncryptionCaps = -1
        this.NetworkConnectionType = -1
        this.BatteryStatus = -1
        this.InRoaming = false
        this.DataRoamingEnabled = false
        this.IsAgentCompatible = false
        this.IsAgentless = false
        this.IsDeviceLocatorServiceEnabled = false
        this.IsDoNotDisturbInEffect = false
        this.IsEncrypted = false
        this.IsEnrolled = false
        this.IsITunesStoreAccountActive = false
        this.IsOSSecure = false
        this.IsPersonalHotspotEnabled = false
        this.IsSupervised = false
        this.PasscodeEnabled = false
        this.VoiceRoamingEnabled = false
        this.ExchangeBlocked = false
    }

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

    override fun toContentValues(): ContentValues {
        val values = super.toContentValues()
        val stringBuilder = StringBuilder()
        this::class.declaredMemberProperties.forEach {
            if (it.visibility == KVisibility.PUBLIC) {
                stringBuilder.append(it.name).append("=")
                        .append(it.getter.call(this).toString())
                        .append(";")
            }
        }
        values.put(McContract.Device.COLUMN_EXTRA_INFO, stringBuilder.toString())
        return values
    }

}
