package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.dataModel.api.devices.test.Memory

class WindowsCE(
        @SerializedName("AgentVersion") val agentVersion: String = "",
        @SerializedName("Memory") val memory: Memory = Memory(),
        @SerializedName("BackupBatteryStatus") val backupBatteryStatus: Int = 0,
        @SerializedName("BatteryStatus") val batteryStatus: Int = 0,
        @SerializedName("CellularCarrier") val cellularCarrier: Any = Any(),
        @SerializedName("CellularSignalStrength") val cellularSignalStrength: Any = Any(),
        @SerializedName("CustomData") val customData: List<Any> = listOf(),
        @SerializedName("DeviceTerms") val deviceTerms: Any = Any(),
        @SerializedName("DeviceUserInfo") val deviceUserInfo: Any = Any(),
        @SerializedName("ExchangeBlocked") val exchangeBlocked: Boolean = false,
        @SerializedName("ExchangeStatus") val exchangeStatus: String = "",
        @SerializedName("HardwareSerialNumber") val hardwareSerialNumber: String = "",
        @SerializedName("IMEI_MEID_ESN") val iMEIMEIDESN: String = "",
        @SerializedName("InRoaming") val inRoaming: Boolean = false,
        @SerializedName("Ipv6") val ipv6: String = "",
        @SerializedName("IsAgentCompatible") val isAgentCompatible: Boolean = false,
        @SerializedName("IsAgentless") val isAgentless: Boolean = false,
        @SerializedName("IsLearning") val isLearning: Boolean = false,
        @SerializedName("LastCheckInTime") val lastCheckInTime: String = "",
        @SerializedName("LastAgentConnectTime") val lastAgentConnectTime: String = "",
        @SerializedName("LastAgentDisconnectTime") val lastAgentDisconnectTime: String = "",
        @SerializedName("LastLoggedOnAt") val lastLoggedOnAt: Any = Any(),
        @SerializedName("LastLoggedOnUser") val lastLoggedOnUser: String = "",
        @SerializedName("NetworkConnectionType") val networkConnectionType: Int = 0,
        @SerializedName("NetworkRSSI") val networkRSSI: Int = 0,
        @SerializedName("NetworkSSID") val networkSSID: String = "",
        @SerializedName("PasscodeEnabled") val passcodeEnabled: Boolean = false,
        @SerializedName("PhoneNumber") val phoneNumber: Any = Any(),
        @SerializedName("Processor") val processor: String = "",
        @SerializedName("SubscriberNumber") val subscriberNumber: Any = Any(),
        kind: String, complianceStatus: Boolean, complianceItems: List<ComplianceItem>, deviceId: String,
        deviceName: String, enrollmentTime: String, family: String, hostName: String, isAgentOnline: Boolean, isVirtual: Boolean,
        customAttributes: List<CustomAttribute>, mACAddress: String, manufacturer: String, mode: String, model: String, oSVersion: String,
        path: String, platform: String)
    : BasicDevice(kind, complianceStatus, complianceItems, deviceId, deviceName, enrollmentTime, family, hostName,
        isAgentOnline, isVirtual,customAttributes,mACAddress, manufacturer, mode, model, oSVersion, path, platform)