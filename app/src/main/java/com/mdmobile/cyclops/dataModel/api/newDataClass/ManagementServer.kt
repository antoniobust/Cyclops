package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.MANAGEMENT_SERVER_TABLE_NAME)
data class ManagementServer(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @field:SerializedName("PrimaryManagementAddress")
        val primaryManagementAddress: String,
        @field:SerializedName("SecondaryManagementAddress")
        val secondaryManagementAddress: String,
        @field:SerializedName("PrimaryAgentAddress")
        val primaryAgentAddress: String,
        @field:SerializedName("SecondaryAgentAddress")
        val secondaryAgentAddress: String,
        @field:SerializedName("DeviceManagementAddress")
        val deviceManagementAddress: String,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("Status")
        val status: String,
        @field:SerializedName("IsConnected")
        val isConnected: Boolean,
        @field:SerializedName("PulseTimeout")
        val pulseTimeout: Int,
        @field:SerializedName("RuleReload")
        val ruleReload: Int,
        @field:SerializedName("ScheduleInterval")
        val scheduleInterval: Int,
        @field:SerializedName("MinThreads")
        val minThreads: Int,
        @field:SerializedName("MaxThread")
        val maxThread: Int,
        @field:SerializedName("MaxBurstThreads")
        val maxBurstThreads: Int,
        @field:SerializedName("PulseWaitInterval")
        val pulseWaitInterval: Int,
        @field:SerializedName("ConnectedDeviceCount")
        val connectedDeviceCount: Int,
        @field:SerializedName("ConnectedManagerCount")
        val connectedManagerCount: Int,
        @field:SerializedName("MsgQueueLength")
        val msgQueueLength: Int,
        @field:SerializedName("CurrentThreadCount")
        val currentThreadCount: Int)