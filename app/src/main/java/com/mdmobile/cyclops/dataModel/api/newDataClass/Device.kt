package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.DEVICE_TABLE_NAME, foreignKeys = [ForeignKey(
        entity = InstanceInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId"))],
        indices = [Index(name = "Device", value = arrayOf("deviceId"), unique = true)])
data class Device(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = -1,
        @field:SerializedName("Kind")
        val kind: String = "N/A",
        @field:SerializedName("DeviceId")
        @NonNull
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
        val instanceId: Int) {

    @Ignore
    constructor(kind: String = "N/A",
                deviceId: String = "N/A",
                deviceName: String = "N/A",
                enrollmentTime: String = "N/A",
                family: String = "N/A",
                hostName: String = "N/A",
                macAddress: String = "N/A",
                manufacturer: String = "N/A",
                mode: String = "N/A",
                model: String = "N/A",
                osVersion: String = "N/A",
                path: String = "N/A",
                complianceStatus: Boolean,
                isAgentOnline: Boolean,
                isVirtual: Boolean,
                platform: String = "N/A",
                extraInfo: String = "N/A"
    ) : this(id = -1, instanceId = -1, kind = kind, deviceId = deviceId, deviceName = deviceName, enrollmentTime = enrollmentTime,
            family = family, hostName = hostName, macAddress = macAddress, manufacturer = manufacturer, mode = mode,
            model = model, osVersion = osVersion, path = path, complianceStatus = complianceStatus, isAgentOnline = isAgentOnline,
            isVirtual = isVirtual, platform = platform, extraInfo = extraInfo)
}