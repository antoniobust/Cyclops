package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import com.google.gson.annotations.SerializedName

open class WindowsRuntime(
        @SerializedName("Kind") val kind: String = "",
        @SerializedName("ComplianceStatus") val complianceStatus: Boolean = false,
        @SerializedName("ComplianceItems") val complianceItems: List<ComplianceItem> = listOf(),
        @SerializedName("DeviceId") val deviceId: String = "",
        @SerializedName("DeviceName") val deviceName: String = "",
        @SerializedName("EnrollmentTime") val enrollmentTime: String = "",
        @SerializedName("Family") val family: String = "",
        @SerializedName("HostName") val hostName: String = "",
        @SerializedName("IsAgentOnline") val isAgentOnline: Boolean = false,
        @SerializedName("IsVirtual") val isVirtual: Boolean = false,
        @SerializedName("CustomAttributes") val customAttributes: List<CustomAttribute> = listOf(),
        @SerializedName("MACAddress") val mACAddress: String = "",
        @SerializedName("Manufacturer") val manufacturer: String = "",
        @SerializedName("Mode") val mode: String = "",
        @SerializedName("Model") val model: String = "",
        @SerializedName("OSVersion") val oSVersion: String = "",
        @SerializedName("Path") val path: String = "",
        @SerializedName("Platform") val platform: String = ""
) {
    class ComplianceItem(
            @SerializedName("ComplianceType") val complianceType: String = "",
            @SerializedName("ComplianceValue") val complianceValue: Boolean = false
    )

    class CustomAttribute(
            @SerializedName("Name") val name: String = "",
            @SerializedName("Value") val value: String = "",
            @SerializedName("DataType") val dataType: String = ""
    )
}