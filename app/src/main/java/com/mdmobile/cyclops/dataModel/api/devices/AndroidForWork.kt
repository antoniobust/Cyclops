package com.mdmobile.cyclops.dataModel.api.devices

import android.database.Cursor

/**
 * Represent gson class for android for work devices
 */
class AndroidForWork : AndroidPlus, IDevice<AndroidPlus> {

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
                BatteryStatus: Short = -1, CellularSignalStrength: Int = -1, NetworkConnectionType: String = "N/A",
                NetworkRSSI: Int = -1, HardwareEncryptionCaps: Int = -1)
            : super(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer,
            Mode, Model, OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform,
            AgentVersion, HardwareSerialNumber, HardwareVersion, IMEI_MEID_ESN, CellularCarrier, LastLoggedOnUser,
            NetworkBSSID, Ipv6, NetworkSSID, OEMVersion, PhoneNumber, SubscriberNumber, PasscodeStatus, SupportedApis,
            ExchangeStatus, LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, InRoaming, AndroidDeviceAdmin,
            CanResetPassword, ExchangeBlocked, IsAgentCompatible, IsAgentless, IsEncrypted, IsOSSecure, PasscodeEnabled,
            BatteryStatus, CellularSignalStrength, NetworkConnectionType, NetworkRSSI, HardwareEncryptionCaps)

    constructor(c: Cursor) : super(c)

    override fun getDevice(): AndroidForWork {
        return this
    }

//    override fun toContentValues(): ContentValues {
//        val values = super.toContentValues()
//        val stringBuilder = StringBuilder()
//        this::class.declaredMemberProperties.forEach {
//            if (it.visibility == KVisibility.PUBLIC) {
//                stringBuilder.append(it.name).append("=")
//                        .append(it.getter.call(this).toString())
//                        .append(";")
//            }
//        }
//        values.put(McContract.Device.COLUMN_EXTRA_INFO, stringBuilder.toString())
//        return values
//    }

}
//    TODO:Support this info
//    DeviceUserIdentity[]	UserIdentities


