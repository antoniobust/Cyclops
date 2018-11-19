package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.DEVICE_TABLE_NAME, foreignKeys = [ForeignKey(
        entity = InstanceInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId")
)])
data class Device(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @field:SerializedName("Kind")
        val kind: String,
        @PrimaryKey
        @field:SerializedName("DeviceId")
        val deviceId: String,
        @field:SerializedName("DeviceName")
        val deviceName: String,
        @field:SerializedName("EnrollmentTime")
        val enrollmentTime: String,
        @field:SerializedName("Family")
        val family: String,
        @field:SerializedName("HostName")
        val hostName: String,
        @field:SerializedName("MACAddress")
        val macAddress: String,
        @field:SerializedName("Manufacturer")
        val manufacturer: String,
        @field:SerializedName("Mode")
        val mode: String,
        @field:SerializedName("Model")
        val model: String,
        @field:SerializedName("OSVersion")
        val osVersion: String,
        @field:SerializedName("Path")
        val path: String,
        @field:SerializedName("ComplianceStatus")
        val complianceStatus: Boolean,
        @field:SerializedName("IsAgentOnline")
        val isAgentOnline: Boolean,
        @field:SerializedName("IsVirtual")
        val isVirtual: Boolean,
        @field:SerializedName("Platform")
        val platform: String,
        val extraInfo: String,
        val instanceId: Int)