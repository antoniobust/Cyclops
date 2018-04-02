package com.mdmobile.cyclops.dataModels.api.devices

import android.content.ContentValues
import android.database.Cursor
import com.mdmobile.cyclops.provider.McContract
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

/**
 * Represent Gson class for android Plus Device
 */

open class AndroidPlus : BasicDevice, IDevice<AndroidPlus> {

    val AgentVersion: String
    val HardwareSerialNumber: String
    val HardwareVersion: String
    val IMEI_MEID_ESN: String
    val CellularCarrier: String
    val LastLoggedOnUser: String
    val NetworkBSSID: String
    val Ipv6: String
    val NetworkSSID: String
    val OEMVersion: String
    val PhoneNumber: String
    val SubscriberNumber: String
    val PasscodeStatus: String
    val SupportedApis: Array<String>
    val ExchangeStatus: String
    val LastCheckInTime: String
    val LastAgentConnectTime: String
    val LastAgentDisconnectTime: String
    val InRoaming: Boolean
    val AndroidDeviceAdmin: Boolean
    val CanResetPassword: Boolean
    val ExchangeBlocked: Boolean
    val IsAgentCompatible: Boolean
    val IsAgentless: Boolean
    val IsEncrypted: Boolean
    val IsOSSecure: Boolean
    val PasscodeEnabled: Boolean
    val BatteryStatus: Short
    val CellularSignalStrength: Int
    val NetworkConnectionType: Int
    val NetworkRSSI: Int
    val HardwareEncryptionCaps: Int

    //Primary Constructor
    constructor(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                IsVirtual: Boolean = false, Platform: String = "N/A", AgentVersion: String = "N/A", HardwareSerialNumber: String = "N/A",
                HardwareVersion: String = "N/A", IMEI_MEID_ESN: String = "N/A", CellularCarrier: String = "N/A",
                LastLoggedOnUser: String = "N/A", NetworkBSSID: String = "N/A", Ipv6: String = "N/A", NetworkSSID: String = "N/A",
                OEMVersion: String = "N/A", PhoneNumber: String = "N/A", SubscriberNumber: String = "N/A",
                PasscodeStatus: String = "N/A", SupportedApis: Array<String> = arrayOf(), ExchangeStatus: String = "N/A",
                LastCheckInTime: String = "N/A", LastAgentConnectTime: String = "N/A", LastAgentDisconnectTime: String = "N/A",
                InRoaming: Boolean = false, AndroidDeviceAdmin: Boolean = false,
                CanResetPassword: Boolean = false, ExchangeBlocked: Boolean = false,
                IsAgentCompatible: Boolean = false, IsAgentless: Boolean = false,
                IsEncrypted: Boolean = false, IsOSSecure: Boolean = false, PasscodeEnabled: Boolean = false,
                BatteryStatus: Short = -1, CellularSignalStrength: Int = -1, NetworkConnectionType: Int = -1,
                NetworkRSSI: Int = -1, HardwareEncryptionCaps: Int = -1)
            : super(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform) {
        this.AgentVersion = AgentVersion
        this.HardwareSerialNumber = HardwareSerialNumber
        this.HardwareVersion = HardwareVersion
        this.IMEI_MEID_ESN = IMEI_MEID_ESN
        this.CellularCarrier = CellularCarrier
        this.LastLoggedOnUser = LastLoggedOnUser
        this.NetworkBSSID = NetworkBSSID
        this.Ipv6 = Ipv6
        this.NetworkSSID = NetworkSSID
        this.OEMVersion = OEMVersion
        this.PhoneNumber = PhoneNumber
        this.SubscriberNumber = SubscriberNumber
        this.PasscodeStatus = PasscodeStatus
        this.SupportedApis = SupportedApis
        this.ExchangeStatus = ExchangeStatus
        this.LastCheckInTime = LastCheckInTime
        this.LastAgentConnectTime = LastAgentConnectTime
        this.LastAgentDisconnectTime = LastAgentDisconnectTime
        this.InRoaming = InRoaming
        this.AndroidDeviceAdmin = AndroidDeviceAdmin
        this.CanResetPassword = CanResetPassword
        this.ExchangeBlocked = ExchangeBlocked
        this.IsAgentCompatible = IsAgentCompatible
        this.IsAgentless = IsAgentless
        this.IsEncrypted = IsEncrypted
        this.IsOSSecure = IsOSSecure
        this.PasscodeEnabled = PasscodeEnabled
        this.BatteryStatus = BatteryStatus
        this.CellularSignalStrength = CellularSignalStrength
        this.NetworkConnectionType = NetworkConnectionType
        this.NetworkRSSI = NetworkRSSI
        this.HardwareEncryptionCaps = HardwareEncryptionCaps
    }

    //    Secondary constructor
    constructor(cursor: Cursor) : super(cursor) {
        this.AgentVersion = "N/A"
        this.HardwareSerialNumber = "N/A"
        this.HardwareVersion = "N/A"
        this.IMEI_MEID_ESN = "N/A"
        this.CellularCarrier = "N/A"
        this.LastLoggedOnUser = "N/A"
        this.NetworkBSSID = "N/A"
        this.Ipv6 = "N/A"
        this.NetworkSSID = "N/A"
        this.OEMVersion = "N/A"
        this.PhoneNumber = "N/A"
        this.SubscriberNumber = "N/A"
        this.PasscodeStatus = "N/A"
        this.SupportedApis = arrayOf("N/A")
        this.ExchangeStatus = "N/A"
        this.LastCheckInTime = "N/A"
        this.LastAgentConnectTime = "N/A"
        this.LastAgentDisconnectTime = "N/A"
        this.InRoaming = false
        this.AndroidDeviceAdmin = false
        this.CanResetPassword = false
        this.ExchangeBlocked = false
        this.IsAgentCompatible = false
        this.IsAgentless = false
        this.IsEncrypted = false
        this.IsOSSecure = false
        this.PasscodeEnabled = false
        this.BatteryStatus = -1
        this.CellularSignalStrength = -1
        this.NetworkConnectionType = -1
        this.NetworkRSSI = -1
        this.HardwareEncryptionCaps = -1
    }


    //TODO: Support following info
//    Array	Antivirus
//    Array	CustomData
//    DeviceTerms	DeviceTerms
//    DeviceUser	DeviceUserInfo
//    DeviceIntegratedApplication[]	IntegratedApplications

    val isInRoaming: Int
        get() = if (InRoaming) 1 else 0

    val isAndroidDeviceAdmin: Int
        get() = if (AndroidDeviceAdmin) 1 else 0

    val isCanResetPassword: Int
        get() = if (CanResetPassword) 1 else 0

    val isExchangeBlocked: Int
        get() = if (ExchangeBlocked) 1 else 0

    val isAgentCompatible: Int
        get() = if (IsAgentCompatible) 1 else 0

    val isAgentless: Int
        get() = if (IsAgentless) 1 else 0

    val isEncrypted: Int
        get() = if (IsEncrypted) 1 else 0

    val isOSSecure: Int
        get() = if (IsOSSecure) 1 else 0

    val isPasscodeEnabled: Int
        get() = if (PasscodeEnabled) 1 else 0

    val supportedApis: String
        get() {
            return SupportedApis.toString()
        }

    override fun getDevice(): AndroidPlus {
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

