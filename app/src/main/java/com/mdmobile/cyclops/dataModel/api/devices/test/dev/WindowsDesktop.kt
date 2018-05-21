package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.dataModel.api.devices.test.DeviceUserInfo

class WindowsDesktop(
        @SerializedName("BiosVersion") val biosVersion: String = "",
        @SerializedName("DeviceUserInfo") val deviceUserInfo: DeviceUserInfo = DeviceUserInfo(),
        @SerializedName("DMRevision") val dMRevision: String = "",
        @SerializedName("FirmwareVersion") val firmwareVersion: Any = Any(),
        @SerializedName("HardwareEncryptionCaps") val hardwareEncryptionCaps: Int = 0,
        @SerializedName("HardwareVersion") val hardwareVersion: Any = Any(),
        @SerializedName("IMEI_MEID_ESN") val iMEIMEIDESN: Any = Any(),
        @SerializedName("InRoaming") val inRoaming: Boolean = false,
        @SerializedName("Language") val language: String = "",
        @SerializedName("PasscodeEnabled") val passcodeEnabled: Boolean = false,
        @SerializedName("PasscodeStatus") val passcodeStatus: String = "",
        @SerializedName("ScreenResolution") val screenResolution: String = "",
        @SerializedName("SubscriberNumber") val subscriberNumber: Any = Any(),
        @SerializedName("TimeZone") val timeZone: String = "",
        kind: String, complianceStatus: Boolean, complianceItems: List<ComplianceItem>, deviceId: String,
        deviceName: String, enrollmentTime: String, family: String, hostName: String, isAgentOnline: Boolean, isVirtual: Boolean,
        customAttributes: List<CustomAttribute>, mACAddress: String, manufacturer: String, mode: String, model: String, oSVersion: String,
        path: String, platform: String)
    : BasicDevice(kind, complianceStatus, complianceItems, deviceId, deviceName, enrollmentTime, family, hostName,
        isAgentOnline, isVirtual, customAttributes, mACAddress, manufacturer, mode, model, oSVersion, path, platform)