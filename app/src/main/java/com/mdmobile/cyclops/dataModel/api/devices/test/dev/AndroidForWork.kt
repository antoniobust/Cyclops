package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.dataModel.api.devices.test.Antivirus
import com.mdmobile.cyclops.dataModel.api.devices.test.Memory

class AndroidForWork(
        @SerializedName("AndroidForWork") val androidForWork: AndroidForWork = AndroidForWork(),
        @SerializedName("BluetoothMACAddress") val bluetoothMACAddress: Any = Any(),
        @SerializedName("BuildVersion") val buildVersion: Any = Any(),
        @SerializedName("CellularTechnology") val cellularTechnology: String = "",
        @SerializedName("UserIdentities") val userIdentities: Any = Any(),
        @SerializedName("AgentVersion") val agentVersion: String = "",
        @SerializedName("AndroidApiLevel") val androidApiLevel: Int = 0,
        @SerializedName("AndroidDeviceAdmin") val androidDeviceAdmin: Boolean = false,
        @SerializedName("AndroidRcLibraryVersion") val androidRcLibraryVersion: String = "",
        @SerializedName("Antivirus") val antivirus: Antivirus = Antivirus(),
        @SerializedName("Memory") val memory: Memory = Memory(),
        @SerializedName("BatteryStatus") val batteryStatus: Int = 0,
        @SerializedName("BuildSecurityPatch") val buildSecurityPatch: String = "",
        @SerializedName("CanResetPassword") val canResetPassword: Boolean = false,
        @SerializedName("CellularCarrier") val cellularCarrier: Any = Any(),
        @SerializedName("CellularSignalStrength") val cellularSignalStrength: Any = Any(),
        @SerializedName("AsuLevel") val asuLevel: Any = Any(),
        @SerializedName("CustomData") val customData: List<Any> = listOf(),
        @SerializedName("DeviceTerms") val deviceTerms: Any = Any(),
        @SerializedName("DeviceUserInfo") val deviceUserInfo: Any = Any(),
        @SerializedName("ExchangeBlocked") val exchangeBlocked: Boolean = false,
        @SerializedName("ExchangeStatus") val exchangeStatus: String = "",
        @SerializedName("HardwareEncryptionCaps") val hardwareEncryptionCaps: Int = 0,
        @SerializedName("HardwareSerialNumber") val hardwareSerialNumber: String = "",
        @SerializedName("HardwareVersion") val hardwareVersion: Any = Any(),
        @SerializedName("ICCID") val iCCID: String = "",
        @SerializedName("IMEI_MEID_ESN") val iMEIMEIDESN: Any = Any(),
        @SerializedName("InRoaming") val inRoaming: Boolean = false,
        @SerializedName("IntegratedApplications") val integratedApplications: Any = Any(),
        @SerializedName("Ipv6") val ipv6: String = "",
        @SerializedName("IsAgentCompatible") val isAgentCompatible: Boolean = false,
        @SerializedName("IsAgentless") val isAgentless: Boolean = false,
        @SerializedName("IsEncrypted") val isEncrypted: Boolean = false,
        @SerializedName("IsOSSecure") val isOSSecure: Boolean = false,
        @SerializedName("LastCheckInTime") val lastCheckInTime: String = "",
        @SerializedName("LastAgentConnectTime") val lastAgentConnectTime: String = "",
        @SerializedName("LastAgentDisconnectTime") val lastAgentDisconnectTime: String = "",
        @SerializedName("LastLoggedOnUser") val lastLoggedOnUser: String = "",
        @SerializedName("NetworkBSSID") val networkBSSID: String = "",
        @SerializedName("NetworkConnectionType") val networkConnectionType: Int = 0,
        @SerializedName("NetworkRSSI") val networkRSSI: Int = 0,
        @SerializedName("NetworkSSID") val networkSSID: String = "",
        @SerializedName("OEMVersion") val oEMVersion: String = "",
        @SerializedName("PasscodeEnabled") val passcodeEnabled: Boolean = false,
        @SerializedName("PasscodeStatus") val passcodeStatus: String = "",
        @SerializedName("PhoneNumber") val phoneNumber: Any = Any(),
        @SerializedName("SelectedApn") val selectedApn: String = "",
        @SerializedName("SubscriberNumber") val subscriberNumber: Any = Any(),
        @SerializedName("SupportedApis") val supportedApis: List<String> = listOf(),
        kind: String, complianceStatus: Boolean, complianceItems: List<ComplianceItem>, deviceId: String,
        deviceName: String, enrollmentTime: String, family: String, hostName: String, isAgentOnline: Boolean, isVirtual: Boolean,
        customAttributes: List<CustomAttribute>, mACAddress: String, manufacturer: String, mode: String, model: String, oSVersion: String,
        path: String, platform: String)
    : BasicDevice(kind, complianceStatus, complianceItems, deviceId, deviceName, enrollmentTime, family, hostName,
        isAgentOnline, isVirtual,customAttributes,mACAddress, manufacturer, mode, model, oSVersion, path, platform) {

    data class AndroidForWork(
            @SerializedName("AfwProfileDisabled") val afwProfileDisabled: Boolean = false,
            @SerializedName("AfwProvisionStage") val afwProvisionStage: String = "",
            @SerializedName("AfwManagementType") val afwManagementType: String = ""
    )
}