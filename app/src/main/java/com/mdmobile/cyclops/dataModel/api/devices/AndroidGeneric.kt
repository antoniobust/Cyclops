package com.mdmobile.cyclops.dataModel.api.devices

import android.content.ContentValues
import android.database.Cursor
import com.mdmobile.cyclops.provider.McContract
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

/**
 * Represent Gson class for android generic device
 */

class AndroidGeneric : BasicDevice, IDevice<AndroidGeneric> {

    val lastAgentDisconnectTime: String
    val lastLoggedOnUser: String
    val networkBSSID: String
    val exchangeStatus: String
    val IMEI_MEID_ESN: String
    val Ipv6: String
    val CellularCarrier: String
    val NetworkSSID: String
    val PhoneNumber: String
    val SubscriberNumber: String
    val AndroidDeviceAdmin: Boolean
    val IsAgentCompatible: Boolean
    val IsAgentless: Boolean
    val IsEncrypted: Boolean
    val IsOSSecure: Boolean
    val CanResetPassword: Boolean
    val ExchangeBlocked: Boolean
    val InRoaming: Boolean
    val PasscodeEnabled: Boolean
    val BatteryStatus: Short
    val CellularSignalStrength: Int
    val HardwareEncryptionCaps: Int
    val NetworkConnectionType: Int
    val NetworkRSSI: Int
    val AgentVersion: String
    val LastCheckInTime: String
    val LastAgentConnectTime: String

    //Primary constructor
    constructor(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A",
                EnrollmentTime: String = "N/A", Family: String = "N/A", HostName: String = "N/A",
                MACAddress: String = "N/A", Manufacturer: String = "N/A", Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A",
                Path: String = "N/A", ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false, IsVirtual: Boolean = false,
                Platform: String = "N/A", AgentVersion: String = "N/A", LastCheckInTime: String = "N/A", LastAgentConnectTime: String = "N/A",
                LastAgentDisconnectTime: String = "N/A", LastLoggedOnUser: String = "N/A", NetworkBSSID: String = "N/A",
                ExchangeStatus: String = "N/A", IMEI_MEID_ESN: String = "N/A", Ipv6: String = "N/A", CellularCarrier: String = "N/A",
                NetworkSSID: String = "N/A", PhoneNumber: String = "N/A", SubscriberNumber: String = "N/A", AndroidDeviceAdmin: Boolean = false,
                IsAgentCompatible: Boolean = false, IsAgentless: Boolean = false, IsEncrypted: Boolean = false, IsOSSecure: Boolean = false,
                CanResetPassword: Boolean = false, ExchangeBlocked: Boolean = false, InRoaming: Boolean = false, PasscodeEnabled: Boolean = false,
                BatteryStatus: Short = -1, CellularSignalStrength: Int = -1, HardwareEncryptionCaps: Int = -1,
                NetworkConnectionType: Int = -1, NetworkRSSI: Int = -1)
            : super(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform) {
        this.lastAgentDisconnectTime = LastAgentDisconnectTime
        this.lastLoggedOnUser = LastLoggedOnUser
        this.networkBSSID = NetworkBSSID
        this.exchangeStatus = ExchangeStatus
        this.IMEI_MEID_ESN = IMEI_MEID_ESN
        this.Ipv6 = Ipv6
        this.CellularCarrier = CellularCarrier
        this.NetworkSSID = NetworkSSID
        this.PhoneNumber = PhoneNumber
        this.SubscriberNumber = SubscriberNumber
        this.AndroidDeviceAdmin = AndroidDeviceAdmin
        this.IsAgentCompatible = IsAgentCompatible
        this.IsAgentless = IsAgentless
        this.IsEncrypted = IsEncrypted
        this.IsOSSecure = IsOSSecure
        this.CanResetPassword = CanResetPassword
        this.ExchangeBlocked = ExchangeBlocked
        this.InRoaming = InRoaming
        this.PasscodeEnabled = PasscodeEnabled
        this.BatteryStatus = BatteryStatus
        this.CellularSignalStrength = CellularSignalStrength
        this.HardwareEncryptionCaps = HardwareEncryptionCaps
        this.NetworkConnectionType = NetworkConnectionType
        this.NetworkRSSI = NetworkRSSI
        this.LastAgentConnectTime = LastAgentConnectTime
        this.AgentVersion = AgentVersion
        this.LastCheckInTime = LastCheckInTime
    }

    //Secondary Constructor
    constructor(c: Cursor) : super(c) {
        this.lastAgentDisconnectTime = "N/A"
        this.lastLoggedOnUser = "N/A"
        this.networkBSSID = "N/A"
        this.exchangeStatus = "N/A"
        this.IMEI_MEID_ESN = "N/A"
        this.Ipv6 = "N/A"
        this.CellularCarrier = "N/A"
        this.NetworkSSID = "N/A"
        this.PhoneNumber = "N/A"
        this.SubscriberNumber = "N/A"
        this.AndroidDeviceAdmin = false
        this.IsAgentCompatible = false
        this.IsAgentless = false
        this.IsEncrypted = false
        this.IsOSSecure = false
        this.CanResetPassword = false
        this.ExchangeBlocked = false
        this.InRoaming = false
        this.PasscodeEnabled = false
        this.BatteryStatus = -1
        this.CellularSignalStrength = -1
        this.HardwareEncryptionCaps = -1
        this.NetworkConnectionType = -1
        this.NetworkRSSI = -1
        this.LastAgentConnectTime = "N/A"
        this.AgentVersion = "N/A"
        this.LastCheckInTime = "N/A"
    }

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
