package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.DEPLOYMENT_SERVER_TABLE_NAME,
        foreignKeys = [ForeignKey(
                entity = InstanceInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId")
        )])
data class DeploymentServer(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @field:SerializedName("PrimaryManagementAddress")
        val primaryManagementAddress: String = "N/A",
        @field:SerializedName("SecondaryManagementAddress")
        val secondaryManagementAddress: String = "N/A",
        @field:SerializedName("PrimaryAgentAddress")
        val primaryAgentAddress: String = "N/A",
        @field:SerializedName("SecondaryAgentAddress")
        val secondaryAgentAddress: String = "N/A",
        @field:SerializedName("DeviceManagementAddress")
        val deviceManagementAddress: String = "N/A",
        @field:SerializedName("Name")
        val name: String = "N/A",
        @field:SerializedName("Status")
        val status: String = "N/A",
        @field:SerializedName("IsConnected")
        val isConnected: Boolean = false,
        @field:SerializedName("PulseTimeout")
        val pulseTimeout: Int = -1,
        @field:SerializedName("RuleReload")
        val ruleReload: Int = -1,
        @field:SerializedName("ScheduleInterval")
        val scheduleInterval: Int = -1,
        @field:SerializedName("MinThreads")
        val minThreads: Int = -1,
        @field:SerializedName("MaxThread")
        val maxThread: Int = -1,
        @field:SerializedName("MaxBurstThreads")
        val maxBurstThreads: Int = -1,
        @field:SerializedName("PulseWaitInterval")
        val pulseWaitInterval: Int = -1,
        @field:SerializedName("ConnectedDeviceCount")
        val connectedDeviceCount: Int = -1,
        @field:SerializedName("ConnectedManagerCount")
        val connectedManagerCount: Int = -1,
        @field:SerializedName("MsgQueueLength")
        val msgQueueLength: Int = -1,
        @field:SerializedName("CurrentThreadCount")
        val currentThreadCount: Int = -1,
        val instanceId: Int = -1) {

    @Ignore
    constructor(
            primaryManagementAddress: String = "N/A",
            secondaryManagementAddress: String = "N/A",
            primaryAgentAddress: String = "N/A",
            secondaryAgentAddress: String = "N/A",
            deviceManagementAddress: String = "N/A",
            name: String = "N/A",
            status: String = "N/A",
            isConnected: Boolean = false,
            pulseTimeout: Int = -1,
            ruleReload: Int = -1,
            scheduleInterval: Int = -1,
            minThreads: Int = -1,
            maxThread: Int = -1,
            maxBurstThreads: Int = -1,
            pulseWaitInterval: Int = -1,
            connectedDeviceCount: Int = -1,
            connectedManagerCount: Int = -1,
            msgQueueLength: Int = -1,
            currentThreadCount: Int = -1,
            instanceId: Int = -1
    ) : this(
            -1, primaryManagementAddress, secondaryManagementAddress, primaryAgentAddress, secondaryAgentAddress,
            deviceManagementAddress, name, status, isConnected, pulseTimeout, ruleReload, scheduleInterval,
            minThreads, maxThread, maxBurstThreads, pulseWaitInterval, connectedDeviceCount, connectedManagerCount,
            msgQueueLength, currentThreadCount, instanceId
    )
}