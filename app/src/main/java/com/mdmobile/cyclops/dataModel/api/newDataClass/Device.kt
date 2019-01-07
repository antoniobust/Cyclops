package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.DEVICE_TABLE_NAME, foreignKeys = [ForeignKey(
        entity = InstanceInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId"))],
        indices = [Index(name = "Device",value = arrayOf("deviceId"), unique = true)])
data class Device(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
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
        val instanceId: Int)