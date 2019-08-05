package mobicontrol.mcApiService.dataModel


import com.google.gson.annotations.SerializedName


data class Device(
        @field:SerializedName("Kind")
        val kind: String = "N/A",
        @field:SerializedName("DeviceId")
        val deviceId: String = "N/A",
        @field:SerializedName("DeviceName")
        val deviceName: String = "N/A",
        @field:SerializedName("EnrollmentTime")
        val enrollmentTime: String = "N/A",
        @field:SerializedName("Family")
        val family: String = "N/A",
        @field:SerializedName("HostName")
        val hostName: String = "N/A",
        @field:SerializedName("MACAddress")
        val macAddress: String = "N/A",
        @field:SerializedName("Manufacturer")
        val manufacturer: String = "N/A",
        @field:SerializedName("Mode")
        val mode: String = "N/A",
        @field:SerializedName("Model")
        val model: String = "N/A",
        @field:SerializedName("OSVersion")
        val osVersion: String = "N/A",
        @field:SerializedName("Path")
        val path: String = "N/A",
        @field:SerializedName("ComplianceStatus")
        val complianceStatus: Boolean,
        @field:SerializedName("IsAgentOnline")
        val isAgentOnline: Boolean,
        @field:SerializedName("IsVirtual")
        val isVirtual: Boolean,
        @field:SerializedName("Platform")
        val platform: String = "N/A",
        val extraInfo: String = "N/A",
        val instanceId: Int)

