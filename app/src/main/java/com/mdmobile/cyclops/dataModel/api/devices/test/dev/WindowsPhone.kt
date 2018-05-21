package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.dataModel.api.devices.test.DeviceUserInfo

class WindowsPhone(
        @SerializedName("BiosVersion") val biosVersion: Any = Any(),
        @SerializedName("CellularCarrier") val cellularCarrier: Any = Any(),
        @SerializedName("CpuId") val cpuId: String = "",
        @SerializedName("DeviceUserInfo") val deviceUserInfo: DeviceUserInfo = DeviceUserInfo(),
        @SerializedName("DMRevision") val dMRevision: String = "",
        @SerializedName("FirmwareVersion") val firmwareVersion: String = "",
        @SerializedName("HardwareEncryptionCaps") val hardwareEncryptionCaps: Int = 0,
        @SerializedName("HardwareVersion") val hardwareVersion: String = "",
        @SerializedName("IMEI_MEID_ESN") val iMEIMEIDESN: String = "",
        @SerializedName("IMEI_MEID_ESN_SIM2") val iMEIMEIDESNSIM2: Any = Any(),
        @SerializedName("InRoaming") val inRoaming: Boolean = false,
        @SerializedName("InRoamingSIM2") val inRoamingSIM2: Boolean = false,
        @SerializedName("Language") val language: String = "",
        @SerializedName("PasscodeEnabled") val passcodeEnabled: Boolean = false,
        @SerializedName("PasscodeStatus") val passcodeStatus: String = "",
        @SerializedName("PhoneNumber") val phoneNumber: Any = Any(),
        @SerializedName("PhoneNumberSIM2") val phoneNumberSIM2: Any = Any(),
        @SerializedName("RadioVersion") val radioVersion: String = "",
        @SerializedName("ScreenResolution") val screenResolution: String = "",
        @SerializedName("SIMCarrierNetwork") val sIMCarrierNetwork: Any = Any(),
        @SerializedName("SubscriberNumber") val subscriberNumber: Any = Any(),
        @SerializedName("SubscriberNumberSIM2") val subscriberNumberSIM2: Any = Any(),
        @SerializedName("TimeZone") val timeZone: String = "",
        @SerializedName("ICCID") val iCCID: Any = Any(),
        @SerializedName("ICCIDSIM2") val iCCIDSIM2: Any = Any(),
        kind: String, complianceStatus: Boolean, complianceItems: List<ComplianceItem>, deviceId: String,
        deviceName: String, enrollmentTime: String, family: String, hostName: String, isAgentOnline: Boolean, isVirtual: Boolean,
        customAttributes: List<CustomAttribute>, mACAddress: String, manufacturer: String, mode: String, model: String, oSVersion: String,
        path: String, platform: String)
    : BasicDevice(kind, complianceStatus, complianceItems, deviceId, deviceName, enrollmentTime, family, hostName,
        isAgentOnline, isVirtual, customAttributes, mACAddress, manufacturer, mode, model, oSVersion, path, platform)